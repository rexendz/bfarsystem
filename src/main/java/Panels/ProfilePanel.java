/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Panels;

import DialogForms.OperatorRegistration;
import DialogForms.OperatorRegistration.OperatorRegistrationListener;
import Entities.DatabaseUtil;
import Entities.FishpondOperator;
import Entities.FishpondRecord;
import Entities.MyToast;
import Entities.OnGetDataListener;
import Entities.TimestampToDate;
import Entities.WindowUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author guess
 */
public class ProfilePanel extends javax.swing.JPanel implements OperatorRegistrationListener {

    /**
     * Creates new form ContentPanel
     */
    private Frame MainView;
    private FishpondOperator operator;
    private ArrayList<FishpondRecord> recordList;
    private ProfilePanelListener myListener;
    private Thread deleteThread;

    public ProfilePanel(Frame MainView, FishpondOperator operator) {
        initComponents();
        this.MainView = MainView;
        this.operator = operator;
        setupProfile(operator);
        populateTableComplaintRecords();
    }

    public void addListener(ProfilePanelListener myListener) {
        this.myListener = myListener;
    }

    @Override
    public void onRegistered() {
        myListener.onDeletedProfile();
    }

    public interface ProfilePanelListener {

        public void onCloseClicked();

        public void onDeletedProfile();
    }

    public void setupProfile(FishpondOperator operator) {
        text_flanum.setText(Long.toString(operator.getFla_number()));
        text_opname.setText(operator.getFullName());
        text_fpaddress.setText(operator.getAddress());
        text_operatorsim.setText(operator.getFishpond_size());
        text_issuancedate.setText(operator.getIssuance_date());
        text_expirationdate.setText(operator.getExpiration_date());
        text_operatorsim.setText(operator.getSim1());
        text_transmittersim.setText(operator.getSim2());
        text_comment.setText(operator.getComment());
        setSatusActive(operator.isIsActive());
    }

    private void setSatusActive(boolean isActive) {
        if (isActive) {
            text_status.setText("  ACTIVE  ");
            text_status.setBackground(Color.decode("#26de81"));
        } else {
            text_status.setText("  EXPIRED  ");
            text_status.setBackground(Color.decode("#fc5c65"));
        }
    }

    public void populateTableComplaintRecords() {
        recordList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("fishpond_record");
        Query query = ref.orderByChild("sim_number").equalTo(operator.getSim2());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot snap : ds.getChildren()) {
                    recordList.add(snap.getValue(FishpondRecord.class));
                }
                try {
                    SwingUtilities.invokeAndWait(() -> populate(recordList));
                } catch (InterruptedException | InvocationTargetException ex) {
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {

            }

        });
    }

    public void populate(ArrayList<FishpondRecord> fishpondRecords) {
        DefaultTableModel model = (DefaultTableModel) table_records.getModel();
        model.setRowCount(0);
        ArrayList<FishpondRecord> sortedRecords = fishpondRecords;
        Collections.sort(sortedRecords, (FishpondRecord o1, FishpondRecord o2) -> Long.compare(o2.getTimestamp(), o1.getTimestamp()));
        Object rowData[] = new Object[5];
        for (FishpondRecord fishpondRecord : sortedRecords) {
            rowData[0] = TimestampToDate.getDate(fishpondRecord.getTimestamp());
            rowData[1] = Float.toString(fishpondRecord.getPh_level());
            rowData[2] = Float.toString(fishpondRecord.getSalinity());
            rowData[3] = Float.toString(fishpondRecord.getTemperature());
            rowData[4] = Float.toString(fishpondRecord.getDo_level());
            model.addRow(rowData);
        }
//        WindowUtils.resizeColumnWidth(records_table);
//        records_table.repaint();
//        records_table.revalidate();
    }

    private void editProfile() {
        OperatorRegistration or = new OperatorRegistration(MainView, true);
        or.setEditMode(true);
        or.showProfile(this.operator);
        or.addListener(this);
        or.setVisible(true);
    }

    private void deleteProfile() {
        if (JOptionPane.showConfirmDialog(null, "Are you sure to delete this profile?") == 0) {
            WindowUtils.asyncWaitCursor(MainView);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("operator");
            DatabaseUtil.readDataByFLA(this.operator.getFla_number(), ref, new OnGetDataListener(){
                @Override
                public void dataRetrieved(DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().removeValue(null);
                    MyToast.victoryMessage("Operator Removed");
                    WindowUtils.defCursor(MainView);
                    myListener.onCloseClicked();
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
        text_flanum = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        text_fpaddress = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        text_issuancedate = new javax.swing.JLabel();
        text_operatorsim = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        text_expirationdate = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        text_opname = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        text_fpsize = new javax.swing.JLabel();
        text_transmittersim = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        text_comment = new javax.swing.JTextArea();
        btn_comment = new javax.swing.JButton();
        text_status = new javax.swing.JLabel();
        btn_edit = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btn_close = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_records = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        text_flanum.setFont(new java.awt.Font("Segoe UI Semibold", 1, 48)); // NOI18N
        text_flanum.setForeground(new java.awt.Color(102, 102, 102));
        text_flanum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_flanum.setText("12342");
        text_flanum.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FLA number");

        jPanel2.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("operator sim1 (main hub)");

        text_fpaddress.setEditable(false);
        text_fpaddress.setBackground(jPanel1.getBackground());
        text_fpaddress.setColumns(20);
        text_fpaddress.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        text_fpaddress.setForeground(new java.awt.Color(102, 102, 102));
        text_fpaddress.setLineWrap(true);
        text_fpaddress.setRows(2);
        text_fpaddress.setText("Omar Yusuf Demetillo Tagayan");
        text_fpaddress.setToolTipText("");
        text_fpaddress.setWrapStyleWord(true);
        text_fpaddress.setBorder(null);
        text_fpaddress.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("issuance date");

        text_issuancedate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        text_issuancedate.setForeground(new java.awt.Color(102, 102, 102));
        text_issuancedate.setText("32x32 in");

        text_operatorsim.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        text_operatorsim.setForeground(new java.awt.Color(102, 102, 102));
        text_operatorsim.setText("32x32 in");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("expiration date");

        text_expirationdate.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        text_expirationdate.setForeground(new java.awt.Color(102, 102, 102));
        text_expirationdate.setText("32x32 in");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("fishpond address");

        text_opname.setEditable(false);
        text_opname.setBackground(jPanel1.getBackground());
        text_opname.setColumns(20);
        text_opname.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        text_opname.setForeground(new java.awt.Color(102, 102, 102));
        text_opname.setLineWrap(true);
        text_opname.setRows(2);
        text_opname.setText("Omar Yusuf Demetillo Tagayan");
        text_opname.setToolTipText("");
        text_opname.setWrapStyleWord(true);
        text_opname.setBorder(null);
        text_opname.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("operator name");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("fishpond size");

        text_fpsize.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        text_fpsize.setForeground(new java.awt.Color(102, 102, 102));
        text_fpsize.setText("32x32 in");

        text_transmittersim.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        text_transmittersim.setForeground(new java.awt.Color(102, 102, 102));
        text_transmittersim.setText("32x32 in");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("operator sim2 (fishpond)");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("comments");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setToolTipText("");
        jScrollPane2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 11)); // NOI18N

        text_comment.setColumns(20);
        text_comment.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        text_comment.setForeground(new java.awt.Color(102, 102, 102));
        text_comment.setLineWrap(true);
        text_comment.setRows(5);
        text_comment.setWrapStyleWord(true);
        jScrollPane2.setViewportView(text_comment);

        btn_comment.setText("update comment");
        btn_comment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_commentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(text_operatorsim)
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                    .addComponent(jLabel5)
                                                    .addGap(144, 144, 144))
                                                .addComponent(text_opname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(text_fpaddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(text_expirationdate)
                                    .addComponent(jLabel9)
                                    .addComponent(text_issuancedate))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(text_fpsize)
                            .addComponent(text_transmittersim)
                            .addComponent(jLabel10)
                            .addComponent(jLabel14))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_comment, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_opname, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(text_fpaddress, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addGap(16, 16, 16)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_operatorsim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_transmittersim)
                .addGap(16, 16, 16)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_fpsize)
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_issuancedate)
                .addGap(16, 16, 16)
                .addComponent(jLabel12)
                .addGap(8, 8, 8)
                .addComponent(text_expirationdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_comment)
                .addContainerGap())
        );

        text_status.setBackground(new java.awt.Color(252, 92, 101));
        text_status.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        text_status.setForeground(new java.awt.Color(255, 255, 255));
        text_status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text_status.setText("  EXPIRED  ");
        text_status.setToolTipText("");
        text_status.setOpaque(true);

        btn_edit.setText("edit");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_delete.setText("delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(text_flanum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(text_status))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_edit, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(text_status)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(text_flanum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(btn_edit)
                .addGap(13, 13, 13)
                .addComponent(btn_delete)
                .addGap(45, 45, 45))
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Records");

        btn_close.setText("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        table_records.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        table_records.setForeground(new java.awt.Color(51, 51, 51));
        table_records.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date time", "PH Level", "Salinity", "Temperature", "DO Level"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_records.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        table_records.setRowHeight(20);
        table_records.setSelectionBackground(new java.awt.Color(102, 195, 255));
        jScrollPane1.setViewportView(table_records);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_close)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel13))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_close)))
                .addGap(59, 59, 59)
                .addComponent(jScrollPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        // TODO add your handling code here:
        myListener.onCloseClicked();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        deleteProfile();
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        // TODO add your handling code here:
        editProfile();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_commentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_commentActionPerformed
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("operator");
        DatabaseUtil.readDataByFLA(this.operator.getFla_number(), ref, new OnGetDataListener(){
            @Override
            public void dataRetrieved(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("comment").setValue(text_comment.getText(), null);
                MyToast.victoryMessage("Comment Updated");
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
    }//GEN-LAST:event_btn_commentActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_comment;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_records;
    private javax.swing.JTextArea text_comment;
    private javax.swing.JLabel text_expirationdate;
    private javax.swing.JLabel text_flanum;
    private javax.swing.JTextArea text_fpaddress;
    private javax.swing.JLabel text_fpsize;
    private javax.swing.JLabel text_issuancedate;
    private javax.swing.JLabel text_operatorsim;
    private javax.swing.JTextArea text_opname;
    private javax.swing.JLabel text_status;
    private javax.swing.JLabel text_transmittersim;
    // End of variables declaration//GEN-END:variables
}
