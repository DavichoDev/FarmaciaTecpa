/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmaciatecpa;

import com.backClasses.Conexion;
import com.backClasses.Venta;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David Carmona
 */
public class addVentas extends javax.swing.JFrame implements Printable {

    /**
     * VARIABLE QUE SE AGREGA AQUI SE ELIMINA EN cleanAll().
     */
    Conexion con = new Conexion();
    Connection reg = con.getConnection();

    ArrayList<Venta> lista = new ArrayList<Venta>();

    private float finalTemp;
    final float IVA = 0.16f;
    private float IVA_acum = 0f;
    private float IVA_acumPag = 0f;
    private float subTotal = 0f;
    private float subTotalVenta = 0f;
    private float Total = 0f;
    public static String newID;
    private float gananciaRelativa = 0f;
    private float gananciaReal = 0f;
    private float costoAd = 0f;
    protected boolean control = false;
    protected static int esControlado = 0;
    protected static boolean SoloUnaReceta = false;
    protected static String lastIdReceta;
    /**
     * * EMPIEZA FALSO.
     */
    protected String esControladoID;

    //CONSTRUCTOR
    public addVentas() {
        initComponents();
        jTable1.setRowSelectionAllowed(true);
        jTextField2.setEditable(false);
        jTextField3.setEditable(false);
        jTextField4.setEditable(false);
        jTextField6.setEditable(false);
        jTextField1.setEditable(true);
        jCheckBox2.setSelected(true);
    }

    //LIMPIA LAS VARIABLES
    private void cleanAll() {
        finalTemp = 0;
        IVA_acum = 0f;
        subTotal = 0f;
        subTotalVenta = 0f;
        Total = 0f;
        gananciaRelativa = 0f;
        gananciaReal = 0f;
        lastIdReceta = "";
        newID = "";
        esControladoID = "";
        esControlado = 0;
        SoloUnaReceta = false;
        jTextField2.setText(null);
        jTextField3.setText(null);
        jTextField4.setText(null);
        jTextField5.setText(null);
        jTextField6.setText(null);
        lista.clear();
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Clave producto", "Nombre", "Precio"
                }
        ));
    }

    //OBTIENE DATOS DE LA TABLA VENTAS
    private void obtenerDatos() {
        jTable1.setRowSelectionAllowed(true);
        String q
                = "SELECT idProducto,nombre_prod,cant_prod,precioVenta_prod"
                + ",precioCompra_prod,tipo_prod FROM Productos WHERE idProducto =?";

        PreparedStatement pst = null;
        ResultSet res = null;

        try {

            pst = reg.prepareStatement(q);
            pst.setString(1, jTextField1.getText());

            res = pst.executeQuery();

            if (res.next()) {

                if (comprobarExistencia(res.getString("idProducto"))) {

                    Venta venta = new Venta(1,
                            res.getString("idProducto"),
                            res.getString("nombre_prod"),
                            res.getFloat("precioVenta_prod"),
                            res.getFloat("precioCompra_prod")
                    );

                    if (res.getString("tipo_prod").equals("CONTROLADO")) {
                        this.esControlado = addReceta.variable;
                    }

                    if (esControlado == 1 && jCheckBox2.isSelected()) {
                        JOptionPane.showMessageDialog(rootPane, "Para vender productos controlados primero"
                                + " ingrese la receta médica");
                    } else {
                        //RE REGISTRA LA SALIDA DEL PRODUCTO.
                        disminuirExistencia(res.getString("idProducto"), res.getFloat("cant_prod"));

                        //EL SUBTOTAL ES IGUAL A EL PRECIO REAL DE LA VENTA.
                        subTotal += (res.getFloat("precioCompra_prod") * 0.84f);    //Compra
                        subTotalVenta += (res.getFloat("precioVenta_prod") * 0.84f);//Venta

                        //Este IVA es el que se PAGO.
                        IVA_acum += (res.getFloat("precioCompra_prod") * IVA);
                        //ESTE IVA es el que se DEBE.
                        IVA_acumPag += (res.getFloat("precioVenta_prod") * IVA);

                        costoAd += res.getFloat("precioCompra_prod");
                        Total += res.getFloat("precioVenta_prod");

                        gananciaRelativa = Total - costoAd;
                        gananciaReal = subTotalVenta - subTotal;

                        this.lista.add(venta);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Este producto no tiene existencias");
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Este producto no esta registrado");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

        jTextField2.setText(String.format("%.2f", Total));
        jTextField3.setText(String.format("%.2f", IVA_acumPag));
        jTextField4.setText(String.format("%.2f", Total));

        mostrar();
        con.desconectar();
        jTextField1.setText(null);
    }

    protected void obtenerDatosID(String id) {

        jTable1.setRowSelectionAllowed(true);
        String q
                = "SELECT idProducto,nombre_prod,cant_prod,precioVenta_prod,"
                + "precioCompra_prod,tipo_prod FROM Productos WHERE idProducto =?";

        PreparedStatement pst = null;
        ResultSet res = null;

        try {
            con.getConnection();
            pst = reg.prepareStatement(q);
            pst.setString(1, id);

            res = pst.executeQuery();

            if (res.next()) {

                if (comprobarExistencia(res.getString("idProducto"))) {

                    Venta venta = new Venta(1,
                            res.getString("idProducto"),
                            res.getString("nombre_prod"),
                            res.getFloat("precioVenta_prod"),
                            res.getFloat("precioCompra_prod")
                    );

                    if (res.getString("tipo_prod").equals("CONTROLADO")) {
                        this.esControlado = addReceta.variable;
                        System.out.println("CUADO ES CONTROLADO: " + addReceta.variable);
                    }

                    if (esControlado == 1 && jCheckBox2.isSelected()) {
                        JOptionPane.showMessageDialog(rootPane, "Para vender productos controlados primero"
                                + " ingrese la receta médica");
                    } else {
                        //RE REGISTRA LA SALIDA DEL PRODUCTO.
                        disminuirExistencia(res.getString("idProducto"), res.getFloat("cant_prod"));

                        //EL SUBTOTAL ES IGUAL A EL PRECIO REAL DE LA VENTA.
                        subTotal += (res.getFloat("precioCompra_prod") * 0.84f);    //Compra
                        subTotalVenta += (res.getFloat("precioVenta_prod") * 0.84f);//Venta

                        //Este IVA es el que se PAGO.
                        IVA_acum += (res.getFloat("precioCompra_prod") * IVA);
                        //ESTE IVA es el que se DEBE.
                        IVA_acumPag += (res.getFloat("precioVenta_prod") * IVA);

                        costoAd += res.getFloat("precioCompra_prod");
                        Total += res.getFloat("precioVenta_prod");

                        gananciaRelativa = Total - costoAd;
                        gananciaReal = subTotalVenta - subTotal;

                        this.lista.add(venta);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Este producto no tiene existencias");
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Este producto no esta registrado");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

        jTextField2.setText(String.format("%.2f", Total));
        jTextField3.setText(String.format("%.2f", IVA_acum));
        jTextField4.setText(String.format("%.2f", Total));

        mostrar();
        con.desconectar();
        jTextField1.setText(null);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setBackground(new java.awt.Color(153, 204, 0));
        jTable1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cantidad", "Clave Producto", "Nombre", "Precio", "Numero de elemento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(20);
        jScrollPane1.setViewportView(jTable1);

        jTextField1.setBackground(new java.awt.Color(153, 204, 0));
        jTextField1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel1.setText("Ingrese codigo del producto:");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 204, 0));
        jLabel2.setText("REGISTRO DE VENTA");

        jButton1.setBackground(new java.awt.Color(153, 153, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jButton1.setText("Realizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel3.setText("Total");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel4.setText("Subtotal");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel5.setText("IVA");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel6.setText("Efectivo");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jLabel7.setText("Cambio");

        jTextField2.setBackground(new java.awt.Color(153, 204, 0));
        jTextField2.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        jTextField3.setBackground(new java.awt.Color(153, 204, 0));
        jTextField3.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        jTextField4.setBackground(new java.awt.Color(153, 204, 0));
        jTextField4.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        jTextField5.setBackground(new java.awt.Color(153, 204, 0));
        jTextField5.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });

        jTextField6.setBackground(new java.awt.Color(153, 204, 0));
        jTextField6.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Eliminar registro");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Introduccion manual");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton3.setText("Agregar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setText("Consultar producto por nombre");
        jButton4.setToolTipText("");
        jButton4.setActionCommand("");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setText("Actualizar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton6.setText("Añadir receta médica");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jCheckBox2.setText("Validar Productos Controlados");

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(204, 0, 0));
        jButton7.setText("Cerrar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jCheckBox1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jTextField4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jTextField3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField6))
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton7))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jButton5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton6)
                                .addComponent(jCheckBox2)))
                        .addContainerGap())
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    //REGISTRAR VENTA
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (this.control == false) {
            //Variables para query
            con.getConnection();
            String query = "INSERT INTO ventas(idVenta,folioReceta, fecha_venta, "
                    + "subtotalVenta_venta, subtotal_venta,"
                    + "ivaAcum_venta, ivaAcumPag_venta,"
                    + "costoAd_venta, total_venta,"
                    + "gananciaRelativa_venta, gananciaNeta_venta) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            long last = 0;

            if (jTable1.getRowCount() == 0 || jTextField5.getText().equals("")) {

                JOptionPane.showMessageDialog(rootPane, "Ingrese correctamente los datos");

            } else {
                if (Float.parseFloat(jTextField5.getText()) > Total ||Float.parseFloat(jTextField5.getText()) == Total) {

                    String cambio = String.format("%.2f", Float.parseFloat(jTextField5.getText()) - Total);

                    jTextField6.setText(cambio);

                    java.util.Date d = new java.util.Date();
                    java.sql.Date fecha = new java.sql.Date(d.getTime());

                    try {

                        PreparedStatement stm = reg.prepareStatement(query);
                        stm.setNull(1, 0);
                        stm.setDate(3, fecha);

                        stm.setFloat(4, subTotalVenta);
                        stm.setFloat(5, subTotal);

                        stm.setFloat(6, IVA_acum);
                        stm.setFloat(7, IVA_acumPag);

                        stm.setFloat(8, costoAd);
                        stm.setFloat(9, Total);

                        stm.setFloat(10, gananciaRelativa);
                        stm.setFloat(11, gananciaReal);

                        if (SoloUnaReceta) {
                            stm.setString(2, lastIdReceta);
                        } else {
                            stm.setString(2, "SIN RECETA");
                        }

                        stm.executeUpdate();

                        last = getUltimoId();
                        finalTemp = last;

                        String insertQuery = "INSERT INTO detalle_venta(idVenta,idProducto,cant_Dventa,"
                                + "iva_Dventa,subtotal_Dventa,total_Dventa)"
                                + " VALUES (?,?,?,?,?,?)";

                        stm = reg.prepareStatement(insertQuery);

                        for (int i = 0; i < lista.size(); i++) {
                            stm.setLong(1, last); //idVenta
                            stm.setString(2, lista.get(i).getId());//IdProducto

                            stm.setFloat(3, lista.get(i).getCantidad());//CantidadProducto
                            stm.setFloat(4, (lista.get(i).getPrecio() * IVA));//IVA del producto(precioCompra)
                            stm.setFloat(5, (lista.get(i).getPrecioCompra() * 0.84f));//Precio real(SIN IVA)
                            stm.setFloat(6, lista.get(i).getPrecio());//Total vendido
                            stm.executeUpdate();
                        }

                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(rootPane, e);
                    }
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(new BillPrintable(), getPageFormat(pj));
                    try {
                        pj.print();

                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(rootPane, "Venta registrada");
                    con.desconectar();
                    cleanAll();
                    addReceta.clearAllReceta();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Ingrese correctamente los datos");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debes actualizar la tabla primero, presiona el boton");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    //Metodo regresa el ultimo idVenta registrado
    private long getUltimoId() throws SQLException {

        String query = "SELECT MAX(idVenta) FROM ventas";
        PreparedStatement pst = null;
        ResultSet res = null;
        long ultimo = 0;

        try {

            pst = reg.prepareStatement(query);
            res = pst.executeQuery();

            while (res.next()) {
                ultimo = res.getLong(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        return ultimo;
    }

    //Metodo regresa el ultimo idVenta registrado
    private String getUltimoFolio() throws SQLException {

        String query = "SELECT folio_receta FROM RecetaMedica ORDER BY folio_receta ASC limit 1";
        PreparedStatement pst = null;
        ResultSet res = null;
        String ultimo = "";

        try {

            pst = reg.prepareStatement(query);
            res = pst.executeQuery();

            while (res.next()) {
                ultimo = res.getString(1);
                System.out.println("ultimo id" + ultimo);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        return ultimo;
    }

    //SE REALIZALA OPERACION EN LA TABLA
    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        char validar = evt.getKeyChar();
        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingresa solo numeros en este campo.");
        }

        if (jTextField1.getText().length() > 12 && !jCheckBox1.isSelected()) {
            obtenerDatos();
            jTable1.setRowSelectionAllowed(true);
        }
    }//GEN-LAST:event_jTextField1KeyTyped

    //VALIDA ENTRADA DE NUMEROS
    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        char validar = evt.getKeyChar();
        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingresa solo numeros en este campo.");
        }
    }//GEN-LAST:event_jTextField5KeyTyped

    //BOTON DE ELIMINAR
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.control == false) {
            if (jTable1.getSelectedColumn() == 4) {
                DefaultTableModel mod = (DefaultTableModel) jTable1.getModel();
                jTable1.setRowSelectionAllowed(true);

                String indexTable = String.valueOf(jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getSelectedColumn()));
                int indexJTable = Integer.parseInt(indexTable);

                int removed = indexJTable;

                jTable1.setCellSelectionEnabled(true);

                //CALCULOS FINANCIEROS
                subTotal = subTotal - (lista.get(removed).getPrecioCompra() * 0.84f);
                subTotalVenta = subTotalVenta - (lista.get(removed).getPrecio() * 0.84f);

                IVA_acum = IVA_acum - (lista.get(removed).getPrecioCompra() * IVA);
                IVA_acumPag = IVA_acumPag - (lista.get(removed).getPrecio() * IVA);

                costoAd -= lista.get(removed).getPrecioCompra();
                Total -= lista.get(removed).getPrecio();

                gananciaRelativa = Total - costoAd;
                gananciaReal = subTotalVenta - subTotal;

                //*FIN* Calculos financieros
                jTextField4.setText(String.format("%.2f", Total));
                jTextField2.setText(String.format("%.2f", Total));
                jTextField3.setText(String.format("%.2f", IVA_acumPag));

                aumentarExistencia(lista.get(removed).getId(),
                        getCantidad(lista.get(removed).getId()));

                mod.removeRow(indexJTable);
                lista.remove(removed);

                mostrar();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Para poder eliminar algun "
                        + "producto presione la quinta columna del elemento a eliminar");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debes actualizar la tabla primero, presiona el boton");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if (this.control == false) {
            String nuevoId = jTextField1.getText();
            if ((jCheckBox1.isSelected() && nuevoId.length() > 0)
                    || (jCheckBox1.isSelected() && nuevoId.length() <= 12)
                    || (jCheckBox1.isSelected() && nuevoId.length() > 13)) {
                obtenerDatos();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Primero selecciona la casilla o llena el campo");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debes actualizar la tabla primero, presiona el boton");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    //BOTON PARA CONSULTAR PRODUCTO
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (this.control == false) {
            addConsultaProd addWindow = new addConsultaProd();
            this.control = true;
            addWindow.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debes actualizar la tabla primero, presiona el boton");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    //METODO QUE DESDE OTRA CLASE ME PERMITE TRAR VARIABLE DE ELLA E INVOCARLO EN ESTA
    public static void setOurId(String id) {
        newID = id;
    }

    public static void setOurFolio(String id) {
        lastIdReceta = id;
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (this.newID == null) {
            JOptionPane.showMessageDialog(rootPane, "La tabla esta actualizada");
            this.control = false;
        } else {
            obtenerDatosID(newID);
            this.newID = null;
            this.control = false;
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (this.control == false) {
            if (SoloUnaReceta == false) {
                addReceta receta = new addReceta();
                receta.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Solo se puede registrar una receta por venta");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Debes actualizar la tabla primero, presiona el boton");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        cleanAll();
        addReceta.clearAllReceta();
        this.dispose();
        setVisible(false);
        Inicio inicio = new Inicio();
        inicio.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    //MOSTRAR EN LA JTABLE
    public void mostrar() {
        String matrix[][] = new String[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            matrix[i][0] = String.valueOf(lista.get(i).getCantidad());
            matrix[i][1] = String.valueOf(lista.get(i).getId());
            matrix[i][2] = lista.get(i).getNombre();
            matrix[i][3] = String.valueOf(lista.get(i).getPrecio());
            matrix[i][4] = String.valueOf(i);
        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                matrix,
                new String[]{
                    "Cantidad", "Clave Producto", "Nombre", "Precio", "Numero de elemento"
                }
        )
        );
    }

    public void aumentarExistencia(String id, float cant) {

        String query = "UPDATE Productos SET cant_prod = ? WHERE idProducto =" + id;

        try {
            PreparedStatement stm = reg.prepareStatement(query);
            stm.setFloat(1, cant + 1);
            stm.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

    }

    public void disminuirExistencia(String id, float cant) {

        String query = "UPDATE Productos SET cant_prod = ? WHERE idProducto =" + id;

        try {
            PreparedStatement stm = reg.prepareStatement(query);
            stm.setFloat(1, cant - 1);
            stm.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }

    }

    public boolean comprobarExistencia(String id) {
        String query = "SELECT cant_prod FROM Productos WHERE idProducto = ?";

        PreparedStatement pst = null;
        ResultSet res = null;

        try {
            con.getConnection();
            pst = reg.prepareStatement(query);
            pst.setString(1, id);
            res = pst.executeQuery();

            if (res.next()) {
                if (res.getFloat("cant_prod") > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        return false;
    }

    public float getCantidad(String id) {
        String query = "SELECT cant_prod FROM Productos WHERE idProducto = ?";

        PreparedStatement pst = null;
        ResultSet res = null;

        try {
            con.getConnection();
            pst = reg.prepareStatement(query);
            pst.setString(1, id);
            res = pst.executeQuery();

            if (res.next()) {
                return res.getFloat("cant_prod");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
        return 0;
    }

    /**
     * METODOS PARA IMPRIMIR TICKET.
     */
    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double middleHeight = 8.0;
        double headerHeight = 2.0;
        double footerHeight = 2.0;
        double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
        double height = convert_CM_To_PPI(headerHeight + middleHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(
                0,
                10,
                width,
                height - convert_CM_To_PPI(1)
        );   //define boarder size    after that print area width is about 180 points

        pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
        pf.setPaper(paper);

        return pf;
    }

    protected static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class BillPrintable implements Printable {

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {

            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;

                double width = pageFormat.getImageableWidth();

                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                ////////// code by alqama//////////////
                FontMetrics metrics = g2d.getFontMetrics(new Font("Arial", Font.BOLD, 7));
                //    int idLength=metrics.stringWidth("000000");
                //int idLength=metrics.stringWidth("00");
                int idLength = metrics.stringWidth("000");
                int amtLength = metrics.stringWidth("000000");
                int qtyLength = metrics.stringWidth("00000");
                int priceLength = metrics.stringWidth("000000");
                int prodLength = (int) width - idLength - amtLength - qtyLength - priceLength - 17;

                //    int idPosition=0;
                //    int productPosition=idPosition + idLength + 2;
                //    int pricePosition=productPosition + prodLength +10;
                //    int qtyPosition=pricePosition + priceLength + 2;
                //    int amtPosition=qtyPosition + qtyLength + 2;
                int productPosition = 0;
                int discountPosition = prodLength + 5;
                int pricePosition = discountPosition + idLength + 10;
                int qtyPosition = pricePosition + priceLength + 4;
                int amtPosition = qtyPosition + qtyLength;
                int espacios = 0;
                String show;
                try {

                    /*Draw Header*/
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;
                    int headerRectHeighta = 40;

                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawString("-------------------------------------", 12, y);
                    y += yShift;
                    g2d.drawString("           FARMACIA TECPA            ", 12, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += headerRectHeight;
                    g2d.drawString(" ID de venta: " + finalTemp + "", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" CANTIDAD       NOMBRE       PRECIO  ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += headerRectHeight;
                    for (int i = 0; i < lista.size(); i++) {
                        String nombre = lista.get(i).getNombre();
                        if (nombre.length() < 10) {
                            espacios = 10 - nombre.length();
                            show = String.format("%-"+espacios+"s", nombre);
                        } else {
                            show = nombre.substring(0,9);
                        }             
                        g2d.drawString("   " + lista.get(i).getCantidad()
                                + "        " + show
                                + "         " + lista.get(i).getPrecio() + "", 10, y);
                        y += yShift;
                    }
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Efectivo: " + jTextField5.getText()
                            + "               ", 10, y);
                    y += yShift;
                    g2d.drawString(" TOTAL: " + Total + "               ", 10, y);
                    y += yShift;
                    g2d.drawString(" Cambio: " + jTextField6.getText()
                            + "               ", 10, y);
                    y += yShift;
                    g2d.drawString(" IVA: " + String.format("%.2f", IVA_acumPag)
                            + "            ", 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("        GRACIAS POR SU VISITA        ", 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;

//            g2d.setFont(new Font("Monospaced",Font.BOLD,10));
//            g2d.drawString("Customer Shopping Invoice", 30,y);y+=yShift; 
                } catch (Exception r) {
                    r.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

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
            java.util.logging.Logger.getLogger(addVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
