/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DialogForms;

import Entities.FishpondBoxes;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author rexen
 */
public class FishpondBoxSim extends javax.swing.JDialog {
    private FishpondBoxes fishpondBoxes;
    private long boxCount;
    private SimWindowListener simWindowListener;

    /**
     * Creates new form FishpondBoxSim
     */
    public FishpondBoxSim(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ((DefaultEditor) jSpinner1.getEditor()).getTextField().setEditable(false);
        jSpinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boxCount = (long) jSpinner1.getValue();
                filterBoxSims();
            }
        });
    }
    
    public void addListener(SimWindowListener simWindowListener) {
        this.simWindowListener = simWindowListener;
    }
    

    public interface SimWindowListener {

        public void onSubmit(FishpondBoxes fishpondBoxes, long boxCount);
    }
    
    public FishpondBoxes getBoxes() {
        return this.fishpondBoxes;
    }
    
    public void filterBoxSims() {
        switch((int) boxCount) {
            case 1:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(false);
                field_operatorsim2.setEnabled(false);
                field_operatorsim3.setEnabled(false);
                field_operatorsim4.setEnabled(false);
                field_operatorsim5.setEnabled(false);
                field_operatorsim6.setEnabled(false);
                field_operatorsim7.setEnabled(false);
                field_operatorsim8.setEnabled(false);
                field_operatorsim9.setEnabled(false);
                field_operatorsim1.setText("");
                field_operatorsim2.setText("");
                field_operatorsim3.setText("");
                field_operatorsim4.setText("");
                field_operatorsim5.setText("");
                field_operatorsim6.setText("");
                field_operatorsim7.setText("");
                field_operatorsim8.setText("");
                field_operatorsim9.setText("");
                break;
            case 2:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(false);
                field_operatorsim3.setEnabled(false);
                field_operatorsim4.setEnabled(false);
                field_operatorsim5.setEnabled(false);
                field_operatorsim6.setEnabled(false);
                field_operatorsim7.setEnabled(false);
                field_operatorsim8.setEnabled(false);
                field_operatorsim9.setEnabled(false);
                field_operatorsim2.setText("");
                field_operatorsim3.setText("");
                field_operatorsim4.setText("");
                field_operatorsim5.setText("");
                field_operatorsim6.setText("");
                field_operatorsim7.setText("");
                field_operatorsim8.setText("");
                field_operatorsim9.setText("");
                break;
            case 3:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(true);
                field_operatorsim3.setEnabled(false);
                field_operatorsim4.setEnabled(false);
                field_operatorsim5.setEnabled(false);
                field_operatorsim6.setEnabled(false);
                field_operatorsim7.setEnabled(false);
                field_operatorsim8.setEnabled(false);
                field_operatorsim9.setEnabled(false);
                field_operatorsim3.setText("");
                field_operatorsim4.setText("");
                field_operatorsim5.setText("");
                field_operatorsim6.setText("");
                field_operatorsim7.setText("");
                field_operatorsim8.setText("");
                field_operatorsim9.setText("");
                break;
            case 4:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(true);
                field_operatorsim3.setEnabled(true);
                field_operatorsim4.setEnabled(false);
                field_operatorsim5.setEnabled(false);
                field_operatorsim6.setEnabled(false);
                field_operatorsim7.setEnabled(false);
                field_operatorsim8.setEnabled(false);
                field_operatorsim9.setEnabled(false);
                field_operatorsim4.setText("");
                field_operatorsim5.setText("");
                field_operatorsim6.setText("");
                field_operatorsim7.setText("");
                field_operatorsim8.setText("");
                field_operatorsim9.setText("");
                break;
            case 5:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(true);
                field_operatorsim3.setEnabled(true);
                field_operatorsim4.setEnabled(true);
                field_operatorsim5.setEnabled(false);
                field_operatorsim6.setEnabled(false);
                field_operatorsim7.setEnabled(false);
                field_operatorsim8.setEnabled(false);
                field_operatorsim9.setEnabled(false);
                field_operatorsim5.setText("");
                field_operatorsim6.setText("");
                field_operatorsim7.setText("");
                field_operatorsim8.setText("");
                field_operatorsim9.setText("");
                break;
            case 6:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(true);
                field_operatorsim3.setEnabled(true);
                field_operatorsim4.setEnabled(true);
                field_operatorsim5.setEnabled(true);
                field_operatorsim6.setEnabled(false);
                field_operatorsim7.setEnabled(false);
                field_operatorsim8.setEnabled(false);
                field_operatorsim9.setEnabled(false);
                field_operatorsim6.setText("");
                field_operatorsim7.setText("");
                field_operatorsim8.setText("");
                field_operatorsim9.setText("");
                break;
            case 7:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(true);
                field_operatorsim3.setEnabled(true);
                field_operatorsim4.setEnabled(true);
                field_operatorsim5.setEnabled(true);
                field_operatorsim6.setEnabled(true);
                field_operatorsim7.setEnabled(false);
                field_operatorsim8.setEnabled(false);
                field_operatorsim9.setEnabled(false);
                field_operatorsim7.setText("");
                field_operatorsim8.setText("");
                field_operatorsim9.setText("");
                break;
            case 8:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(true);
                field_operatorsim3.setEnabled(true);
                field_operatorsim4.setEnabled(true);
                field_operatorsim5.setEnabled(true);
                field_operatorsim6.setEnabled(true);
                field_operatorsim7.setEnabled(true);
                field_operatorsim8.setEnabled(false);
                field_operatorsim9.setEnabled(false);
                field_operatorsim8.setText("");
                field_operatorsim9.setText("");
                break;
            case 9:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(true);
                field_operatorsim3.setEnabled(true);
                field_operatorsim4.setEnabled(true);
                field_operatorsim5.setEnabled(true);
                field_operatorsim6.setEnabled(true);
                field_operatorsim7.setEnabled(true);
                field_operatorsim8.setEnabled(true);
                field_operatorsim9.setEnabled(false);
                field_operatorsim9.setText("");
                break;
            case 10:
                field_operatorsim.setEnabled(true);
                field_operatorsim1.setEnabled(true);
                field_operatorsim2.setEnabled(true);
                field_operatorsim3.setEnabled(true);
                field_operatorsim4.setEnabled(true);
                field_operatorsim5.setEnabled(true);
                field_operatorsim6.setEnabled(true);
                field_operatorsim7.setEnabled(true);
                field_operatorsim8.setEnabled(true);
                field_operatorsim9.setEnabled(true);
                break;
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

        field_operatorsim = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        field_operatorsim1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        field_operatorsim2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        field_operatorsim3 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        field_operatorsim4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        field_operatorsim5 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        field_operatorsim6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        field_operatorsim7 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        field_operatorsim8 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        field_operatorsim9 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(200, 200, 200));
        setType(java.awt.Window.Type.POPUP);

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

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Box 1 SIM: ");

        field_operatorsim1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim1.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim1.setEnabled(false);
        field_operatorsim1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim1ActionPerformed(evt);
            }
        });
        field_operatorsim1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim1KeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("+63");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Box 2 SIM: ");

        jSpinner1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Long.valueOf(1L), Long.valueOf(1L), Long.valueOf(10L), Long.valueOf(1L)));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Number of Fishpond Boxes: ");

        field_operatorsim2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim2.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim2.setEnabled(false);
        field_operatorsim2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim2ActionPerformed(evt);
            }
        });
        field_operatorsim2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim2KeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Box 3 SIM: ");

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("+63");

        field_operatorsim3.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim3.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim3.setEnabled(false);
        field_operatorsim3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim3ActionPerformed(evt);
            }
        });
        field_operatorsim3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim3KeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("+63");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Box 4 SIM: ");

        field_operatorsim4.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim4.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim4.setEnabled(false);
        field_operatorsim4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim4ActionPerformed(evt);
            }
        });
        field_operatorsim4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim4KeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Box 5 SIM: ");

        jLabel19.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("+63");

        field_operatorsim5.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim5.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim5.setEnabled(false);
        field_operatorsim5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim5ActionPerformed(evt);
            }
        });
        field_operatorsim5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim5KeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("+63");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Box 6 SIM: ");

        field_operatorsim6.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim6.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim6.setEnabled(false);
        field_operatorsim6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim6ActionPerformed(evt);
            }
        });
        field_operatorsim6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim6KeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Box 7 SIM: ");

        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("+63");

        field_operatorsim7.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim7.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim7.setEnabled(false);
        field_operatorsim7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim7ActionPerformed(evt);
            }
        });
        field_operatorsim7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim7KeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("+63");

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Box 8 SIM: ");

        field_operatorsim8.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim8.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim8.setEnabled(false);
        field_operatorsim8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim8ActionPerformed(evt);
            }
        });
        field_operatorsim8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim8KeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Box 9 SIM: ");

        jLabel23.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("+63");

        field_operatorsim9.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        field_operatorsim9.setForeground(new java.awt.Color(51, 51, 51));
        field_operatorsim9.setEnabled(false);
        field_operatorsim9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                field_operatorsim9ActionPerformed(evt);
            }
        });
        field_operatorsim9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                field_operatorsim9KeyTyped(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("+63");

        jLabel13.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Box 10 SIM: ");

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButton2.setText("Reset");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_operatorsim, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_operatorsim3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_operatorsim7, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_operatorsim4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_operatorsim5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_operatorsim8, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_operatorsim1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_operatorsim2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(field_operatorsim6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(field_operatorsim9, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(field_operatorsim9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void field_operatorsimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsimActionPerformed

    private void field_operatorsimKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsimKeyTyped

        char enter = evt.getKeyChar();
        if (!Character.isDigit(enter) || field_operatorsim.getText().length() >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_field_operatorsimKeyTyped

    private void field_operatorsim1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim1ActionPerformed

    private void field_operatorsim1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim1KeyTyped

    private void field_operatorsim2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim2ActionPerformed

    private void field_operatorsim2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim2KeyTyped

    private void field_operatorsim3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim3ActionPerformed

    private void field_operatorsim3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim3KeyTyped

    private void field_operatorsim4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim4ActionPerformed

    private void field_operatorsim4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim4KeyTyped

    private void field_operatorsim5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim5ActionPerformed

    private void field_operatorsim5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim5KeyTyped

    private void field_operatorsim6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim6ActionPerformed

    private void field_operatorsim6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim6KeyTyped

    private void field_operatorsim7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim7ActionPerformed

    private void field_operatorsim7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim7KeyTyped

    private void field_operatorsim8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim8ActionPerformed

    private void field_operatorsim8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim8KeyTyped

    private void field_operatorsim9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_field_operatorsim9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim9ActionPerformed

    private void field_operatorsim9KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_field_operatorsim9KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_field_operatorsim9KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        fishpondBoxes = new FishpondBoxes();
        filterBoxSims();
        switch((int) boxCount) {
            case 1:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                break;
            case 2:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                break;
            case 3:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                fishpondBoxes.setBox3_sim("+63" + field_operatorsim2.getText());
                break;
            case 4:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                fishpondBoxes.setBox3_sim("+63" + field_operatorsim2.getText());
                fishpondBoxes.setBox4_sim("+63" + field_operatorsim3.getText());
                break;
            case 5:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                fishpondBoxes.setBox3_sim("+63" + field_operatorsim2.getText());
                fishpondBoxes.setBox4_sim("+63" + field_operatorsim3.getText());
                fishpondBoxes.setBox5_sim("+63" + field_operatorsim4.getText());
                break;
            case 6:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                fishpondBoxes.setBox3_sim("+63" + field_operatorsim2.getText());
                fishpondBoxes.setBox4_sim("+63" + field_operatorsim3.getText());
                fishpondBoxes.setBox5_sim("+63" + field_operatorsim4.getText());
                fishpondBoxes.setBox6_sim("+63" + field_operatorsim5.getText());
                break;
            case 7:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                fishpondBoxes.setBox3_sim("+63" + field_operatorsim2.getText());
                fishpondBoxes.setBox4_sim("+63" + field_operatorsim3.getText());
                fishpondBoxes.setBox5_sim("+63" + field_operatorsim4.getText());
                fishpondBoxes.setBox6_sim("+63" + field_operatorsim5.getText());
                fishpondBoxes.setBox7_sim("+63" + field_operatorsim6.getText());
                break;
            case 8:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                fishpondBoxes.setBox3_sim("+63" + field_operatorsim2.getText());
                fishpondBoxes.setBox4_sim("+63" + field_operatorsim3.getText());
                fishpondBoxes.setBox5_sim("+63" + field_operatorsim4.getText());
                fishpondBoxes.setBox6_sim("+63" + field_operatorsim5.getText());
                fishpondBoxes.setBox7_sim("+63" + field_operatorsim6.getText());
                fishpondBoxes.setBox8_sim("+63" + field_operatorsim7.getText());
                break;
            case 9:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                fishpondBoxes.setBox3_sim("+63" + field_operatorsim2.getText());
                fishpondBoxes.setBox4_sim("+63" + field_operatorsim3.getText());
                fishpondBoxes.setBox5_sim("+63" + field_operatorsim4.getText());
                fishpondBoxes.setBox6_sim("+63" + field_operatorsim5.getText());
                fishpondBoxes.setBox7_sim("+63" + field_operatorsim6.getText());
                fishpondBoxes.setBox8_sim("+63" + field_operatorsim7.getText());
                fishpondBoxes.setBox9_sim("+63" + field_operatorsim8.getText());
                break;
            case 10:
                fishpondBoxes.setBox1_sim("+63" + field_operatorsim.getText());
                fishpondBoxes.setBox2_sim("+63" + field_operatorsim1.getText());
                fishpondBoxes.setBox3_sim("+63" + field_operatorsim2.getText());
                fishpondBoxes.setBox4_sim("+63" + field_operatorsim3.getText());
                fishpondBoxes.setBox5_sim("+63" + field_operatorsim4.getText());
                fishpondBoxes.setBox6_sim("+63" + field_operatorsim5.getText());
                fishpondBoxes.setBox7_sim("+63" + field_operatorsim6.getText());
                fishpondBoxes.setBox8_sim("+63" + field_operatorsim7.getText());
                fishpondBoxes.setBox9_sim("+63" + field_operatorsim8.getText());
                fishpondBoxes.setBox10_sim("+63" + field_operatorsim9.getText());
                break;
        }
        simWindowListener.onSubmit(fishpondBoxes, boxCount);
        hide();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        field_operatorsim.setText("");
        boxCount = 1;
        jSpinner1.setValue((long) 1);
        filterBoxSims();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FishpondBoxSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FishpondBoxSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FishpondBoxSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FishpondBoxSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FishpondBoxSim dialog = new FishpondBoxSim(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField field_operatorsim;
    private javax.swing.JTextField field_operatorsim1;
    private javax.swing.JTextField field_operatorsim2;
    private javax.swing.JTextField field_operatorsim3;
    private javax.swing.JTextField field_operatorsim4;
    private javax.swing.JTextField field_operatorsim5;
    private javax.swing.JTextField field_operatorsim6;
    private javax.swing.JTextField field_operatorsim7;
    private javax.swing.JTextField field_operatorsim8;
    private javax.swing.JTextField field_operatorsim9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables
}
