package com.university.model;

import java.time.LocalTime;
import java.util.Objects;

public class PairTime {
    private int pairNumber;
    private LocalTime startTime;
    private LocalTime endTime;

    public PairTime(int pairNumber, LocalTime startTime, LocalTime endTime) {
        this.pairNumber = pairNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public PairTime() {
    }

    public int getPairNumber() {
        return pairNumber;
    }

    public void setPairNumber(int pairNumber) {
        this.pairNumber = pairNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairTime pairTime = (PairTime) o;
        return pairNumber == pairTime.pairNumber && Objects.equals(startTime, pairTime.startTime) && Objects.equals(endTime, pairTime.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pairNumber, startTime, endTime);
    }

    @Override
    public String toString() {
        return "PairTime{" +
                "pairNumber=" + pairNumber +
                ", studyStartTime=" + startTime +
                ", studyEndTime=" + endTime +
                '}';
    }
}
