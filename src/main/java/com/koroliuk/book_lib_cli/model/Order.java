package com.koroliuk.book_lib_cli.model;

import java.sql.Date;
import java.time.LocalDate;

public class Order {
    int id;
    Date startTime;
    Date endTime;
    int userId;
    Boolean isReturned;

    public Order(int id, Date startTime, Date endTime, int userId, Boolean isReturned) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.isReturned = isReturned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }
}
