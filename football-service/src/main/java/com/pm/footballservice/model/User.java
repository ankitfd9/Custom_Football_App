package com.pm.footballservice.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    @Column(nullable=false)
    String username;

    @Column(nullable=false)
    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_club_bookmarks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "club_id")
    )
    List<Club> bookmarkedClubs = new ArrayList<>();

    public List<Club> getBookmarkedClubs() {
        return bookmarkedClubs;
    }

    public void setBookmarkedClubs(List<Club> bookmarkedClubs) {
        this.bookmarkedClubs = bookmarkedClubs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
