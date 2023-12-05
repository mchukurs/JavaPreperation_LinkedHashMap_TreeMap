package com.chukurs;

import java.time.LocalDate;
import java.util.*;

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

        addPurchase("Andrew Grave", jmc, 99.99);
        addPurchase("Austin Bowl", jmc, 159.99);
        addPurchase("Johny Vendor", pymc, 134.99);
        addPurchase("Astrid West", pymc, 129.99);
        addPurchase("Trianna Brews", jmc, 89.99);

        purchases.forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("-".repeat(20));
        students.forEach((k, v) -> System.out.println(k + ": " + v));

        NavigableMap<LocalDate, List<Purchase>> datedPurchases = new TreeMap<>();
        for (Purchase p : purchases.values()) {
            //executing .compute() on TreeMap
            datedPurchases.compute(p.purchaseDate(),
                    (pdate, plist) -> {
                        List<Purchase> list = (plist == null) ? new ArrayList<>() : plist;
                        list.add(p);
                        return list;
                    });
        }
        datedPurchases.forEach((key,value)-> System.out.println(key+" :"+ value));
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
        //Jan 1st to Jan 4th
        int day = new Random().nextInt(1, 15);
        //interested to see sales for each day, so map is created

        String key = course.courseId() + "_" + existingStudent.getId();
        int year = LocalDate.now().getYear();
        Purchase purchase = new Purchase(course.courseId(), existingStudent.getId(), price, year, day);
        purchases.put(key, purchase);
    }
}