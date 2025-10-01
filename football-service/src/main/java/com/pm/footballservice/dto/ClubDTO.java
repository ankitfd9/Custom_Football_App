package com.pm.footballservice.dto;


public class ClubDTO {

    private int clubId;
    private String clubName;

    public ClubDTO(int clubId) {
        this.clubId = clubId;
    }

    public ClubDTO(int clubId, String clubName) {
        this.clubId = clubId;
        this.clubName = clubName;
    }

    public ClubDTO() {
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }
}
