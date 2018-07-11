package com.jjoey.sportseco.models;

public class DrillSubItem {

    private Drills drills;
    public String descriptionTxt;
    public String timerTxt;

    public DrillSubItem() {
    }

    public DrillSubItem(Drills drills, String descriptionTxt, String timerTxt) {
        this.drills = drills;
        this.descriptionTxt = descriptionTxt;
        this.timerTxt = timerTxt;
    }

    public Drills getDrills() {
        return drills;
    }

    public void setDrills(Drills drills) {
        this.drills = drills;
    }

    public String getDescriptionTxt() {
        return descriptionTxt;
    }

    public void setDescriptionTxt(String descriptionTxt) {
        this.descriptionTxt = descriptionTxt;
    }

    public String getTimerTxt() {
        return timerTxt;
    }

    public void setTimerTxt(String timerTxt) {
        this.timerTxt = timerTxt;
    }
}
