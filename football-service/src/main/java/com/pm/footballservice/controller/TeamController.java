package com.pm.footballservice.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pm.footballservice.dto.ClubDTO;
import com.pm.footballservice.model.Club;
import com.pm.footballservice.service.TeamService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final Gson gson;

    public TeamController(TeamService teamService, Gson gson) {
        this.teamService = teamService;
        this.gson = gson;
    }

    @GetMapping
    public ResponseEntity<?> getTeam() throws URISyntaxException {
        return teamService.getTeam();
    }

    @PostMapping("/addBookmark")
    public ResponseEntity<?> addBookmark(@RequestBody ClubDTO club) throws URISyntaxException {
        return teamService.addBookmark(club);
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<?> getBookmarks() throws URISyntaxException {
        List<Club> clubs = teamService.getUserBookmarkedClubs();
        System.out.println(clubs);
        HashMap<String, Object> response = new HashMap<>();
        response.put("bookmarks", clubs);
        // JsonObject response = new JsonObject();
        // response.add("club", gson.toJsonTree(clubs));
        return ResponseEntity.ok().body(response);
    }
}
