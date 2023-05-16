package com.koroliuk.book_lib_cli.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Order {
    int id;
    Date startTime;
    Date endTime;
    int userId;
    int bookId;
    Boolean isReturned;

    public Order(int id, Date startTime, Date endTime, int userId, int bookId, Boolean isReturned) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.bookId = bookId;
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Order other = (Order) obj;
        return id == other.id &&
                Objects.equals(startTime, other.startTime) &&
                Objects.equals(endTime, other.endTime) &&
                userId == other.userId &&
                bookId == other.bookId &&
                isReturned == other.isReturned;    
    }
}
