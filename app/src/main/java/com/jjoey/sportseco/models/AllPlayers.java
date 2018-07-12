package com.jjoey.sportseco.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "all_players_coach")
public class AllPlayers extends Model {

    @Column(name = "user_id_player")
    public String userId;

    @Column(name = "first_name_player")
    public String firstName;

    @Column(name = "last_name_player")
    public String lastName;

    @Column(name = "image_url_player")
    public String imageURL;

    @Column(name = "username_player")
    public String username;

    @Column(name = "address_player")
    public String address;

    public AllPlayers() {
    }

    public AllPlayers(String userId, String firstName, String lastName, String imageURL, String username, String address) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
        this.username = username;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
