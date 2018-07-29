package com.jjoey.sportseco.models;

import java.io.Serializable;

public class Event implements Serializable {

    protected long id;
    protected Reminder reminder;

    public Event() {
    }

    public long getId() {
        return id;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return null;
    }

}
