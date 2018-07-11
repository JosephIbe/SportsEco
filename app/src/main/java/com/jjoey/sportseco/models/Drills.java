package com.jjoey.sportseco.models;

public class Drills {

    public int drill_Id;
    public String drill_Title;
    public String drill_CoverImg;
    public String[] imageURLs;
    public double duration;
    private Sessions sessions;

    public Drills() {
    }

    public Drills(int drill_Id, String drill_Title, String drill_CoverImg, String[] imageURLs, double duration, Sessions sessions) {
        this.drill_Id = drill_Id;
        this.drill_Title = drill_Title;
        this.drill_CoverImg = drill_CoverImg;
        this.imageURLs = imageURLs;
        this.duration = duration;
        this.sessions = sessions;
    }

    public int getDrill_Id() {
        return drill_Id;
    }

    public void setDrill_Id(int drill_Id) {
        this.drill_Id = drill_Id;
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

    public String[] getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(String[] imageURLs) {
        this.imageURLs = imageURLs;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Sessions getSessions() {
        return sessions;
    }

    public void setSessions(Sessions sessions) {
        this.sessions = sessions;
    }
}
