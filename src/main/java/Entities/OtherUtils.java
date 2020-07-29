/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author guess
 */
public class OtherUtils {
    
        
    private static String monthNames[] = {
        "January", "February", "March", 
        "April", "May", "June", "July", 
        "August", "September", "October", 
        "November", "December"};
    
    public static ArrayList<String> getYears(){
        ArrayList<String> years = new ArrayList<>();
        
        for(int x = 2017; x <= 2022; x++){
            years.add(Integer.toString(x));
        }
        
        return years;
    }
   
    public static String[] getMonths(){
        return monthNames;
    }
    
    public static String getMonth(int month){
        return monthNames[month];
    }
    
    public static int getMonthNumber(String month){
        
        int index = 0;
        for(int i = 0; i < monthNames.length; i++){
            if(monthNames[i].equals(month)){
                index = i+1;
            }
        }
        return index;
    }   
    
    public static String getCurrentYear(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        return Integer.toString(calendar.get(Calendar.YEAR));
    }
    
    public static String getCurrentMonth(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        return OtherUtils.getMonth(calendar.get(Calendar.MONTH));
    }
}
