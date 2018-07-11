package com.jjoey.sportseco.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoachResponse {

    @Expose
    @SerializedName("coach_details")
    private CoachDetails coachDetails;

    @Expose
    @SerializedName("status")
    private Integer status;

    @Expose
    @SerializedName("message")
    private String message;

    public CoachResponse() {
    }

    public CoachDetails getCoachDetails() {
        return coachDetails;
    }

    public void setCoachDetails(CoachDetails coachDetails) {
        this.coachDetails = coachDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class CoachDetails {

        @Expose
        @SerializedName("coach_id")
        public String coachId;

        @Expose
        @SerializedName("academy_id")
        public String academyId;

        @Expose
        @SerializedName("username")
        public String username;

        @Expose
        @SerializedName("first_name")
        public String firstName;

        @Expose
        @SerializedName("last_name")
        public String lastName;

        @Expose
        @SerializedName("gender")
        public String gender;

        @Expose
        @SerializedName("mobile")
        public String mobileNum;

        @Expose
        @SerializedName("email")
        public String emailAddr;

        @Expose
        @SerializedName("middle_name")
        public String midName;

        @Expose
        @SerializedName("nick_name")
        public String nickName;

        @Expose
        @SerializedName("state")
        public String originState;

        public String getCoachId() {
            return coachId;
        }

        public void setCoachId(String coachId) {
            this.coachId = coachId;
        }

        public String getAcademyId() {
            return academyId;
        }

        public void setAcademyId(String academyId) {
            this.academyId = academyId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMobileNum() {
            return mobileNum;
        }

        public void setMobileNum(String mobileNum) {
            this.mobileNum = mobileNum;
        }

        public String getEmailAddr() {
            return emailAddr;
        }

        public void setEmailAddr(String emailAddr) {
            this.emailAddr = emailAddr;
        }

        public String getMidName() {
            return midName;
        }

        public void setMidName(String midName) {
            this.midName = midName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getOriginState() {
            return originState;
        }

        public void setOriginState(String originState) {
            this.originState = originState;
        }

    }

}
