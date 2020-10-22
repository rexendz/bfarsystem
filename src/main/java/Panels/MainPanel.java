/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import DialogForms.AdminDialogForm;
import DialogForms.OperatorRegistration;
import DialogForms.OperatorRegistration.OperatorRegistrationListener;
import Entities.Account;
import Entities.DatabaseUtil;
import Entities.FishpondOperator;
import Entities.MyToast;
import Entities.WindowUtils;
import Panels.ProfilePanel.ProfilePanelListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author guess
 */
public class MainPanel extends javax.swing.JPanel implements OperatorRegistrationListener, ProfilePanelListener {

    /**
     * Creates new form MainPanel
     */
    private Frame MainView;
    private MainPageListener myListener;
    private Account account;
    private OperatorRegistration registrationWindow;
    private ArrayList<FishpondOperator> operatorList;
    private ProfilePanel profilePanel;
    private ValueEventListener operatorListener;
    private DatabaseReference operatorRef;
    private int i;

    public MainPanel(Frame MainView, Account account) {

        initComponents();
        this.MainView = MainView;
        this.account = account;

        ComponentOrientation co = pnl_west.getComponentOrientation().isLeftToRight()
                ? ComponentOrientation.RIGHT_TO_LEFT : ComponentOrientation.LEFT_TO_RIGHT;
        pnl_west.applyComponentOrientation(co);
        pnl_west.revalidate();
        pnl_west.repaint();

        operatorListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                MyToast.infoMessage("Operator List Updated");
                retrieveOperatorsThenPopulate();
            }

            @Override
            public void onCancelled(DatabaseError de) {

            }

        };

        operatorRef = FirebaseDatabase.getInstance().getReference().child("operator");
        operatorRef.addValueEventListener(operatorListener);
    }

    private void filterOperators() {
        Thread t = new Thread(() -> {
            do {
            } while (operatorList == null);
            String op_num = fliter_opnum.getText();
            String fla_num = filter_flanum.getText();

            String status = filter_status.getSelectedItem().toString();
            String barangay = filter_barangay.getText();

            ArrayList<FishpondOperator> filteredOperators = new ArrayList<>();
            operatorList.stream().filter(op -> ((op.isIsActive() && status.equals("Active")) || (!op.isIsActive() && status.equals("Expired")) || status.equals("All"))).filter(op -> (op.getSim1().contains(op_num) || op_num.isEmpty())).filter(op -> (String.valueOf(op.getFla_number()).contains(fla_num) || fla_num.isEmpty())).filter(op -> (op.getBarangay().toLowerCase().contains(barangay) || barangay.isEmpty())).forEachOrdered(op -> {
                filteredOperators.add(op);
            });
            System.out.println("SIZE OF FILTERED: " + filteredOperators.size());
            populateOperatorList(filteredOperators);
        });

        t.start();

    }

    private void retrieveOperatorsThenPopulate() {
        operatorList = new ArrayList<>();
        operatorList.clear();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("operator");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (operatorList.isEmpty()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        operatorList.add(snap.getValue(FishpondOperator.class));
                    }
                    filterOperators();
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {

            }
        });
    }

    private void populateOperatorList(ArrayList<FishpondOperator> operators) {
        pnl_list.removeAll();
        if (operators.size() > 0) {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    for (int i = 0; i < operators.size(); i++) {
                        createCard(i, operators.get(i));
                    }
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pnl_list.revalidate();
        pnl_list.repaint();

    }

    public void addPageListener(MainPageListener myListener) {
        this.myListener = myListener;
    }

    private void createCard(int index, FishpondOperator operator) {
        JPanel card = new JPanel();

        card.setBackground(new java.awt.Color(255, 255, 255));
        card.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        card.setPreferredSize(new java.awt.Dimension(310, 150));
        card.setMaximumSize(new Dimension(310, 150));

        card.setLayout(new java.awt.GridBagLayout());
        GridBagConstraints labCnst = new GridBagConstraints();
        labCnst.weightx = 1;
        labCnst.weighty = 0;
        labCnst.fill = GridBagConstraints.NONE;
        labCnst.insets = new Insets(3, 3, 3, 3);

        labCnst.gridx = 0;
        labCnst.gridy = 0;
        labCnst.anchor = GridBagConstraints.FIRST_LINE_START;
        JLabel opNumLabel = createLabel("Operator #: " + (operator.getSim1()));

        card.add(opNumLabel, labCnst);

        labCnst.gridx = 1;
        labCnst.gridy = 0;

        JLabel flaNumLabel = createLabel("FLA #: " + Long.toString(operator.getFla_number()));
        card.add(flaNumLabel, labCnst);

        labCnst.gridx = 2;
        labCnst.gridy = 0;
        labCnst.anchor = GridBagConstraints.FIRST_LINE_END;
        String status = "";
        Color color = null;
        if (operator.isIsActive()) {
            status = " ACTIVE  ";
            color = Color.decode("#26de81");
        } else {
            status = "  EXPIRED  ";
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
        labCnst.fill = GridBagConstraints.HORIZONTAL;
        labCnst.gridx = 0;
        labCnst.gridy = 1;
        labCnst.gridwidth = 3;
        labCnst.anchor = GridBagConstraints.FIRST_LINE_START;
        JLabel nameLabel = createLabel("Name: " + operator.getFullName());

        card.add(nameLabel, labCnst);

        labCnst.gridx = 0;
        labCnst.gridy = 2;

        javax.swing.JTextArea addresTextArea = new javax.swing.JTextArea();
        addresTextArea.setColumns(20);
        addresTextArea.setRows(2);
        addresTextArea.setBorder(null);
        addresTextArea.setLineWrap(true);
        addresTextArea.setWrapStyleWord(true);
        addresTextArea.setText(operator.getAddress());
        addresTextArea.setEditable(false);

        card.add(addresTextArea, labCnst);

        labCnst.gridx = 0;
        labCnst.gridy = 3;
        JLabel commentLabel = createLabel("Comments: " + operator.getComment());
        card.add(commentLabel, labCnst);
        labCnst.gridx = 0;
        labCnst.gridy = 4;
        labCnst.fill = GridBagConstraints.NONE;
        labCnst.anchor = GridBagConstraints.CENTER;

        JButton viewProfileBtn = new JButton();
        viewProfileBtn.setBackground(Color.decode("#1B9CFC"));
        viewProfileBtn.setForeground(new java.awt.Color(255, 255, 255));
        viewProfileBtn.setText("View full profile");
        viewProfileBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        viewProfileBtn.addActionListener((ActionEvent e) -> {
            try {
                WindowUtils.asyncWaitCursor(MainView);
                profilePanel = new ProfilePanel(MainView, operator);
                profilePanel.addListener(this);
                panel_content.setViewportView(profilePanel);
            } finally {
                WindowUtils.asyncDefCursor(MainView);
            }

        });

        card.add(viewProfileBtn, labCnst);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 0;
        gbc.gridy = index;
        gbc.weighty = 1;
        gbc.weightx = 1;
        pnl_list.add(Box.createRigidArea(new Dimension(0, 5)));
        pnl_list.add(card, gbc);
        pnl_list.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private JPanel createRowContainer() {
        JPanel newContainer = new JPanel();
        newContainer.setOpaque(false);
        newContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        return newContainer;
    }

    private JLabel createLabel(String label) {
        JLabel newLabel = new JLabel();
        newLabel.setText(label);
        newLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
        return newLabel;
    }

    private void displayRegistrationDialog() {
        registrationWindow = new OperatorRegistration(MainView, true);
        registrationWindow.addListener(this);
        registrationWindow.setVisible(true);
    }

    private void manageAccounts() {
        if (account.isAdmin()) {
            AdminDialogForm adminWindow = new AdminDialogForm(MainView, true, account);
            adminWindow.setVisible(true);
        } else {
            MyToast.errorMessage("Accounts with Admin privilages only");
        }
    }

    @Override
    public void onRegistered() {
        panel_content.setViewportView(null);
    }

    @Override
    public void onCloseClicked() {
        panel_content.setViewportView(null);
    }

    @Override
    public void onDeletedProfile() {
        panel_content.setViewportView(null);
        retrieveOperatorsThenPopulate();
    }

    public interface MainPageListener {

        public void onLogoutButtonClicked();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btn_logout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jToggleButton2 = new javax.swing.JToggleButton();
        btn_manageAccounts = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fliter_opnum = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        filter_status = new javax.swing.JComboBox<>();
        filter_flanum = new javax.swing.JTextField();
        filter_barangay = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        pnl_west = new javax.swing.JScrollPane();
        pnl_list = new javax.swing.JPanel();
        pnl_center = new javax.swing.JPanel();
        panel_content = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1149, 75));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(27, 156, 252));

        btn_logout.setBackground(new java.awt.Color(255, 255, 255));
        btn_logout.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setText("logout");
        btn_logout.setContentAreaFilled(false);
        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Operators");

        jToggleButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton2.setText("Add Operator");
        jToggleButton2.setContentAreaFilled(false);
        jToggleButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        btn_manageAccounts.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btn_manageAccounts.setForeground(new java.awt.Color(255, 255, 255));
        btn_manageAccounts.setText("Manage Accounts");
        btn_manageAccounts.setContentAreaFilled(false);
        btn_manageAccounts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_manageAccounts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_manageAccountsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 891, Short.MAX_VALUE)
                .addComponent(btn_manageAccounts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton2)
                .addGap(3, 3, 3)
                .addComponent(btn_logout)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_logout)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton2)
                    .addComponent(btn_manageAccounts))
                .addGap(0, 0, 0))
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel5.setBackground(new java.awt.Color(230, 230, 230));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Filter");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Operator #: ");

        fliter_opnum.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        fliter_opnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fliter_opnumActionPerformed(evt);
            }
        });
        fliter_opnum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fliter_opnumKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("FLA #: ");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Barangay: ");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Status: ");

        filter_status.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        filter_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Active", "Expired" }));
        filter_status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filter_statusActionPerformed(evt);
            }
        });

        filter_flanum.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        filter_flanum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filter_flanumActionPerformed(evt);
            }
        });
        filter_flanum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                filter_flanumKeyTyped(evt);
            }
        });

        filter_barangay.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        filter_barangay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filter_barangayActionPerformed(evt);
            }
        });
        filter_barangay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                filter_barangayKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fliter_opnum, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filter_flanum, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filter_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filter_barangay, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(635, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(filter_barangay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(filter_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fliter_opnum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(filter_flanum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(330, 180));

        pnl_west.setBackground(new java.awt.Color(240, 243, 255));
        pnl_west.setBorder(null);
        pnl_west.setPreferredSize(new java.awt.Dimension(300, 150));

        pnl_list.setBackground(new java.awt.Color(255, 255, 255));
        pnl_list.setMaximumSize(new java.awt.Dimension(300, 32767));
        pnl_list.setLayout(new javax.swing.BoxLayout(pnl_list, javax.swing.BoxLayout.Y_AXIS));
        pnl_west.setViewportView(pnl_list);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_west, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_west, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pnl_west.getVerticalScrollBar().setUnitIncrement(16);

        add(jPanel3, java.awt.BorderLayout.WEST);

        pnl_center.setBackground(new java.awt.Color(37, 204, 247));
        pnl_center.setLayout(new java.awt.BorderLayout());

        panel_content.setBackground(new java.awt.Color(51, 51, 51));
        panel_content.setBorder(null);
        panel_content.setPreferredSize(new java.awt.Dimension(0, 500));
        pnl_center.add(panel_content, java.awt.BorderLayout.CENTER);

        add(pnl_center, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        operatorRef.removeEventListener(operatorListener);
        myListener.onLogoutButtonClicked();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void fliter_opnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fliter_opnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fliter_opnumActionPerformed

    private void filter_flanumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filter_flanumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filter_flanumActionPerformed

    private void filter_barangayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filter_barangayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filter_barangayActionPerformed

    private void filter_statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filter_statusActionPerformed
        // TODO add your handling code here:
        filterOperators();
    }//GEN-LAST:event_filter_statusActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        displayRegistrationDialog();
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void btn_manageAccountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_manageAccountsActionPerformed
        // TODO add your handling code here:
        manageAccounts();
    }//GEN-LAST:event_btn_manageAccountsActionPerformed

    private void fliter_opnumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fliter_opnumKeyTyped
        filterOperators();
    }//GEN-LAST:event_fliter_opnumKeyTyped

    private void filter_flanumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filter_flanumKeyTyped
        filterOperators();
    }//GEN-LAST:event_filter_flanumKeyTyped

    private void filter_barangayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filter_barangayKeyTyped
        filterOperators();
    }//GEN-LAST:event_filter_barangayKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_manageAccounts;
    private javax.swing.JTextField filter_barangay;
    private javax.swing.JTextField filter_flanum;
    private javax.swing.JComboBox<String> filter_status;
    private javax.swing.JTextField fliter_opnum;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JScrollPane panel_content;
    private javax.swing.JPanel pnl_center;
    private javax.swing.JPanel pnl_list;
    private javax.swing.JScrollPane pnl_west;
    // End of variables declaration//GEN-END:variables
}
