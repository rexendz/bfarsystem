/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import Entities.Account;
import Entities.DatabaseUtil;
import Entities.MyToast;
import Entities.OnGetDataListener;
import Entities.StringUtil;
import Entities.WindowUtils;
import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author guess
 */
public class LoginPanel extends javax.swing.JPanel {

    /**
     * Creates new form login_panel
     */
    private LoginPageListener myListener;
    private Frame MainView;

    public LoginPanel(Frame MainView) {

        initComponents();
        this.MainView = MainView;
    }

    public void addPageListener(LoginPageListener myListener) {
        this.myListener = myListener;
    }

    public interface LoginPageListener {

        public void onSuccessLogin(Account account);

        public void onSignupButtonClicked();
    }

    private void loginAction() {
        Thread t = new Thread(() -> {
            String username = field_username.getText();
            char[] password = field_password.getPassword();
            if (username.isEmpty() || Arrays.toString(password).isEmpty()) {
                MyToast.errorMessage("Please Fill Out The Missing Fields");
            } else {
                WindowUtils.syncWaitCursor(MainView);
                try {
                    SwingUtilities.invokeAndWait(() -> {
                        btn_login.setEnabled(false);
                        btn_signup.setEnabled(false);
                    });
                } catch (InterruptedException | InvocationTargetException ex) {

                }
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseUtil.readDataByUsername(username, ref.child("account"), new OnGetDataListener() {
                    @Override
                    public void dataRetrieved(DataSnapshot dataSnapshot) {
                        Map<String, Object> newPost = (Map<String, Object>) dataSnapshot.getValue();
                        Account account = new Account(newPost);
                        BCrypt.Result result = BCrypt.verifyer().verify(password, account.getPassword());
                        if (result.verified) {
                            if (account.isActivated()) {
                                WindowUtils.defCursor(MainView);
                                MyToast.victoryMessage(MyToast.LoginSuccess);
                                myListener.onSuccessLogin(account);
                            } else {
                                try {
                                    SwingUtilities.invokeAndWait(() -> {
                                        btn_login.setEnabled(true);
                                        btn_signup.setEnabled(true);
                                    });
                                } catch (InterruptedException | InvocationTargetException ex) {
                                }
                                WindowUtils.defCursor(MainView);
                                MyToast.errorMessage("Account not activated");

                            }
                        } else {
                            try {
                                SwingUtilities.invokeAndWait(() -> {
                                    btn_login.setEnabled(true);
                                    btn_signup.setEnabled(true);
                                });
                            } catch (InterruptedException | InvocationTargetException ex) {
                            }
                            WindowUtils.defCursor(MainView);
                            MyToast.errorMessage("Invalid Password");
                        }
                    }

                    @Override
                    public void dataExists(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            try {
                                SwingUtilities.invokeAndWait(() -> {
                                    btn_login.setEnabled(true);
                                    btn_signup.setEnabled(true);
                                });
                            } catch (InterruptedException | InvocationTargetException ex) {
                            }
                            WindowUtils.defCursor(MainView);
                            MyToast.errorMessage("Username does not exist!");
                        }
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }

                });
            }
        });

        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        field_username = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        field_password = new javax.swing.JPasswordField();
        btn_signup = new javax.swing.JButton();
        btn_login = new javax.swing.JButton();

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(1360, 788));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login.png"))); // NOI18N
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setMinimumSize(new java.awt.Dimension(0, 0));
        jLabel2.setPreferredSize(new java.awt.Dimension(1360, 788));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("password");

        field_username.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        field_username.setForeground(new java.awt.Color(51, 51, 51));
        field_username.setPreferredSize(new java.awt.Dimension(6, 35));
        field_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_usernameActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("username");

        field_password.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        field_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_passwordActionPerformed(evt);
            }
        });

        btn_signup.setBackground(new java.awt.Color(255, 255, 255));
        btn_signup.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btn_signup.setForeground(new java.awt.Color(255, 255, 255));
        btn_signup.setText("<HTML>signup</HTML>");
        btn_signup.setContentAreaFilled(false);
        btn_signup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_signup.setDefaultCapable(false);
        btn_signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_signupMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_signupMouseExited(evt);
            }
        });
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });

        btn_login.setBackground(new java.awt.Color(37, 204, 247));
        btn_login.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_login.setForeground(new java.awt.Color(255, 255, 255));
        btn_login.setText("<HTML>login<HTML>");
        btn_login.setContentAreaFilled(false);
        btn_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_login.setDefaultCapable(false);
        btn_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_loginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_loginMouseExited(evt);
            }
        });
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(149, 149, 149)
                            .addComponent(btn_signup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(field_username, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(field_password, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(field_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(field_password, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_signup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jLayeredPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.POPUP_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1155, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(36, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(39, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap(185, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(204, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1155, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 860, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        // TODO add your handling code here:
        myListener.onSignupButtonClicked();
    }//GEN-LAST:event_btn_signupActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        // TODO add your handling code here:
        loginAction();
    }//GEN-LAST:event_btn_loginActionPerformed

    private void field_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_usernameActionPerformed
        // TODO add your handling code here:
        loginAction();
    }//GEN-LAST:event_field_usernameActionPerformed

    private void field_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_passwordActionPerformed
        // TODO add your handling code here:
        loginAction();
    }//GEN-LAST:event_field_passwordActionPerformed

    private void btn_loginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_loginMouseEntered
        // TODO add your handling code here:
        //btn_login.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        btn_login.setText(String.format(StringUtil.underlined, "login"));
    }//GEN-LAST:event_btn_loginMouseEntered

    private void btn_loginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_loginMouseExited
        // TODO add your handling code here:
        btn_login.setText("<HTML>login</HTML>");
    }//GEN-LAST:event_btn_loginMouseExited

    private void btn_signupMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_signupMouseEntered
        btn_signup.setText(String.format(StringUtil.underlined, "signup"));
    }//GEN-LAST:event_btn_signupMouseEntered

    private void btn_signupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_signupMouseExited
        // TODO add your handling code here:
        btn_signup.setText("<HTML>signup</HTML>");
    }//GEN-LAST:event_btn_signupMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_signup;
    private javax.swing.JPasswordField field_password;
    private javax.swing.JTextField field_username;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
