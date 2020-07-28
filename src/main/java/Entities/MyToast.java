/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;

/**
 *
 * @author guess
 */
public class MyToast {
    
    public static final String LocaldbError = "Connection Error";
    public static final String TitlePlsCheckConnection = "Please check your connection";
    public static final String LoginError = "Login Error";
    public static final String LoginSuccess = "Successfully Logged In";

    public static volatile Queue<Toast> toasts = new LinkedList<>(); 
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "Info: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void errorBox(String errorMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error: " + titleBar, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void errorMessage(String message){
        Toast toast = new Toast(Toast.ERROR, message);
        toast.showToast();

    }
    
    public static void victoryMessage(String message){
        
        Toast toast = new Toast(Toast.SUCCESS, message);
        toast.showToast();
        
    }
    
    public static void infoMessage(String message){
        
        Toast toast = new Toast(Toast.INFO, message);
        toast.showToast();
   }
}