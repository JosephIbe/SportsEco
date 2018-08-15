package com.jjoey.sportseco.models;

import java.util.Date;

public class Attendance {

    public Date attendanceDate;
    public PlayerSession playerSession;
    public boolean isPresent;

    public Attendance() {
    }

    public Attendance(Date attendanceDate, PlayerSession playerSession, boolean isPresent) {
        this.attendanceDate = attendanceDate;
        this.playerSession = playerSession;
        this.isPresent = isPresent;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public PlayerSession getPlayerSession() {
        return playerSession;
    }

    public void setPlayerSession(PlayerSession playerSession) {
        this.playerSession = playerSession;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

}
