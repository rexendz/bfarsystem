/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DialogForms;

import Entities.Account;
import Entities.DatabaseUtil;
import Entities.MyToast;
import Entities.OnGetDataListener;
import Entities.WindowUtils;
import Panels.MainPanel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author guess
 */
public class AdminDialogForm extends javax.swing.JDialog {

    /**
     * Creates new form AdminDialogForm
     */
    private Frame MainView;
    private final int approvedAccounts = 1;
    private final int unapprovedAccounts = 2;
    private int mode = approvedAccounts;
    private Account userAccount;
    private ArrayList<Account> accountList;

    public AdminDialogForm(java.awt.Frame parent, boolean modal, Account account) {
        super(parent, modal);
        initComponents();
        userAccount = account;
        this.MainView = parent;
        retrieveAccountsFromDatabase();
    }

    private void populateIfExists() {
        ArrayList<Account> filteredList = new ArrayList<>();
        Thread t = new Thread(() -> {
            do {
            } while (accountList == null);
            accountList.forEach(account -> {
                if (account.isActivated() && mode == approvedAccounts) {
                    filteredList.add(account);
                } else if (!account.isActivated() && mode == unapprovedAccounts) {
                    filteredList.add(account);
                }
            });
            populateAccountList(filteredList);
        });
        t.start();
    }

    private void retrieveAccountsFromDatabase() {
        accountList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("account");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot snap : ds.getChildren()) {
                    accountList.add(snap.getValue(Account.class));
                }
                populateIfExists();
            }

            @Override
            public void onCancelled(DatabaseError de) {

            }

        });

    }

    private void populateAccountList(ArrayList<Account> accounts) {
        pnl_profilelist.removeAll();
        try {
            SwingUtilities.invokeAndWait(() -> {
                for (int i = 0; i < accounts.size(); i++) {
                    createCard(i, accounts.get(i));
                }
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        pnl_profilelist.revalidate();
        pnl_profilelist.repaint();
    }

    private void createCard(int index, Account account) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("account");

        JPanel card = new JPanel();

        card.setBackground(new java.awt.Color(255, 255, 255));
        card.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        card.setPreferredSize(new java.awt.Dimension(310, 150));
        card.setMaximumSize(new Dimension(310, 150));

        //card.setLayout(new java.awt.BorderLayout());
        card.setLayout(new java.awt.GridBagLayout());
        GridBagConstraints labCnst = new GridBagConstraints();
        labCnst.insets = new Insets(3, 10, 3, 10);

        labCnst.weightx = 1.5;
        labCnst.weighty = 0;
        labCnst.gridx = 0;
        labCnst.gridy = 0;
        labCnst.gridwidth = 2;
        labCnst.anchor = GridBagConstraints.FIRST_LINE_END;
        String status = "";
        Color color = null;
        if (account.isActivated()) {
            status = " APPROVED  ";
            color = Color.decode("#26de81");
        } else {
            status = "  PENDING  ";
            color = Color.decode("#fc5c65");
        }
        JLabel activeLabel = createLabel(status);
        activeLabel.setBackground(color);
        activeLabel.setOpaque(true);
        activeLabel.setForeground(Color.WHITE);
        activeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        activeLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        activeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        card.add(activeLabel, labCnst);

        labCnst.gridx = 0;
        labCnst.gridy = 1;
        labCnst.gridwidth = 2;
        labCnst.fill = GridBagConstraints.HORIZONTAL;
        labCnst.anchor = GridBagConstraints.FIRST_LINE_START;
        JLabel accName = createLabel("Name: " + account.getFullname());

        card.add(accName, labCnst);

        labCnst.gridy = 2;

        JLabel nameLabel = createLabel("Username: " + account.getUsername());

        card.add(nameLabel, labCnst);

        labCnst.fill = GridBagConstraints.NONE;
        labCnst.gridwidth = 1;

        if (mode == approvedAccounts) {

            labCnst.gridy = 3;
            labCnst.anchor = GridBagConstraints.LINE_START;

            JButton deactivateBtn = new JButton();
            deactivateBtn.setBackground(Color.decode("#fc5c65"));
            deactivateBtn.setForeground(new java.awt.Color(255, 255, 255));
            deactivateBtn.setText("Deactivate Account");
            deactivateBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            deactivateBtn.addActionListener((ActionEvent e) -> {
                if (!account.getUsername().equals("elmer")) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure to deactivate this account?") != 0) {
                        return;
                    }
                    WindowUtils.waitCursor(MainView);
                    DatabaseUtil.readDataByUsername(account.getUsername(), ref, new OnGetDataListener() {
                        @Override
                        public void dataRetrieved(DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("activated").setValue(false, null);
                            dataSnapshot.getRef().child("admin").setValue(false, null);
                            MyToast.victoryMessage(account.getUsername() + " has been deactivated");
                            retrieveAccountsFromDatabase();
                            WindowUtils.defCursor(MainView);
                        }

                        @Override
                        public void dataExists(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                } else {
                    MyToast.errorMessage("Cannot Modify Root Account");
                }
            });

            card.add(deactivateBtn, labCnst);

            labCnst.gridx = 1;
            labCnst.anchor = GridBagConstraints.LINE_END;

            JButton grantBtn = new JButton();
            grantBtn.setBackground(Color.decode("#45aaf2"));
            grantBtn.setForeground(new java.awt.Color(255, 255, 255));
            grantBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            if (!account.isAdmin()) {
                grantBtn.setText("Make Admin");
            } else {
                grantBtn.setText("Remove as Admin");
            }
            grantBtn.addActionListener((ActionEvent e) -> {
                if (!account.getUsername().equals("elmer")) {
                    WindowUtils.waitCursor(MainView);
                    if (!account.isAdmin()) {
                        if (JOptionPane.showConfirmDialog(null, "Are you sure to grant this account admin privileges?") != 0) {
                            return;
                        }
                        DatabaseUtil.readDataByUsername(account.getUsername(), ref, new OnGetDataListener() {
                            @Override
                            public void dataRetrieved(DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("admin").setValue(true, null);
                                MyToast.victoryMessage(account.getUsername() + " has been granted admin privileges");
                                retrieveAccountsFromDatabase();
                                WindowUtils.defCursor(MainView);
                            }

                            @Override
                            public void dataExists(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    } else {
                        if (JOptionPane.showConfirmDialog(null, "Are you sure to remove this account's admin privileges?") != 0) {
                            return;
                        }
                        DatabaseUtil.readDataByUsername(account.getUsername(), ref, new OnGetDataListener() {
                            @Override
                            public void dataRetrieved(DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().child("admin").setValue(false, null);
                                MyToast.victoryMessage(account.getUsername() + " has been stripped of admin privileges");
                                retrieveAccountsFromDatabase();
                                WindowUtils.defCursor(MainView);
                            }

                            @Override
                            public void dataExists(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                } else {
                    MyToast.errorMessage("Cannot Modify Root Account");
                }
            });

            card.add(grantBtn, labCnst);

        } else if (mode == unapprovedAccounts) {

            labCnst.gridy = 3;
            labCnst.anchor = GridBagConstraints.LINE_START;

            JButton deleteRequestBtn = new JButton();
            deleteRequestBtn.setBackground(Color.decode("#fc5c65"));
            deleteRequestBtn.setForeground(new java.awt.Color(255, 255, 255));
            deleteRequestBtn.setText("Delete Request");
            deleteRequestBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            deleteRequestBtn.addActionListener((ActionEvent e) -> {
                if (JOptionPane.showConfirmDialog(null, "Are you sure to delete this request?") != 0) {
                    return;
                }
                WindowUtils.waitCursor(MainView);
                DatabaseUtil.readDataByUsername(account.getUsername(), ref, new OnGetDataListener() {
                    @Override
                    public void dataRetrieved(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().removeValue(null);
                        MyToast.victoryMessage(account.getUsername() + " has been deleted");
                        retrieveAccountsFromDatabase();
                        WindowUtils.defCursor(MainView);
                    }

                    @Override
                    public void dataExists(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            });

            card.add(deleteRequestBtn, labCnst);
            labCnst.gridx = 1;
            labCnst.gridy = 3;
            labCnst.anchor = GridBagConstraints.LINE_END;

            JButton approveBtn = new JButton();
            approveBtn.setBackground(Color.decode("#45aaf2"));
            approveBtn.setForeground(new java.awt.Color(255, 255, 255));
            approveBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            approveBtn.setText("Activate Account");

            approveBtn.addActionListener((ActionEvent e) -> {
                if (JOptionPane.showConfirmDialog(null, "Are you sure to activate this account?") != 0) {
                    return;
                }
                WindowUtils.waitCursor(MainView);
                DatabaseUtil.readDataByUsername(account.getUsername(), ref, new OnGetDataListener() {
                    @Override
                    public void dataRetrieved(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("activated").setValue(true, null);
                        MyToast.victoryMessage(account.getUsername() + " has been activated");
                        retrieveAccountsFromDatabase();
                        WindowUtils.defCursor(MainView);
                    }

                    @Override
                    public void dataExists(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            });

            card.add(approveBtn, labCnst);

        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = index;
        gbc.weighty = 1;
        gbc.weightx = 1;
        pnl_profilelist.add(Box.createRigidArea(new Dimension(0, 5)));
        pnl_profilelist.add(card, gbc);
        pnl_profilelist.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private JLabel createLabel(String label) {
        JLabel newLabel = new JLabel();
        newLabel.setText(label);
        newLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
        return newLabel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrp_profile = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        scrollpanel = new javax.swing.JScrollPane();
        pnl_profilelist = new javax.swing.JPanel();
        label_profiles = new javax.swing.JLabel();
        rbtn_approved = new javax.swing.JRadioButton();
        rbtn_unapproved = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 538));

        scrollpanel.setBackground(new java.awt.Color(245, 245, 245));
        scrollpanel.setBorder(null);

        pnl_profilelist.setBackground(new java.awt.Color(245, 245, 245));
        pnl_profilelist.setMaximumSize(new java.awt.Dimension(1056, 32767));
        pnl_profilelist.setLayout(new javax.swing.BoxLayout(pnl_profilelist, javax.swing.BoxLayout.Y_AXIS));
        scrollpanel.setViewportView(pnl_profilelist);

        label_profiles.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        label_profiles.setText("Profiles");

        btngrp_profile.add(rbtn_approved);
        rbtn_approved.setSelected(true);
        rbtn_approved.setText("Activated");
        rbtn_approved.setOpaque(false);
        rbtn_approved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_approvedActionPerformed(evt);
            }
        });

        btngrp_profile.add(rbtn_unapproved);
        rbtn_unapproved.setText("Unactivated");
        rbtn_unapproved.setOpaque(false);
        rbtn_unapproved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_unapprovedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpanel)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label_profiles))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(309, Short.MAX_VALUE)
                        .addComponent(rbtn_approved)
                        .addGap(18, 18, 18)
                        .addComponent(rbtn_unapproved)))
                .addContainerGap(314, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_profiles)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_approved)
                    .addComponent(rbtn_unapproved))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollpanel, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
        );

        scrollpanel.getVerticalScrollBar().setUnitIncrement(16);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rbtn_approvedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_approvedActionPerformed
        // TODO add your handling code here:
        if (rbtn_approved.isSelected()) {
            mode = approvedAccounts;
            populateIfExists();
        }
    }//GEN-LAST:event_rbtn_approvedActionPerformed

    private void rbtn_unapprovedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_unapprovedActionPerformed
        // TODO add your handling code here:
        if (rbtn_unapproved.isSelected()) {
            mode = unapprovedAccounts;
            populateIfExists();
        }

    }//GEN-LAST:event_rbtn_unapprovedActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btngrp_profile;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_profiles;
    private javax.swing.JPanel pnl_profilelist;
    private javax.swing.JRadioButton rbtn_approved;
    private javax.swing.JRadioButton rbtn_unapproved;
    private javax.swing.JScrollPane scrollpanel;
    // End of variables declaration//GEN-END:variables
}
