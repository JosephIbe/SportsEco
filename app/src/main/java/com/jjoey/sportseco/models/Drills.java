package com.jjoey.sportseco.models;

public class Drills {

    public String progId;
    public String progSessId;
    public String progSessionStepsId;
    public String drill_Title;
    public String drill_CoverImg;
    public String drillDesc;
    public String drillVideo;
    public String drillFocusPts;
    public String drillStartTime;
    public String drillEndTime;

    public Drills() {
    }

    public Drills(String progId, String progSessId, String progSessionStepsId, String drill_Title, String drill_CoverImg, String drillDesc, String drillVideo, String drillFocusPts, String drillStartTime, String drillEndTime) {
        this.progId = progId;
        this.progSessId = progSessId;
        this.progSessionStepsId = progSessionStepsId;
        this.drill_Title = drill_Title;
        this.drill_CoverImg = drill_CoverImg;
        this.drillDesc = drillDesc;
        this.drillVideo = drillVideo;
        this.drillFocusPts = drillFocusPts;
        this.drillStartTime = drillStartTime;
        this.drillEndTime = drillEndTime;
    }

    public String getProgId() {
        return progId;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }

    public String getProgSessId() {
        return progSessId;
    }

    public void setProgSessId(String progSessId) {
        this.progSessId = progSessId;
    }

    public String getProgSessionStepsId() {
        return progSessionStepsId;
    }

    public void setProgSessionStepsId(String progSessionStepsId) {
        this.progSessionStepsId = progSessionStepsId;
    }

    public String getDrill_Title() {
        return drill_Title;
    }

    public void setDrill_Title(String drill_Title) {
        this.drill_Title = drill_Title;
    }

    public String getDrill_CoverImg() {
        return drill_CoverImg;
    }

    public void setDrill_CoverImg(String drill_CoverImg) {
        this.drill_CoverImg = drill_CoverImg;
    }

    public String getDrillDesc() {
        return drillDesc;
    }

    public void setDrillDesc(String drillDesc) {
        this.drillDesc = drillDesc;
    }

    public String getDrillVideo() {
        return drillVideo;
    }

    public void setDrillVideo(String drillVideo) {
        this.drillVideo = drillVideo;
    }

    public String getDrillFocusPts() {
        return drillFocusPts;
    }

    public void setDrillFocusPts(String drillFocusPts) {
        this.drillFocusPts = drillFocusPts;
    }

    public String getDrillStartTime() {
        return drillStartTime;
    }

    public void setDrillStartTime(String drillStartTime) {
        this.drillStartTime = drillStartTime;
    }

    public String getDrillEndTime() {
        return drillEndTime;
    }

    public void setDrillEndTime(String drillEndTime) {
        this.drillEndTime = drillEndTime;
    }
}
