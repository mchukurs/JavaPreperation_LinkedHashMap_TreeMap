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

        //tree map has
        int currentYear = LocalDate.now().getYear();
        LocalDate firstDay = LocalDate.ofYearDay(currentYear, 1);
        LocalDate week1 = firstDay.plusDays(7);
        //map similar to dated purchases map equal to first 7 entries
        //head map excludes the value you pass
        Map<LocalDate, List<Purchase>> week1Purchases = datedPurchases.headMap(week1);
        //tail map includes the value you pass
        Map<LocalDate, List<Purchase>> week2Purchases = datedPurchases.tailMap(week1);
//        System.out.println("-".repeat(20));
//        System.out.println("-".repeat(20));
//        week1Purchases.forEach((k, v) -> System.out.println(k + ": " + v));
//        System.out.println("-".repeat(20));
//        week2Purchases.forEach((k, v) -> System.out.println(k + ": " + v));
//        System.out.println("-".repeat(20));
//        System.out.println("-".repeat(20));
//        datedPurchases.forEach((key, value) -> System.out.println(key + " :" + value));
        displayStats(1,week1Purchases);
        displayStats(2,week2Purchases);
        System.out.println("-".repeat(20));
        //lastKey is lastDate as we keyed using dates
        LocalDate lastDate = datedPurchases.lastKey();
        var previousEntry = datedPurchases.lastEntry();

//        List<Purchase> lastDaysData = previousEntry.getValue();
//        System.out.println(lastDate+ ": "+ lastDaysData.size());

        while(previousEntry !=null){
            List<Purchase> lastDaysData = previousEntry.getValue();
            System.out.println(lastDate+ ": "+ lastDaysData.size());

            LocalDate prevDate = datedPurchases.lowerKey(lastDate);
            previousEntry = datedPurchases.lowerEntry(lastDate);
            lastDate = prevDate;
            
        }

    }
    private static void displayStats(int period, Map<LocalDate, List<Purchase>> periodData){
        System.out.println("-".repeat(20));
        Map<String, Integer> weeklyCounts = new TreeMap<>();
        periodData.forEach((key,value)->{
            System.out.println(key+": "+value);
            for(Purchase p: value){
                weeklyCounts.merge(p.courseId(),1, (prev,current)->{
                    return prev+current;
                });
            }
        });
        System.out.printf("Week %d Purchases = %s%n", period, weeklyCounts);
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