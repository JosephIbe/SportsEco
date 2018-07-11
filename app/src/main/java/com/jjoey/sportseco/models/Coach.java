package com.jjoey.sportseco.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "coach_details")
public class Coach extends Model {

    @Column(name = "coach_id")
    public String coachId;

    @Column(name = "academy_id")
    public String academyId;

    @Column(name = "username")
    public String username;

    @Column(name = "first_name")
    public String firstName;

    @Column(name = "last_name")
    public String lastName;

    @Column(name = "gender")
    public String gender;

    @Column(name = "mobile_num")
    public String mobileNum;

    @Column(name = "email_addr")
    public String emailAddr;

    @Column(name = "middle_name")
    public String midName;

    @Column(name = "nick_name")
    public String nickName;

    @Column(name = "state")
    public String originState;

    public Coach() {
    }

    public Coach(String coachId, String academyId, String username, String firstName, String lastName, String gender, String mobileNum, String emailAddr, String midName, String nickName, String originState) {
        this.coachId = coachId;
        this.academyId = academyId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.mobileNum = mobileNum;
        this.emailAddr = emailAddr;
        this.midName = midName;
        this.nickName = nickName;
        this.originState = originState;
    }

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