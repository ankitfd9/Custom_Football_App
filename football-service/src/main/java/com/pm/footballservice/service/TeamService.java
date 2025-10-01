package com.pm.footballservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pm.footballservice.dto.ClubDTO;
import com.pm.footballservice.model.Club;
import com.pm.footballservice.model.Team;
import com.pm.footballservice.model.User;
import com.pm.footballservice.repository.ClubRepository;
import com.pm.footballservice.repository.UserRepository;
import com.pm.footballservice.utils.CustomUserDetails;
import kong.unirest.HttpResponse;
import kong.unirest.HttpStatus;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TeamService {
    Logger logger = org.slf4j.LoggerFactory.getLogger(TeamService.class);
    private UserRepository userRepository;
    private ClubRepository clubRepository;

    public TeamService(UserRepository userRepository,ClubRepository clubRepository) {
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
    }
    private String API_URI= "https://api.football-data.org/v4/teams";
    //private String API_URI= "https://api.football-data.org/v4/teams/81/matches";

    @Value("${API.KEY}")
    private String API_KEY;

    Gson gson = new Gson();



    public ResponseEntity<?> getTeam() throws URISyntaxException {
/*        String urlWithParam = API_URI + "?name=" + team.getName();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(urlWithParam))
                .header("x-apisports-key", API_KEY)
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        Team teamResponse = null;
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Example: log response
            System.out.println("Response: " + response.body());

            teamResponse = gson.fromJson(response.body(), Team.class);

            // TODO: Convert response.body() to a Team object using a JSON library (e.g., Jackson or Gson)
            // return objectMapper.readValue(response.body(), Team.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return teamResponse; // replace with parsed Team object*/

        List<Integer> teamIds = getUserBookmarkedClubs().stream().map(Club::getId).toList();
        logger.debug("USER DEBUG ENABLED : " +teamIds);

        JSONArray matches = new JSONArray();
        JSONObject json = null;
        String responseBody = null;
        String URI_FINAL;
        HttpResponse<String> response= null;

        if(!teamIds.isEmpty()) {
            for (int i = 0; i < teamIds.size(); i++) {
                URI_FINAL = API_URI + "/" + teamIds.get(i) + "/matches";
                response = Unirest.get(URI_FINAL)
                        .header("X-Auth-Token", API_KEY)
                        .header("Accept-Encoding", "")
                        .asString();
                if (response.getStatus() == 200) {
                    responseBody = response.getBody();
                    json = new JSONObject(responseBody);
                    matches = matches.putAll(json.getJSONArray("matches"));
                } else {
                    return ResponseEntity
                            .status(response.getStatus())
                            .body("{\"error\":\"Failed to fetch data\"}");
                }
            }
            
        }
        else{
            return ResponseEntity.ok().body(new JSONObject(responseBody));
        }

        /*HttpResponse<String> response = Unirest.get(API_URI)
                .header("X-Auth-Token", API_KEY)
                .header("Accept-Encoding", "")
                .asString();
        if (response.getStatus() == 200) {
            responseBody = response.getBody();

            // Parse the response body into a JSON object
            json = new JSONObject(responseBody);

            // Directly extract the 'matches' array from the root
            matches = json.getJSONArray("matches");


            //return ResponseEntity.ok(matches.toString());
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity
                    .status(response.getStatus())
                    .body("{\"error\":\"Failed to fetch data\"}");
        }*/
        Gson gson = new Gson();
        HashMap<String, Object> res = new HashMap<>();
        printMatches(matches,res);
        //res.put("matches", matches);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(res);
    }

    public List<Club> getUserBookmarkedClubs() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails userDetails) {
                return userDetails.getBookmarkedClubs();
            }
        }

        return Collections.emptyList();
    }


    public ResponseEntity<?> addBookmark(ClubDTO club1) {
        int clubId=club1.getClubId();
        System.out.println(clubId);
        if(!clubRepository.existsById(clubId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Club club = clubRepository.findById(clubId).get();
        List<Club> list = getUserBookmarkedClubs();
        list.add(club);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                User user = userDetails.getUser();
                user.setBookmarkedClubs(list);
                userRepository.save(user);
            }
        }
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("club", clubId);
        return ResponseEntity.ok().body(responseMap);
    }
    private void printMatches(JSONArray matches, HashMap<String,Object> res){
        // Print each match info
        List<HashMap<String,Object>> matchList = new ArrayList<>();

            for (int i = 0; i < matches.length(); i++) {
                JSONObject match = matches.getJSONObject(i);
                matchList.add((HashMap<String, Object>) match.toMap());
                // logger.debug("Match ID: " + match.getInt("id"));
                // logger.debug("UTC Date: " + match.getString("utcDate"));
                // logger.debug("Home Team: " + match.getJSONObject("homeTeam").getString("name"));
                // logger.info("Away Team: " + match.getJSONObject("awayTeam").getString("name"));
                // logger.info("Score: " + match.getJSONObject("score").getJSONObject("fullTime").toString());
                // System.out.println("---------------------------");
            }
        res.put("matches",matchList);
        // System.out.println(matchList);
    }
}

