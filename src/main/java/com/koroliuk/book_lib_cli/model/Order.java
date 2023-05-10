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

    public LocalDate getStart_time() {
        return startTime;
    }

    public void setStart_time(LocalDate start_time) {
        this.startTime = start_time;
    }

    public LocalDate getEnd_time() {
        return endTime;
    }

    public void setEnd_time(LocalDate end_time) {
        this.endTime = end_time;
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
