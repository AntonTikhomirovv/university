package com.university.model;

import java.util.Objects;

public class Audience {
    private int id;
    private int campusNumber;
    private int roomNumber;

    public Audience(int id, int campusNumber, int roomNumber) {
        this.id = id;
        this.campusNumber = campusNumber;
        this.roomNumber = roomNumber;
    }

    public Audience() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampusNumber() {
        return campusNumber;
    }

    public void setCampusNumber(int campusNumber) {
        this.campusNumber = campusNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audience audience = (Audience) o;
        return id == audience.id && campusNumber == audience.campusNumber && roomNumber == audience.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, campusNumber, roomNumber);
    }

    @Override
    public String toString() {
        return "Audience{" +
                "id=" + id +
                ", campusNumber=" + campusNumber +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
