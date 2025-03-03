package com.jadie.chapter07;

import java.util.Collections;
import java.util.List;

public class Person {
    private List<Course> courses;
    public List<Course> getCourse() {
        return courses;
    }
    public void setCourse(List<Course> aList) {
        this.courses = aList;
    }


    public List<Course> getCourses() {
        return List.copyOf(courses);
    }
    public void addCourse(Course aCourse) {
        courses.add(aCourse);
    }
    public void removeCourse(Course aCourse) {
        courses.remove(aCourse);
    }
}
