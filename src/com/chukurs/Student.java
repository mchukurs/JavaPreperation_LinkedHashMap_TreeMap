package com.chukurs;

import java.time.LocalDate;

record Course(String courseId, String name, String subject){

}
record Purchase(String courseId, int stdentId, double price, int yr, int dayOfYear){
//yr= year of purchase
//dayOfYear = used to get the date
    public LocalDate purchaseDate(){
        //will be used as a KEY in the map
        return LocalDate.ofYearDay(yr,dayOfYear);
    }
}

public class Student {
}
