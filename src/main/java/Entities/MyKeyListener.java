/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author guess
 */
public class MyKeyListener implements KeyListener{
    
    public final static int NumberOnly = 1;
    public final static int NumberAndDecimalOnly = 2;
    private int mode;
    
    public MyKeyListener(int mode){
        this.mode = mode;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        switch(mode){
            case NumberAndDecimalOnly:
            {
                char c = e.getKeyChar();
                if(!(Character.isDigit(c)||c=='.' || c==KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_DECIMAL || c == KeyEvent.VK_PERIOD)){
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    tk.beep();
                    e.consume();
                }
                break;
            }
            case NumberOnly:
            {
                char c = e.getKeyChar();
                if(!(Character.isDigit(c))){
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    tk.beep();
                    e.consume();
                }
                break;
            }
            default:
                break;
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
