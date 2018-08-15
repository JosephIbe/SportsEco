package com.jjoey.sportseco.models;

public class HistorySessions {

    public String startDateSession;
    public String endDateSession;
    public String progSessionId;
    public String progSessionName;
    public String status;

    public HistorySessions() {
    }

    public HistorySessions(String startDateSession, String endDateSession, String progSessionId, String progSessionName, String status) {
        this.startDateSession = startDateSession;
        this.endDateSession = endDateSession;
        this.progSessionId = progSessionId;
        this.progSessionName = progSessionName;
        this.status = status;
    }

    public String getStartDateSession() {
        return startDateSession;
    }

    public void setStartDateSession(String startDateSession) {
        this.startDateSession = startDateSession;
    }

    public String getEndDateSession() {
        return endDateSession;
    }

    public void setEndDateSession(String endDateSession) {
        this.endDateSession = endDateSession;
    }

    public String getProgSessionId() {
        return progSessionId;
    }

    public void setProgSessionId(String progSessionId) {
        this.progSessionId = progSessionId;
    }

    public String getProgSessionName() {
        return progSessionName;
    }

    public void setProgSessionName(String progSessionName) {
        this.progSessionName = progSessionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
