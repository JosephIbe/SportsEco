package com.jjoey.sportseco.models;

import com.jjoey.sportseco.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * Task class (extends Event)
 * @author albert.suarez.molgo
 */
public class Task extends Event {

    private String title;
    private Date dateStart;
    private Date dateEnd;
    private boolean done;

    private String meetingAssociated;


    public Task() {}

    public Task(long id, String title, int dayStart, int monthStart, int yearStart, int dayEnd, int monthEnd, int yearEnd) {
        super();
        this.id = id;
        this.title = title;
        this.dateStart = DateUtils.createDate(dayStart, monthStart, yearStart);
        this.dateEnd = DateUtils.createDate(dayEnd, monthEnd, yearEnd);
        this.done = false;
    }

    public Task(String title, int dayStart, int monthStart, int yearStart, int dayEnd, int monthEnd, int yearEnd) {
        super();
        this.title = title;
        this.dateStart = DateUtils.createDate(dayStart, monthStart, yearStart);
        this.dateEnd = DateUtils.createDate(dayEnd, monthEnd, yearEnd);
        this.done = false;
    }

    public Task(String title, Date dateStart, Date dateEnd) {
        super();
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.done = false;
    }

    public Task(long id, String title, Date dateStart, Date dateEnd) {
        super();
        this.id = id;
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.done = false;
    }

    public Task(long id, String title, Date dateStart, Date dateEnd, boolean done, String meetingAssociated) {
        super();
        this.id = id;
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.done = done;
        this.meetingAssociated = meetingAssociated;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(int day, int month, int year) {
        this.dateStart = DateUtils.createDate(day, month, year);
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(int day, int month, int year) {
        this.dateEnd = DateUtils.createDate(day, month, year);
    }

    public String getTitle() {
        return title;
    }

    public int getStartDay() {
        return DateUtils.getDay(dateStart);
    }

    public int getStartMonth() {
        return DateUtils.getMonth(dateStart);
    }

    public int getStartYear() {
        return DateUtils.getYear(dateStart);
    }

    public int getEndDay() {
        return DateUtils.getDay(dateEnd);
    }

    public int getEndMonth() {
        return DateUtils.getMonth(dateEnd);
    }

    public int getEndYear() {
        return DateUtils.getYear(dateEnd);
    }

    public List<Date> getAllDays() {
        List<Date> list = new LinkedList<>();
        Date d = dateStart;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        while(!DateUtils.isSameDay(d, dateEnd)) {
            list.add(d);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            d = calendar.getTime();
        }
        list.add(dateEnd);
        return list;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getMeetingAssociated() {
        return meetingAssociated;
    }

    public void setMeetingAssociated(String meetingAssociated) {
        this.meetingAssociated = meetingAssociated;
    }

    public boolean hasAMeetingAssociated() {
        return !this.meetingAssociated.equals("");
    }

    @Override
    public String toString() {
        String result = "";
        result += "Title: " + this.title + "\n";
        result += "Start date: " + DateUtils.dateToString(dateStart);
        result += "End date: " + DateUtils.dateToString(dateEnd);
        if (done) result += "DONE" + "\n";
        else result += "NOT DONE" + "\n";
        if (hasAMeetingAssociated())
            result += "Associated with a meeting with \'" + meetingAssociated + "\' description \n";
        else
            result += "It is not associated with a meeting \n";
        return result;
    }
}
