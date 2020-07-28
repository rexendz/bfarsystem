/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author guess
 */
public class Toast extends javax.swing.JFrame {

    /**
     * Creates new form Toast
     */
    public Toast() {
        initComponents();
    }
    
    public static Color RED = Color.decode("#eb3b5a");
    public static Color GREEN = Color.decode("#20bf6b");
    public static Color BLUE = Color.decode("#45aaf2");
    
    public static final int ERROR = 1;
    public static final int SUCCESS = 2;
    public static final int INFO = 3;
    
    public static final int TOAST = 1;
    public static final int STATIC = 2;
    
    private int type;
    private String message;
    private int mode;
    
    private static int STATIC_Number = -1;
    public static int TOAST_Number = -1;
    
    
    
    public Toast(int type, String message) {
        initComponents();
        this.type = type;
        this.message = message;
        this.mode = TOAST;
        initialize();
    }
    
    public Toast(int type, String message, int mode) {
        initComponents();
        this.type = type;
        this.message = message;
        this.mode = mode;
        initialize();
    }
    
    public void displayToast(){
        Thread t = new Thread(()->{
            try {
                SwingUtilities.invokeAndWait(()->fadein());
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.getLogger(Toast.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
    }
    
  
    
    public void closeToast(){
        Thread t = new Thread(()->{
            try {
                SwingUtilities.invokeAndWait(()->fadeout());
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.getLogger(Toast.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
    }
    
    public void showToast(){
        Thread t = new Thread(()->{
//            try {
//                SwingUtilities.invokeAndWait(()->fadein());
//            } catch (InterruptedException | InvocationTargetException ex) {
//                Logger.getLogger(Toast.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
            fadein();
            pauseFrame(1500);
            fadeout();
//            try {
//                SwingUtilities.invokeAndWait(()->fadeout());
//            } catch (InterruptedException | InvocationTargetException ex) {
//                Logger.getLogger(Toast.class.getName()).log(Level.SEVERE, null, ex);
//            }
        });
        t.start();
    }
    
    public void setText(String text){
        SwingUtilities.invokeLater(()->label_content.setText(text));
    }
    
    private void initialize(){
        
        label_content.setText(message);
        ihandaAngUI();
        this.setVisible(true);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
       
        if(mode == TOAST){
            Toast.TOAST_Number ++;
            int x = (dim.width-w)/2;
            int y = (dim.height-h)-125; 
            this.setLocation(x, y-(60*Toast.TOAST_Number));

        }else{
            Toast.STATIC_Number ++;
            int x = (dim.width-w)/2;
            int y = (dim.height-h)/2; 
            this.setLocation(x, y+((60*Toast.STATIC_Number)-200));
        }
    }
  
    
    public void ipakita(){
        
        switch(mode){
            case TOAST:
                fadein();
                pauseFrame(700);
                fadeout();
                break;
            case STATIC:
                fadein();
                fadeout();
        }
        
    }
    
    public void pauseFrame(int milliseconds){
        try {
            
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(Toast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void fadein(){
        for (double d = 0.0; d < 1; d += 0.1) { 
            try { 
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Toast.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            this.setOpacity((float) d);
        }     
    }
    
    
    
    private void fadeout(){
        try{
            
            for (double d = 0.85; d > 0.2; d -= 0.1) { 
                try { 
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Toast.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.setOpacity((float) d);
            } 

            if(mode == STATIC){
                Toast.STATIC_Number--;
            }else{
                Toast.TOAST_Number--;
            }
            this.dispose();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ihandaAngUI(){
        switch(type){
            case ERROR:
                setError();
                break;
            case SUCCESS:
                setSuccess();
                break;
            case INFO:
                setInfo();
                break;
            default:
                break;

        }

    }
    
    public void setError(){
        label_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/error.png")));
        pnl_main.setBackground(RED);

    }
    
    public void setSuccess(){
        label_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/success.png")));
        pnl_main.setBackground(GREEN);

    }
    
    public void setInfo(){
        label_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info.png")));
        pnl_main.setBackground(BLUE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_main = new javax.swing.JPanel();
        label_icon = new javax.swing.JLabel();
        label_content = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setOpacity(0.0F);
        setSize(new java.awt.Dimension(741, 61));
        setType(java.awt.Window.Type.POPUP);

        pnl_main.setBackground(new java.awt.Color(235, 59, 90));

        label_icon.setMinimumSize(new java.awt.Dimension(50, 14));
        label_icon.setPreferredSize(new java.awt.Dimension(50, 50));

        label_content.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        label_content.setForeground(new java.awt.Color(255, 255, 255));
        label_content.setText("asdadasdal;kdasdasd");

        javax.swing.GroupLayout pnl_mainLayout = new javax.swing.GroupLayout(pnl_main);
        pnl_main.setLayout(pnl_mainLayout);
        pnl_mainLayout.setHorizontalGroup(
            pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_mainLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(label_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_content, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_mainLayout.setVerticalGroup(
            pnl_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_mainLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(label_content)
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(label_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_main, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel label_content;
    private javax.swing.JLabel label_icon;
    private javax.swing.JPanel pnl_main;
    // End of variables declaration//GEN-END:variables
}
