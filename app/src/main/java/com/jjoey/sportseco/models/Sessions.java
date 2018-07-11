package com.jjoey.sportseco.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "sessions")
public class Sessions extends Model {

    @Column(name = "session_icon")
    public int sessionIcon;

    @Column(name = "session_name")
    public String sessionName;

    @Column(name = "program_details")
    public ProgramDetails programDetails;

    @Column(name = "session_date_time")
    public String dateTime;

    public Sessions() {
    }

    public Sessions(int sessionIcon, String sessionName, ProgramDetails programDetails, String progId, String dateTime) {
        this.sessionIcon = sessionIcon;
        this.sessionName = sessionName;
        this.programDetails = programDetails;
        this.dateTime = dateTime;
    }

    public int getSessionIcon() {
        return sessionIcon;
    }

    public void setSessionIcon(int sessionIcon) {
        this.sessionIcon = sessionIcon;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public ProgramDetails getProgramDetails() {
        return programDetails;
    }

    public void setProgramDetails(ProgramDetails programDetails) {
        this.programDetails = programDetails;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
