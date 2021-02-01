/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DialogForms;

import DialogForms.FishpondBoxSim.SimWindowListener;
import Entities.Account;
import Entities.DatabaseUtil;
import Entities.DatePicker;
import Entities.FishpondBoxes;
import Entities.FishpondOperator;
import Entities.MyKeyListener;
import Entities.MyToast;
import Entities.OnGetDataListener;
import Entities.RequiredFields;
import Entities.WindowUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;

/**
 *
 * @author guess
 */
public class OperatorRegistration extends javax.swing.JDialog implements SimWindowListener {

    /**
     * Creates new form OperatorRegistration
     */
    private long boxCount;
    private FishpondBoxes fishpondBoxes;
    private FishpondBoxSim simWindow;
    private OperatorRegistration myPanel = this;
    private Frame MainView;
    private RequiredFields fields;
    private OperatorRegistrationListener myListener;
    private MyKeyListener digitsOnly = new MyKeyListener(MyKeyListener.NumberOnly);
    private FishpondOperator originalProfile = null;
    private boolean editMode;

    public OperatorRegistration(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.MainView = parent;
        fields = new RequiredFields();
        fields.add(field_fname);
        fields.add(field_mname);
        fields.add(field_lname);
        fields.add(field_fsize);
        fields.add(field_issuancedate);
        fields.add(field_expirydate);
        fields.add(field_barangay);
        fields.add(field_operatorsim);
        field_flanumber.addKeyListener(digitsOnly);
    }

    public void showProfile(FishpondOperator operator) {
        this.originalProfile = operator;
        field_flanumber.setText(Long.toString(operator.getFla_number()));
        field_operatorsim.setText(operator.getSim1().substring(3));
        field_fname.setText(operator.getFirstname());
        field_mname.setText(operator.getMiddlename());
        field_lname.setText(operator.getLastname());
        field_fsize.setText(operator.getFishpond_size());
        field_issuancedate.setText(operator.getIssuance_date());
        field_expirydate.setText(operator.getExpiration_date());
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void addListener(OperatorRegistrationListener myListener) {
        this.myListener = myListener;
    }

    public void registerOperator() {

        if (JOptionPane.showConfirmDialog(null, "Are you sure to save this profile?") != 0) {
            return;
        }
        if(fishpondBoxes == null) {
            MyToast.errorMessage("Please fill up boxes sim fields");
            return;
        }

        if (!fields.isAllFilled()) {
            MyToast.errorMessage("Please fill up all fields");
            return;
        } else {
            FishpondOperator operator = new FishpondOperator();
            operator.setBoxes(boxCount);
            operator.setFirstname(field_fname.getText());
            operator.setMiddlename(field_mname.getText());
            operator.setLastname(field_lname.getText());
            operator.setFla_number(Long.parseLong(field_flanumber.getText()));
            operator.setFishpond_size(field_fsize.getText());
            operator.setBarangay(field_barangay.getText());
            try {
                operator.setMunicipality(cbox_municipality.getSelectedItem().toString());
            } catch (Exception e) {
                operator.setMunicipality("No Municipality");
            }
            operator.setIssuance_date(field_issuancedate.getText());
            operator.setExpiration_date(field_expirydate.getText());
            operator.setSim1("+63" + field_operatorsim.getText());
            operator.setCityProvince(cbox_cityprovince.getSelectedItem().toString());

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("operator");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    boolean flaTaken = false;
                    boolean sim1Taken = false;
                    String key = null;
                    for (DataSnapshot snap : ds.getChildren()) {
                        if (operator.getFla_number() == snap.getValue(FishpondOperator.class).getFla_number()) {
                            flaTaken = true;
                            key = snap.getKey();
                        }
                        if (operator.getSim1().equals(snap.getValue(FishpondOperator.class).getSim1())) {
                            sim1Taken = true;
                            key = snap.getKey();
                        }
                    }
                    if (!editMode) {
                        if (flaTaken) {
                            MyToast.errorMessage("FLA Number Already Taken!");
                        } else if (sim1Taken) {
                            MyToast.errorMessage("Sim 1 Number Already Taken!");
                        } else {
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("fishpond_box");
                            String fla_id = Long.toString(operator.getFla_number());
                            ref2.child(fla_id).setValue(fishpondBoxes, null);
                            String id = ref.push().getKey();
                            ref.child(id).setValue(operator, null);
                            MyToast.victoryMessage("Profile Successfully Saved");
                            myListener.onRegistered();
                            myPanel.dispose();
                        }
                    } else {
                        if (key != null) {
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("fishpond_box");
                            String fla_id = Long.toString(operator.getFla_number());
                            ref2.child(fla_id).removeValue(null);
                            ref2.child(fla_id).setValue(fishpondBoxes, null);
                            ds.child(key).getRef().removeValue(null);
                            String id = ref.push().getKey();
                            ref.child(id).setValue(operator, null);
                            MyToast.victoryMessage("Profile Successfully Saved");
                            myListener.onRegistered();
                            myPanel.dispose();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {

                }

            });
        }
    }
    

    @Override
    public void onSubmit(FishpondBoxes a, long boxCount) {
        this.fishpondBoxes = a;
        this.fishpondBoxes = simWindow.getBoxes();
        this.boxCount = boxCount;
        System.out.println(this.fishpondBoxes.getBox1_sim());
    }

    public interface OperatorRegistrationListener {

        public void onRegistered();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        field_flanumber = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        field_fname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        field_mname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        field_lname = new javax.swing.JTextField();
        field_fsize = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        field_issuancedate = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btn_register = new javax.swing.JButton();
        btn_setissuancedate = new javax.swing.JButton();
        field_expirydate = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btn_setissuancedate1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        field_operatorsim = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbox_cityprovince = new javax.swing.JComboBox<>();
        cbox_municipality = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        field_barangay = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(230, 230, 230));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Operator Registration");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("FLA #: ");

        field_flanumber.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_flanumber.setForeground(new java.awt.Color(51, 51, 51));
        field_flanumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                field_flanumberFocusLost(evt);
            }
        });
        field_flanumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_flanumberActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Firstname:");

        field_fname.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_fname.setForeground(new java.awt.Color(51, 51, 51));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Middlename");

        field_mname.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_mname.setForeground(new java.awt.Color(51, 51, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Lastname:");

        field_lname.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_lname.setForeground(new java.awt.Color(51, 51, 51));

        field_fsize.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_fsize.setForeground(new java.awt.Color(51, 51, 51));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Fishpond Size: ");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Issuance Date:");

        field_issuancedate.setEditable(false);
        field_issuancedate.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_issuancedate.setForeground(new java.awt.Color(51, 51, 51));

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Barangay: ");

        btn_register.setBackground(new java.awt.Color(255, 255, 255));
        btn_register.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btn_register.setForeground(new java.awt.Color(27, 156, 252));
        btn_register.setText("Register");
        btn_register.setBorderPainted(false);
        btn_register.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registerActionPerformed(evt);
            }
        });

        btn_setissuancedate.setText("...");
        btn_setissuancedate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_setissuancedateActionPerformed(evt);
            }
        });

        field_expirydate.setEditable(false);
        field_expirydate.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_expirydate.setForeground(new java.awt.Color(51, 51, 51));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Expiration Date; ");

        btn_setissuancedate1.setText("...");
        btn_setissuancedate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_setissuancedate1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Operator SIM1 (Main Hub)");

        field_operatorsim.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsimActionPerformed(evt);
            }
        });
        field_operatorsim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsimKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("+63");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Fishpond Boxes SIMs");

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("City/Province: ");

        cbox_cityprovince.setFont(new java.awt.Font("Source Code Pro Semibold", 0, 14)); // NOI18N
        cbox_cityprovince.setForeground(new java.awt.Color(255, 255, 255));
        cbox_cityprovince.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Zamboanga City ", "Zamboanga Del Norte", "Zamboang Del Sur", "Zamboanga Sibugay" }));
        cbox_cityprovince.setSelectedIndex(-1);
        cbox_cityprovince.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbox_cityprovinceActionPerformed(evt);
            }
        });

        cbox_municipality.setFont(new java.awt.Font("Source Code Pro Semibold", 0, 14)); // NOI18N
        cbox_municipality.setForeground(new java.awt.Color(255, 255, 255));
        cbox_municipality.setEnabled(false);
        cbox_municipality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbox_municipalityActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Municipality:");

        field_barangay.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_barangay.setForeground(new java.awt.Color(51, 51, 51));
        field_barangay.setEnabled(false);
        field_barangay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_barangayActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(27, 156, 252));
        jButton1.setText("Enter Numbers");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(field_fname, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(field_flanumber, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(94, 94, 94)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(field_expirydate, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_setissuancedate1))
                                    .addComponent(jLabel14)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(field_operatorsim, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(25, 25, 25)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(40, 40, 40))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(field_mname, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(field_lname, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(field_fsize, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(field_issuancedate, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_setissuancedate)))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbox_cityprovince, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(cbox_municipality, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_register, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(field_barangay))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(8, 8, 8)
                        .addComponent(field_flanumber, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(field_operatorsim, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15)))
                        .addGap(7, 7, 7)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_fname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(field_mname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(field_lname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_fsize, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(field_issuancedate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_setissuancedate)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(field_expirydate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_setissuancedate1))))
                .addGap(27, 27, 27)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbox_cityprovince, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbox_municipality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(field_barangay, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_register, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_setissuancedateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_setissuancedateActionPerformed
        // TODO add your handling code here:
        field_issuancedate.setText(new DatePicker(MainView).setPickedDate());
    }//GEN-LAST:event_btn_setissuancedateActionPerformed

    private void btn_setissuancedate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_setissuancedate1ActionPerformed
        // TODO add your handling code here:
        field_expirydate.setText(new DatePicker(MainView).setPickedDate());
    }//GEN-LAST:event_btn_setissuancedate1ActionPerformed

    private void btn_registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registerActionPerformed
        // TODO add your handling code here:
        registerOperator();
    }//GEN-LAST:event_btn_registerActionPerformed

    private void cbox_cityprovinceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbox_cityprovinceActionPerformed
        if (cbox_cityprovince.getSelectedIndex() == 0) { // Zamboanga City
            cbox_municipality.setEnabled(false);
            cbox_municipality.setSelectedIndex(-1);
            field_barangay.setEnabled(true);
        }
        if (cbox_cityprovince.getSelectedIndex() == 1) { // Zamboanga Del Norte
            cbox_municipality.setEnabled(true);
            cbox_municipality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Baliguian", "Godod", "Gutalac", "Jose Dalman", "Kalawit", "Katipunan", "La Libertad", "Labason", "Leon B. Postigo", "Liloy", "Manukan", "Mutia", "Piñan", "Polanco", "President Manuel A. Roxas", "Rizal", "Salug", "Sergio Osmeña Sr.", "Siayan", "Sibuco", "Sibutad", "Sindangan", "Siocon", "Sirawai", "Tampilisan"}));
            field_barangay.setEnabled(true);
        }
        if (cbox_cityprovince.getSelectedIndex() == 2) { // Zamboanga Del Sur
            cbox_municipality.setEnabled(true);
            cbox_municipality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Aurora", "Bayog", "Dimataling", "Dinas", "Dumalinao", "Dumingag", "Guipos", "Josefina", "Kumalarang", "Labangan", "Lakewood", "Lapuyan", "Mahayag", "Margosatubig", "Midsalip", "Molave", "Pagadian", "Pitogo", "Ramon Magsaysay", "San Miguel", "San Pablo", "Sominot", "Tabina", "Tambulig", "Tigbao", "Tukuran", "Vincenzo A. Sagun"}));
            field_barangay.setEnabled(true);
        }
        if (cbox_cityprovince.getSelectedIndex() == 3) { // Zamboanga Sibugay
            cbox_municipality.setEnabled(true);
            cbox_municipality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Alicia", "Buug", "Diplahan", "Imelda", "Ipil", "Kabasalan", "Mabuhay", "Malangas", "Naga", "Olutanga", "Payao", "Roseller Lim", "Siay", "Talusan", "Titay", "Tungawan"}));
            field_barangay.setEnabled(true);
        }
    }//GEN-LAST:event_cbox_cityprovinceActionPerformed

    private void cbox_municipalityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbox_municipalityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbox_municipalityActionPerformed

    private void field_barangayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_barangayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_barangayActionPerformed

    private void field_operatorsimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsimActionPerformed

    private void field_operatorsimKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsimKeyTyped

        char enter = evt.getKeyChar();
        if (!Character.isDigit(enter) || field_operatorsim.getText().length() >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_field_operatorsimKeyTyped

    private void field_flanumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_flanumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_flanumberActionPerformed

    private void field_flanumberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_field_flanumberFocusLost
        long flanum = -25565;
        try {
            flanum = Long.parseLong(field_flanumber.getText());
        } catch (NumberFormatException e) {
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("account");
        Query query = ref.orderByChild("fla_number").equalTo(flanum);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                Account existingAccount = new Account((Map<String, Object>) ds.getValue());
                field_operatorsim.setText(existingAccount.getSim1().substring(3));
                field_fname.setText(existingAccount.getFirstname());
                field_mname.setText(existingAccount.getMiddlename());
                field_lname.setText(existingAccount.getLastname());
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {

            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {

            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {

            }

            @Override
            public void onCancelled(DatabaseError de) {

            }

        });
    }//GEN-LAST:event_field_flanumberFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (simWindow == null) {
            simWindow = new FishpondBoxSim(MainView, true);
            simWindow.addListener(this);
            simWindow.setVisible(true);
        } else {
            simWindow.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_register;
    private javax.swing.JButton btn_setissuancedate;
    private javax.swing.JButton btn_setissuancedate1;
    private javax.swing.JComboBox<String> cbox_cityprovince;
    private javax.swing.JComboBox<String> cbox_municipality;
    private javax.swing.JTextField field_barangay;
    private javax.swing.JTextField field_expirydate;
    private javax.swing.JTextField field_flanumber;
    private javax.swing.JTextField field_fname;
    private javax.swing.JTextField field_fsize;
    private javax.swing.JTextField field_issuancedate;
    private javax.swing.JTextField field_lname;
    private javax.swing.JTextField field_mname;
    private javax.swing.JTextField field_operatorsim;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
