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

    @Column(name = "dob_player")
    public String dobPlayer;

    @Column(name = "gender_player")
    public String genderPlayer;

    @Column(name = "pincode_player")
    public String pinCodePlayer;

    @Column(name = "height_player")
    public String heightPlayer;

    @Column(name = "weight_player")
    public String weightPlayer;

    @Column(name = "basketball_exp_player")
    public String bsktballExpPlayer;

    @Column(name = "position_player")
    public String positionPlayer;

    @Column(name = "state_player")
    public String statePlayer;

    @Column(name = "mobile_player")
    public String mobilePlayer;

    public AllPlayers() {
    }

    public AllPlayers(String userId, String firstName, String lastName, String imageURL, String username, String address, String dobPlayer, String genderPlayer, String pinCodePlayer, String heightPlayer, String weightPlayer, String bsktballExpPlayer, String positionPlayer, String statePlayer, String mobilePlayer) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
        this.username = username;
        this.address = address;
        this.dobPlayer = dobPlayer;
        this.genderPlayer = genderPlayer;
        this.pinCodePlayer = pinCodePlayer;
        this.heightPlayer = heightPlayer;
        this.weightPlayer = weightPlayer;
        this.bsktballExpPlayer = bsktballExpPlayer;
        this.positionPlayer = positionPlayer;
        this.statePlayer = statePlayer;
        this.mobilePlayer = mobilePlayer;
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

    public String getDobPlayer() {
        return dobPlayer;
    }

    public void setDobPlayer(String dobPlayer) {
        this.dobPlayer = dobPlayer;
    }

    public String getGenderPlayer() {
        return genderPlayer;
    }

    public void setGenderPlayer(String genderPlayer) {
        this.genderPlayer = genderPlayer;
    }

    public String getPinCodePlayer() {
        return pinCodePlayer;
    }

    public void setPinCodePlayer(String pinCodePlayer) {
        this.pinCodePlayer = pinCodePlayer;
    }

    public String getHeightPlayer() {
        return heightPlayer;
    }

    public void setHeightPlayer(String heightPlayer) {
        this.heightPlayer = heightPlayer;
    }

    public String getWeightPlayer() {
        return weightPlayer;
    }

    public void setWeightPlayer(String weightPlayer) {
        this.weightPlayer = weightPlayer;
    }

    public String getBsktballExpPlayer() {
        return bsktballExpPlayer;
    }

    public void setBsktballExpPlayer(String bsktballExpPlayer) {
        this.bsktballExpPlayer = bsktballExpPlayer;
    }

    public String getPositionPlayer() {
        return positionPlayer;
    }

    public void setPositionPlayer(String positionPlayer) {
        this.positionPlayer = positionPlayer;
    }

    public String getStatePlayer() {
        return statePlayer;
    }

    public void setStatePlayer(String statePlayer) {
        this.statePlayer = statePlayer;
    }

    public String getMobilePlayer() {
        return mobilePlayer;
    }

    public void setMobilePlayer(String mobilePlayer) {
        this.mobilePlayer = mobilePlayer;
    }
}
