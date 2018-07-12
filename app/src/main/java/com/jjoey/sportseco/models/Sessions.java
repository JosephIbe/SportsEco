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

    @Column(name = "session_desc")
    public String sessionDesc;

    @Column(name = "session_video_link")
    public String sessionVideoLink;

    @Column(name = "session_image")
    public String sessionCoverImage;

    @Column(name = "session_focus_points")
    public String sessionFocusPoints;

    @Column(name = "session_date_time")
    public String dateTime;

    @Column(name = "coach_id")
    public String coachId;

    public Sessions() {
    }

    public Sessions(int sessionIcon, String sessionName, String programSessionId, String programId, String programName, String sessionDesc, String sessionVideoLink, String sessionCoverImage, String sessionFocusPoints, String dateTime, String coachId) {
        this.sessionIcon = sessionIcon;
        this.sessionName = sessionName;
        this.programSessionId = programSessionId;
        this.programId = programId;
        this.programName = programName;
        this.sessionDesc = sessionDesc;
        this.sessionVideoLink = sessionVideoLink;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionFocusPoints = sessionFocusPoints;
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

    public String getSessionDesc() {
        return sessionDesc;
    }

    public void setSessionDesc(String sessionDesc) {
        this.sessionDesc = sessionDesc;
    }

    public String getSessionVideoLink() {
        return sessionVideoLink;
    }

    public void setSessionVideoLink(String sessionVideoLink) {
        this.sessionVideoLink = sessionVideoLink;
    }

    public String getSessionCoverImage() {
        return sessionCoverImage;
    }

    public void setSessionCoverImage(String sessionCoverImage) {
        this.sessionCoverImage = sessionCoverImage;
    }

    public String getSessionFocusPoints() {
        return sessionFocusPoints;
    }

    public void setSessionFocusPoints(String sessionFocusPoints) {
        this.sessionFocusPoints = sessionFocusPoints;
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
