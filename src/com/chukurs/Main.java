package com.chukurs;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Main {
    private static Map<String, Purchase> purchases = new LinkedHashMap<>();
    private static NavigableMap<String, Student> students = new TreeMap<>();
    public static void main(String[] args) {
        Course jmc = new Course("jmc101", "Java Master Class", "JAVA");
        Course pymc = new Course("pymc101", "Python Master Class", "PYTHON");

        addPurchase("Matiss Hood", jmc, 99.99);
        addPurchase("Robin Hood", jmc, 159.99);
        addPurchase("Matiss Hood", pymc, 134.99);
        addPurchase("Chris Avatar", pymc, 129.99);
        addPurchase("Anna British", jmc, 89.99);

        purchases.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("-".repeat(20));
        students.forEach((k,v)-> System.out.println(k+": "+v));
    }

    private static void addPurchase(String name, Course course, double price) {
        Student existingStudent = students.get(name);
        if (existingStudent == null) {
            //student does not exist, creating one
            existingStudent = new Student(name, course);
            students.put(name, existingStudent);
        } else {
            existingStudent.addCourse(course);
        }
        //day is setup this way to demonstrate that insertion is ordered
        int day = purchases.size() + 1;
        String key = course.courseId() + "_" + existingStudent.getId();
        int year = LocalDate.now().getYear();
        Purchase purchase = new Purchase(course.courseId(), existingStudent.getId(), price, year, day);
        purchases.put(key, purchase);
    }
}