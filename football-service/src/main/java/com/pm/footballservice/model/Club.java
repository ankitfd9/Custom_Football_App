package com.pm.footballservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private int id;
    private String name;
    private String league;
    private int leagueId;
    private String leagueCode;


    public Club(int id, String name, String league, int leagueId, String leagueCode) {
        this.id = id;
        this.name = name;
        this.league = league;
        this.leagueId = leagueId;
        this.leagueCode = leagueCode;
    }

    public Club() {

    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueCode() {
        return leagueCode;
    }

    public void setLeagueCode(String leagueCode) {
        this.leagueCode = leagueCode;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}
