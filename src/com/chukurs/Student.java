package com.chukurs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

record Course(String courseId, String name, String subject) {

}

record Purchase(String courseId, int studentId, double price, int yr, int dayOfYear) {
    //yr= year of purchase
//dayOfYear = used to get the date
    public LocalDate purchaseDate() {
        //will be used as a KEY in the map
        return LocalDate.ofYearDay(yr, dayOfYear);
    }
}

public class Student {
    public static int lastId = 1;
    private String name;
    private int id;
    private List<Course> courseList;


    public Student(String name, List<Course> courseList) {
        this.name = name;
        this.courseList = courseList;
        this.id = lastId++;
    }


    public Student(String name, Course course) {
        this(name, new ArrayList<>(List.of(course)));

    }

    public void addCourse(Course course) {
        this.courseList.add(course);
    }

    @Override
    public String toString() {
        String[] courseNames = new String[courseList.size()];
        Arrays.setAll(courseNames, (int i) -> courseList.get(i).name());
        return "[%d] : %s".formatted(id, String.join(", ", courseNames));
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
