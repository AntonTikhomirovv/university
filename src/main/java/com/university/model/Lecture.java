package com.university.model;
import java.util.List;
import java.util.Objects;

public class Lecture {
    private int id;
    private int audience;
    private int teacher;
    private int pairTime;

    public Lecture(int id, int audience, int teacher, int pairTime) {
        this.id = id;
        this.audience = audience;
        this.teacher = teacher;
        this.pairTime = pairTime;
    }

    public Lecture() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    public int getTeacher() {
        return teacher;
    }

    public void setTeacher(int teacher) {
        this.teacher = teacher;
    }

    public int getPairTime() {
        return pairTime;
    }

    public void setPairTime(int pairTime) {
        this.pairTime = pairTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return id == lecture.id && Objects.equals(audience, lecture.audience) && Objects.equals(teacher, lecture.teacher) && Objects.equals(pairTime, lecture.pairTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audience, teacher, pairTime);
    }
}
