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

//    @Column(name = "program_details")
//    public ProgramDetails programDetails;

    @Column(name = "program_session_id")
    public String programSessionId;

    @Column(name = "program_id")
    public String programId;

    @Column(name = "program_name")
    public String programName;

    @Column(name = "session_date_time")
    public String dateTime;

    @Column(name = "coach_id")
    public String coachId;

    public Sessions() {
    }

    public Sessions(String sessionName, String programSessionId, String programId, String programName, String dateTime, String coachId) {
        this.sessionName = sessionName;
        this.programSessionId = programSessionId;
        this.programId = programId;
        this.programName = programName;
        this.dateTime = dateTime;
        this.coachId = coachId;
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

    public String getProgramSessionId() {
        return programSessionId;
    }

    public void setProgramSessionId(String programSessionId) {
        this.programSessionId = programSessionId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }
}
