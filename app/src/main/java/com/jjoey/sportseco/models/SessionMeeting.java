package com.jjoey.sportseco.models;

import com.jjoey.sportseco.utils.DateUtils;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Meeting class (extends Event)
 * @author albert.suarez.molgo
 */
public class SessionMeeting extends Event {

    private Date dateStart;

    private Date dateEnd;

    private String description;

    private Set<Task> tasks;


    public SessionMeeting() {}

    public SessionMeeting(long id, int day, int month, int year, int hourStart, int minuteStart, int hourEnd, int minuteEnd, String description) {
        super();
        super.eventType = EventType.EVENT_MEETING;
        this.id = id;
        Date d = DateUtils.createDate(day, month, year);
        this.dateStart = DateUtils.createHour(d, hourStart, minuteStart);
        this.dateEnd = DateUtils.createHour(d, hourEnd, minuteEnd);
        this.description = description;
        this.tasks = new LinkedHashSet<>();
    }

    public SessionMeeting(int day, int month, int year, int hourStart, int minuteStart, int hourEnd, int minuteEnd, String description) {
        super();
        super.eventType = EventType.EVENT_MEETING;
        Date d = DateUtils.createDate(day, month, year);
        this.dateStart = DateUtils.createHour(d, hourStart, minuteStart);
        this.dateEnd = DateUtils.createHour(d, hourEnd, minuteEnd);
        this.description = description;
        this.tasks = new LinkedHashSet<>();
    }

    public SessionMeeting(Date dateStart, Date dateEnd, String description) {
        super();
        super.eventType = EventType.EVENT_MEETING;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
        this.tasks = new LinkedHashSet<>();
    }

    public SessionMeeting(long id, Date dateStart, Date dateEnd, String description) {
        super();
        super.eventType = EventType.EVENT_MEETING;
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
        this.tasks = new LinkedHashSet<>();
    }

    public int getDay() {
        return DateUtils.getDay(dateStart);
    }

    public int getMonth() {
        return DateUtils.getMonth(dateStart);
    }

    public int getYear() {
        return DateUtils.getYear(dateStart);
    }

    public int getStartHour() {
        return DateUtils.getHour(dateStart);
    }

    public int getStartMinute() {
        return DateUtils.getMinute(dateStart);
    }

    public int getEndHour() {
        return DateUtils.getHour(dateEnd);
    }

    public int getEndMinute() {
        return DateUtils.getMinute(dateEnd);
    }

    public Date getDate() {
        return dateStart;
    }

    public int getTotalStartMinute() {
        int hour = DateUtils.getHour(dateStart);
        int minute = DateUtils.getMinute(dateStart);
        return hour*60+minute;
    }

    public int getTotalEndMinute() {
        int hour = DateUtils.getHour(dateEnd);
        int minute = DateUtils.getMinute(dateEnd);
        return hour*60+minute;
    }

    public void setDate(int day, int month, int year) {
        int hourStart, minuteStart, hourEnd, minuteEnd;
        hourStart = DateUtils.getHour(dateStart);
        hourEnd = DateUtils.getMinute(dateEnd);
        minuteStart = DateUtils.getHour(dateStart);
        minuteEnd = DateUtils.getMinute(dateEnd);
        Date date = DateUtils.createDate(day, month, year);
        this.dateStart = DateUtils.createHour(date, hourStart, minuteStart);
        this.dateEnd = DateUtils.createHour(date, hourEnd, minuteEnd);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getHourStart() {
        return dateStart;
    }

    public void setHourStart(int hour, int minute) {
        this.dateStart = DateUtils.createHour(this.dateStart, hour, minute);
    }

    public Date getHourEnd() {
        return dateEnd;
    }

    public void setHourEnd(int hour, int minute) {
        this.dateEnd = DateUtils.createHour(this.dateEnd, hour, minute);
    }

    public void addTask(Task t) {
        tasks.add(t);
        t.setMeetingAssociated(description);
    }

    public void eraseTask(Task t) {
        tasks.remove(t);
        t.setMeetingAssociated(null);
    }

    @Override
    public String toString() {
        String result = "";
        result += "Date: " + DateUtils.dateToString(dateStart);
        result += "Description: " + this.description + "\n";
        result += "Start hour: " + DateUtils.hourToString(dateStart);
        result += "End hour: " + DateUtils.hourToString(dateEnd);
        return result;
    }
}
