/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author guess
 */
public class RequiredFields {
    
    private ArrayList<Component> components;
    
    public RequiredFields(){
        components = new ArrayList<Component>();
    }
    
    public void add(Component component){
        components.add(component);
    }
    
    public boolean isAllFilled(){
        for(Component component: components){
            if(component instanceof JTextField){
                if(((JTextField) component).getText().isEmpty()){
                    return false;
                }
            }else if(component instanceof JComboBox){
                if(((JComboBox) component).getSelectedIndex()==-1){
                    return false;
                }
            }else if(component instanceof JPasswordField){
                if(((JPasswordField) component).getPassword().length<=0){
                    return false;
                }
            }
        }
        return true;
    }
}
