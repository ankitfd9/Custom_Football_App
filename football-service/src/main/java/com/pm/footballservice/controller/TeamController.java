package com.pm.footballservice.controller;

import com.pm.footballservice.model.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {

    @GetMapping("/")
    public ResponseEntity<?> getTeam(@RequestBody Team team) {
        return ResponseEntity.ok().build();
    }
}
