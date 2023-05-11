package com.koroliuk.book_lib_cli.model;

import java.time.LocalDate;

public class Order {
    int id;
    LocalDate startTime;
    LocalDate endTime;
    int userId;
    Boolean isReturned;

    public Order(int id, LocalDate startTime, LocalDate endTime, int userId, Boolean isReturned) {
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

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
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
