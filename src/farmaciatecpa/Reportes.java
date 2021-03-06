/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmaciatecpa;

import com.backClasses.Conexion;
import com.backClasses.Producto;
import com.backClasses.Proveedor;
import com.backClasses.Receta;
import com.backClasses.Ventas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author David Carmona
 */
public class Reportes extends javax.swing.JFrame {

    Conexion con = new Conexion();
    Connection reg = con.getConnection();
    ArrayList<Producto> listaProd = new ArrayList<Producto>();
    ArrayList<Proveedor> listaProv = new ArrayList<Proveedor>();
    ArrayList<Ventas> listaVenta = new ArrayList<Ventas>();
    ArrayList<Receta> listaReceta = new ArrayList<Receta>();

    /**
     * Creates new form Reportes
     */
    public Reportes() {
        initComponents();
    }

    private void cleanAll() {
        listaProd.clear();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 204, 0));
        jLabel1.setText("CONSULTA DE REGISTROS");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setBackground(new java.awt.Color(153, 153, 255));
        jComboBox1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Productos", "Proveedores", "Receta Medica", "Ventas" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(153, 153, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton1.setText("Consultar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton2.setForeground(new java.awt.Color(153, 0, 0));
        jButton2.setText("X");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(232, 232, 232)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        jTable1.setRowSelectionAllowed(true);
        String q = "";

        String comboBox = (String) jComboBox1.getSelectedItem();
        String sentencia = "";

        if (comboBox.equals("Productos")) {
            sentencia = "Productos";
            q = "select * from productos";
        }

        if (comboBox.equals("Proveedores")) {
            sentencia = "Proveedores";
            q = "select * from proveedores";
        }
        if (comboBox.equals("Receta Medica")) {
            sentencia = "recetaMedica";
            q = "select * from recetaMedica";
        }
        if (comboBox.equals("Ventas")) {
            sentencia = "Ventas";
            q = "select * from Ventas";
        }

        PreparedStatement pst = null;
        ResultSet res = null;

        try {
            pst = reg.prepareStatement(q);
            res = pst.executeQuery();

            while (res.next()) {
                if (comboBox.equals("Productos")) {
                    Producto producto = new Producto(
                            res.getString("idProducto"),
                            res.getString("nombre_prod"),
                            res.getString("tipo_prod"),
                            res.getString("desc_prod"),
                            res.getString("sust_prod"),
                            res.getFloat("cant_prod"),
                            res.getFloat("precioVenta_prod"),
                            res.getFloat("precioCompra_prod")
                    );
                    listaProd.add(producto);

                }

                if (comboBox.equals("Proveedores")) {
                    Proveedor proveedor = new Proveedor(
                            res.getString("nombre_prv"),
                            res.getString("telefono_prv"),
                            res.getString("dir_prv"),
                            res.getString("email_prv")
                    );
                    listaProv.add(proveedor);
                }

                if (comboBox.equals("Receta Medica")) {
                    Receta receta = new Receta(
                            res.getString("folio_receta"),
                            res.getString("nombre_receta"),
                            res.getString("dir_receta"),
                            res.getString("cedula_receta")
                    );
                    listaReceta.add(receta);
                }

                if (comboBox.equals("Ventas")) {
                    Ventas venta = new Ventas(
                            res.getInt("idVenta"),
                            res.getString("folioReceta"),
                            res.getDate("fecha_venta"),
                            res.getFloat("subtotalVenta_venta"),
                            res.getFloat("subtotal_venta"),
                            res.getFloat("ivaAcum_venta"),
                            res.getFloat("ivaAcumPag_venta"),
                            res.getFloat("costoAd_venta"),
                            res.getFloat("total_venta"),
                            res.getFloat("gananciaRelativa_venta"),
                            res.getFloat("gananciaNeta_venta")
                    );
                    listaVenta.add(venta);
                }

            }
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(rootPane, e);
        }

        if (comboBox.equals("Productos")) {
            mostrarProductos();
        }

        if (comboBox.equals("Proveedores")) {
            mostrarProveedores();
        }

        if (comboBox.equals("Receta Medica")) {
            mostrarRecetas();
        }

        if (comboBox.equals("Ventas")) {
            mostrarVentas();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
        Inicio inicio = new Inicio();
        inicio.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{}
        ));
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void mostrarProductos() {
        String matrix[][] = new String[listaProd.size()][8];

        for (int i = 0; i < listaProd.size(); i++) {
            matrix[i][0] = listaProd.get(i).getCodProducto();
            matrix[i][1] = listaProd.get(i).getNomProducto();
            matrix[i][2] = listaProd.get(i).getTipProducto();
            matrix[i][3] = listaProd.get(i).getDesProducto();
            matrix[i][4] = listaProd.get(i).getSusProducto();
            matrix[i][5] = String.valueOf(listaProd.get(i).getCantProducto());
            matrix[i][6] = String.valueOf(listaProd.get(i).getPreVentaProducto());
            matrix[i][7] = String.valueOf(listaProd.get(i).getPreCompraProducto());
        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                matrix,
                new String[]{
                    "Codigo de producto", "Nombre", "Tipo", "Descripción", "Sustancia", "Cantidad", "Precio","Precio Adq"
                }
        )
        );
    }

    private void mostrarProveedores() {
        String matrix[][] = new String[listaProv.size()][4];

        for (int i = 0; i < listaProv.size(); i++) {
            matrix[i][0] = listaProv.get(i).getNombre();
            matrix[i][1] = listaProv.get(i).getTelefono();
            matrix[i][2] = listaProv.get(i).getDireccion();
            matrix[i][3] = listaProv.get(i).getEmail();

        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                matrix,
                new String[]{
                    "Nombre", "Telefono", "Direccion", "E-mail"
                }
        )
        );
    }

    private void mostrarVentas() {
        String matrix[][] = new String[listaVenta.size()][11];

        for (int i = 0; i < listaVenta.size(); i++) {
            matrix[i][0] = String.valueOf(listaVenta.get(i).getIdVenta());
            matrix[i][1] = listaVenta.get(i).getFolioReceta();
            matrix[i][2] = String.valueOf(listaVenta.get(i).getFecha());
            matrix[i][3] = String.valueOf(listaVenta.get(i).getSubtotalVenta());
            matrix[i][4] = String.valueOf(listaVenta.get(i).getSubtotal());
            matrix[i][5] = String.valueOf(listaVenta.get(i).getIvaAcum());
            matrix[i][6] = String.valueOf(listaVenta.get(i).getIvaAcumPag());
            matrix[i][7] = String.valueOf(listaVenta.get(i).getCostoAd());
            matrix[i][8] = String.valueOf(listaVenta.get(i).getTotal());
            matrix[i][9] = String.valueOf(listaVenta.get(i).getGananciaRelativa());
            matrix[i][10] = String.valueOf(listaVenta.get(i).getGananciaReal());

        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                matrix,
                new String[]{
                    "No.Venta", "FolioReceta", "Fecha", "SubtotalVend", "SubtotalAdq", "IVAxPag",
                    "IVAxCob", "CostoAdq", "CTotal", "GRelativa", "GNeta"
                }
        )
        );
    }

    private void mostrarRecetas() {
        String matrix[][] = new String[listaReceta.size()][4];

        for (int i = 0; i < listaReceta.size(); i++) {
            matrix[i][0] = listaReceta.get(i).getFolio();
            matrix[i][1] = listaReceta.get(i).getNombre();
            matrix[i][2] = listaReceta.get(i).getDireccion();
            matrix[i][3] = listaReceta.get(i).getCedula();
        }

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                matrix,
                new String[]{
                    "Folio Receta", "Nombre", "Direccion", "Cedula Profesional"
                }
        )
        );
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
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reportes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reportes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
