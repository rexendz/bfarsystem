/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author guess
 */
public class WindowUtils {
    
    public WindowUtils(){
        
    }
    
    public static boolean confirmAction(Frame form, String message){
        boolean yes = false;
        JOptionPane myOption = new JOptionPane();
        myOption.setBackground(Color.WHITE);
        myOption.setOpaque(true);
        int a=myOption.showConfirmDialog(form, message);  
        if(a==JOptionPane.YES_OPTION){
            yes = true;
        }  
        return yes;
    }
    
     public static boolean confirmAction(JDialog form, String message){
        boolean yes = false;
        JOptionPane myOption = new JOptionPane();
        myOption.setBackground(Color.WHITE);
        myOption.setOpaque(true);
        int a=myOption.showConfirmDialog(form, message);  
        if(a==JOptionPane.YES_OPTION){
            yes = true;
        }  
        return yes;
    }
 
     public static void waitCursor(JDialog form){
         form.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    
    public static void defCursor(JDialog form){
        form.setCursor(Cursor.getDefaultCursor());
    }
    
    public static void asyncWaitCursor(JDialog form){
        SwingUtilities.invokeLater(()->{
            form.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        });
    }
    
    public static void asyncDefCursor(JDialog form){
        SwingUtilities.invokeLater(()->{
            form.setCursor(Cursor.getDefaultCursor());
                });
    }
    
    public static void syncDefCursor(JDialog form){
        try {
            SwingUtilities.invokeAndWait(()->{
                form.setCursor(Cursor.getDefaultCursor());
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(WindowUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void syncWaitCursor(JDialog form){
        try {
            SwingUtilities.invokeAndWait(()->{
                form.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(WindowUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public static void waitCursor(Frame form){
         form.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    
    public static void defCursor(Frame form){
        form.setCursor(Cursor.getDefaultCursor());
    }
    
    public static void asyncWaitCursor(Frame form){
        SwingUtilities.invokeLater(()->{
            form.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        });
    }
    
    public static void asyncDefCursor(Frame form){
        SwingUtilities.invokeLater(()->{
            form.setCursor(Cursor.getDefaultCursor());
                });
    }
    
    public static void syncDefCursor(Frame form){
        try {
            SwingUtilities.invokeAndWait(()->{
                form.setCursor(Cursor.getDefaultCursor());
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(WindowUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void syncWaitCursor(Frame form){
        try {
            SwingUtilities.invokeAndWait(()->{
                form.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(WindowUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void resume(Frame form){
        SwingUtilities.invokeLater(()->{
            form.setCursor(Cursor.getDefaultCursor());
            form.setEnabled(true);
        });
    }
    
    public static void pause(Frame form){
        SwingUtilities.invokeLater(()->{
            form.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            form.setEnabled(false);
        });
    }
        public static void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 30; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
}
