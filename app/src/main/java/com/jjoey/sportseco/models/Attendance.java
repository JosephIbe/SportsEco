package com.jjoey.sportseco.models;

import java.util.Date;

public class Attendance {

    public Date attendanceDate;
    public Player player;

    public Attendance() {
    }

    public Attendance(Date attendanceDate, Player player) {
        this.attendanceDate = attendanceDate;
        this.player = player;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
