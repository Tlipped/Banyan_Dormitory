package com.banyan_dormitory.model;

import java.sql.Date;
import java.sql.Time;

public class Visitor {
    private String name,visitor_id,phone_number,reason;
    private Date date;
    private Time time;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Visitor(String name, String visitor_id, String phone_number, String reason, Date date,Time time) {
        this.name = name;
        this.visitor_id = visitor_id;
        this.phone_number = phone_number;
        this.reason = reason;
        this.date = date;
        this.time=time;
    }
}
