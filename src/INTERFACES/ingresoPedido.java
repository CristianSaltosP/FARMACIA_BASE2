/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INTERFACES;

import Conexion.conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author
 */
public class ingresoPedido extends javax.swing.JFrame {

    /**
     * Creates new form ingresoPedido
     */
    DefaultTableModel modelo, modelo1, modelo2;

    public ingresoPedido() {
        initComponents();

//        this.setResizable(MAXIMIZED_BOTH);
        this.setExtendedState(MAXIMIZED_BOTH);
        cargarTab();
        jPanel2.setVisible(false);
        String[] titulos = {"CODIGO", "NOMBRE COMERCIAL", "PVC", "CANTIDAD", "SUBTOTAL"};
        String[] registros = new String[5];
        modelo1 = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDetalles.setModel(modelo1);

        jPanel3.setVisible(false);

        jlNombreP1.setVisible(false);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        jLabel12.setVisible(false);

    }

    private void cargarTab() {
        String[] titulos = {"CODIGO", "NOMBRE COMERCIAL", "COMPONENTE PRIMARIO", "TIPO DE MEDICAMENTO", "FECHA DE CADUCIDAD", "MILIGRAMAGE", "LOTE", "STOCK", "PVP", "PVC", "PRESENTACIÓN", "VIA DE ADMINISTRACIÓN", "TIPO DE VENTA"};
        String[] registros = new String[13];
        modelo = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblMedicamentos.setModel(modelo);
        CadCon cc = new CadCon();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select* from MEDICAMENTOS";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("COD_MED");
                registros[1] = rs.getString("NOM_COMERCIAL");
                registros[2] = rs.getString("COMP_PRIN_MED");
                registros[3] = rs.getString("TIPO_MED");

                //Fecha
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
                registros[4] = formateador.format(rs.getDate("FECH_CAD"));
//                

                registros[5] = rs.getString("MG_MED");
                registros[6] = rs.getString("LOTE_MED");
                registros[7] = rs.getString("STOCK_MED");
                registros[8] = rs.getString("PVP_MED");
                registros[9] = rs.getString("PVC_MED");
                registros[10] = rs.getString("PREST_MED");
                registros[11] = rs.getString("VIA_ADM");
                registros[12] = rs.getString("TIPO_VENTA");
                modelo.addRow(registros);

            }
            tblMedicamentos.setModel(modelo);
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void cargarTablaLaboratorios() {
        String[] titulos = {"CODIGO", "NOMBRE LABORATORIO", "DIRECCION", "TELEFONO", "E_MAIL"};
        String[] registros = new String[5];
        modelo2 = new DefaultTableModel(null, titulos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLaboratorios.setModel(modelo2);
        CadCon cc = new CadCon();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select* from LABORATORIOS ORDER BY COD_LAB";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("COD_LAB");
                registros[1] = rs.getString("NOM_LAB");
                registros[2] = rs.getString("DIR_LAB");
                registros[3] = rs.getString("TELF_LAB");
                registros[4] = rs.getString("E_MAIL_LAB");

                modelo2.addRow(registros);

            }
            tblLaboratorios.setModel(modelo2);
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void ingresarDetallePedido() {
        CadCon cc = new CadCon();
        Connection cn = cc.conectar();
        int fila = tblMedicamentos.getSelectedRow();
        String codigo = tblMedicamentos.getValueAt(fila, 0).toString().trim();
        String numPedido = txtCodigo.getText();
        double cantidad = Double.valueOf(txtCantidad.getText());

        String sql = "";
        sql = "insert into DETALLE_PEDIDOS (COD_MED_P, COD_PED_P,CANT_MED_PED) values(?,?,?)";

        try {
            PreparedStatement psd1 = cn.prepareStatement(sql);

            psd1.setString(1, codigo);
            psd1.setString(2, numPedido);
            psd1.setDouble(3, cantidad);
            psd1.executeUpdate();
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void pedidos() {

        int fila = tblMedicamentos.getSelectedRow();

        String codigo = tblMedicamentos.getValueAt(fila, 0).toString().trim();
        String nombre = tblMedicamentos.getValueAt(fila, 1).toString().trim();
        double precio = Double.valueOf(tblMedicamentos.getValueAt(fila, 9).toString());
//            System.out.println("valor= "+codigo);
//            System.out.println("valor= "+nombre);
//            System.out.println("valor= "+precio);
        String numPedido = txtCodigo.getText();
        String cantidad = txtCantidad.getText();

        String[] registros = new String[5];
        CadCon cc = new CadCon();
        Connection cn = cc.conectar();
        String sql = "";
        String sql1 = "";
        sql = "select* from MEDICAMENTOS WHERE COD_MED='" + codigo + "'";
//      sql = "select* from SUBTOTAL_TEMPORAL_PEDIDO WHERE COD_MED_PER='" + codigo + "' AND NUM_PED_PER='"+numPedido+"' ";

        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("COD_MED");
                registros[1] = rs.getString("NOM_COMERCIAL");
                registros[2] = rs.getString("PVC_MED");
                registros[3] = cantidad;
//                registros[4] = rs.getString("SUBTOTAL");
            }

//            tblDetalles.setValueAt(cantidad, 0, 3);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        sql1 = "select* from SUBTOTAL_TEMPORAL_PEDIDO WHERE COD_MED_PER='" + codigo + "' AND NUM_PED_PER='" + numPedido + "' ";

        try {
            Statement psd1 = cn.createStatement();
            ResultSet rs1 = psd1.executeQuery(sql1);

            while (rs1.next()) {

                registros[4] = String.valueOf(rs1.getDouble("SUBTOTAL"));

            }
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        modelo1.addRow(registros);
        tblDetalles.setModel(modelo1);

    }

    public void ingresarPedido() {
        CadCon cc = new CadCon();
        Connection cn = cc.conectar();

        String FEC_HOR_PED, CI_PROV_P, CI_FAR_P;
        int NUM_PED;
        double TOTAL_PED;
        NUM_PED = Integer.valueOf(txtCodigo.getText());
        CI_PROV_P = txtCedProv.getText();
        CI_FAR_P = txtCedFarm.getText();

        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formateador.format(txtFecha.getDate());
        System.out.println(fecha);

        String sql = "";
        sql = "insert into PEDIDOS (NUM_PED, FEC_HOR_PED,TOTAL_PED,CI_PROV_P,CI_FAR_P) values(?,TO_DATE(?,'YYYY-MM-DD'),?,?,?)";

        try {
            PreparedStatement psd1 = cn.prepareStatement(sql);

            psd1.setInt(1, NUM_PED);
            psd1.setString(2, fecha);
            psd1.setDouble(3, 0);
            psd1.setString(4, CI_PROV_P);
            psd1.setString(5, CI_FAR_P);
            psd1.executeUpdate();
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void realizarPedido() {
        if (tblMedicamentos.getSelectedRow() != -1) {
            if (txtCantidad.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Ingrese la cantidad deseada");
            } else {
                jPanel2.setVisible(true);
                int f = 0;
                int cant = tblDetalles.getRowCount();
                int s = tblMedicamentos.getSelectedRow();
                String dato = String.valueOf(tblMedicamentos.getValueAt(s, 0));

                if (cant > 0) {
                    for (int j = 0; j < cant; j++) {

                        String dato1 = String.valueOf(tblDetalles.getValueAt(j, 0));

                        if (dato1.equals(dato)) {

                            f = 1;
                            break;
                        }

                    }
                    if (f == 1) {
                        JOptionPane.showMessageDialog(null, "No se puede ingresar el mismo medicamento...Si desea puede realizar una actualizocion");
                    } else {
                        ingresarDetallePedido();
                        pedidos();
                          totalPedido();
                          cargarTab();
                        txtCantidad.setText("");
                    }

                } else {
                    ingresarDetallePedido();
                    pedidos();
                     totalPedido();
                     cargarTab();
                    txtCantidad.setText("");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un medicamento para realizar el pedido");
        }

    }

    private void totalPedido() {
        CadCon cc = new CadCon();
        Connection cn = cc.conectar();
        double total = 0;
        int num = Integer.valueOf(txtCodigo.getText());
        String sql = "";
        sql = "select* from PEDIDOS WHERE NUM_PED='" + num + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                total = rs.getDouble("TOTAL_PED");

            }
            txtTotal.setText(String.valueOf(total));
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void datosProveedor() {
        CadCon cc = new CadCon();
        Connection cn = cc.conectar();
        String ced = txtCedProv.getText();
        String nombre = "", apellido = "";
        String sql = "";
        sql = "select* from PROVEEDORES WHERE CI_PROV='" + ced + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                nombre = rs.getString("NOM_PROV");
                apellido = rs.getString("APE_PROV");

            }
            cn.close();
            jlNombreP1.setVisible(true);
            jLabel10.setVisible(true);
            jlNombreP.setText(nombre);
            jlApellidoP.setText(apellido);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void datosFarmaceutico() {
        CadCon cc = new CadCon();
        Connection cn = cc.conectar();
        String ced = txtCedFarm.getText();
        String nombre = "", apellido = "";
        String sql = "";
        sql = "select* from FARMACEUTICOS WHERE CI_FAR='" + ced + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                nombre = rs.getString("NOM_FAR");
                apellido = rs.getString("APE_FAR");

            }
            cn.close();
            jLabel11.setVisible(true);
            jLabel12.setVisible(true);
            jlNombreF.setText(nombre);
            jlApellidoF.setText(apellido);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void eliminarDetalle() {

        if (tblDetalles.getSelectedRow() != -1) {

            if (tblDetalles.getSelectedRow() != -1 && JOptionPane.showConfirmDialog(null, "Estas seguro que quieres borrar el registro", "Borrar Registro",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int fila = tblDetalles.getSelectedRow();

                String codigo = String.valueOf(tblDetalles.getValueAt(fila, 0));
                int num = Integer.valueOf(txtCodigo.getText());
                CadCon cc = new CadCon();
                Connection cn = cc.conectar();
                String sql = "";
                sql = "delete from DETALLE_PEDIDOS where COD_MED_P ='" + codigo + "'AND COD_PED_P='" + num + "'";
                try {
                    PreparedStatement psd = cn.prepareStatement(sql);
                    int n = psd.executeUpdate();

                    if (n > 0) {
                        cn.close();
                        JOptionPane.showMessageDialog(null, "se borro el registro correctamente");
                        totalPedido();
                        cargarTab();
                        modelo1.removeRow(fila);

                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Si desea eliminar debe seleccione un viaje");
        }
    }

    private void limpiarTabla() {
        for (int i = 0; i < tblDetalles.getRowCount(); i++) {
            modelo1.removeRow(i);
            i -= 1;
        }
    }

    public void nuevoPedido() {
        jlNombreP1.setVisible(false);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        jLabel12.setVisible(false);
        txtCodigo.setText("");
        txtCedProv.setText("");
        txtCedFarm.setText("");
        txtCantidad.setText("");
        txtFecha.setDate(null);
        txtLaboratorio.setText("");
        txtTotal.setText("");
    }

    public void bloqueoCampos() {
        txtCodigo.setEnabled(false);
        txtCedProv.setEnabled(false);
        txtCedFarm.setEnabled(false);
    }

    public void desbloqueoCampos() {
        txtCodigo.setEnabled(true);
        txtCedProv.setEnabled(true);
        txtCedFarm.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLaboratorios = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtCedProv = new javax.swing.JTextField();
        txtCedFarm = new javax.swing.JTextField();
        txtFecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtLaboratorio = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jlNombreP1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlNombreP = new javax.swing.JLabel();
        jlApellidoP = new javax.swing.JLabel();
        jlNombreF = new javax.swing.JLabel();
        jlApellidoF = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalles = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicamentos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        txtCantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        tblLaboratorios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblLaboratorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLaboratoriosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLaboratorios);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("N° Pedido");

        jLabel5.setText("CI. Farmaceutico");

        jLabel4.setText("CI. Proveedor");

        jLabel2.setText("Fecha");

        txtCedProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCedProvFocusLost(evt);
            }
        });

        txtCedFarm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCedFarmFocusLost(evt);
            }
        });

        jLabel3.setText("Total");

        jLabel7.setText("$");

        jLabel8.setText("Laboratorio");

        jButton5.setText("...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jlNombreP1.setText("Nombre:");

        jLabel10.setText("Apellido:");

        jLabel11.setText("Nombre:");

        jLabel12.setText("Apellido:");

        jlNombreP.setFont(new java.awt.Font("Sitka Small", 1, 15)); // NOI18N

        jlApellidoP.setFont(new java.awt.Font("Sitka Small", 1, 15)); // NOI18N

        jlNombreF.setFont(new java.awt.Font("Sitka Small", 1, 15)); // NOI18N

        jlApellidoF.setFont(new java.awt.Font("Sitka Small", 1, 15)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jlNombreP1)
                                .addGap(44, 44, 44))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(31, 31, 31))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10)
                            .addGap(44, 44, 44))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(txtLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlApellidoP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCedProv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(jlNombreP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel12)
                        .addGap(56, 56, 56)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCedFarm)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlApellidoF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlNombreF, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addGap(13, 13, 13)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCedProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtCedFarm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jlNombreP, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jlNombreP1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlNombreF, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jlApellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlApellidoF, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(27, 27, 27))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalle Pedido"));

        tblDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblDetalles);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton1.setText("Realizar Pedido");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Nuevo pedido");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Salir");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("Actualizar");

        jButton7.setText("Eliminar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(29, 29, 29)
                .addComponent(jButton6)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));

        tblMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblMedicamentos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton2.setText("Pedir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Cantidad");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton2)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(49, 49, 49))
        );

        jLabel9.setText("uta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(106, 106, 106)))
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(62, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (tblMedicamentos.getSelectedRow() != -1) {
            ingresarPedido();
//            bloqueoCampos();

            JOptionPane.showMessageDialog(null, "Ingrese la cantidad que desee pedir");
            jPanel3.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un medicamento para realizar el pedido");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        realizarPedido();
        totalPedido();
        cargarTab();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
//        desbloqueoCampos();
        nuevoPedido();
//        limpiarTabla();
        tblDetalles.setModel(new DefaultTableModel());


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        cargarTablaLaboratorios();
        jDialog1.setSize(500, 300);
        jDialog1.setLocationRelativeTo(this);
        jDialog1.setVisible(true);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tblLaboratoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaboratoriosMouseClicked
        if (evt.getClickCount() == 2) {
            int row = tblLaboratorios.getSelectedRow();
            String nombre = String.valueOf(tblLaboratorios.getValueAt(row, 2));
            txtLaboratorio.setText(nombre);
            jDialog1.dispose();

        }
    }//GEN-LAST:event_tblLaboratoriosMouseClicked

    private void txtCedProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCedProvFocusLost
        datosProveedor();
    }//GEN-LAST:event_txtCedProvFocusLost

    private void txtCedFarmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCedFarmFocusLost
        datosFarmaceutico();
    }//GEN-LAST:event_txtCedFarmFocusLost

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        eliminarDetalle();

    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(ingresoPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ingresoPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ingresoPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ingresoPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ingresoPedido().setVisible(true);
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
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jlApellidoF;
    private javax.swing.JLabel jlApellidoP;
    private javax.swing.JLabel jlNombreF;
    private javax.swing.JLabel jlNombreP;
    private javax.swing.JLabel jlNombreP1;
    private javax.swing.JTable tblDetalles;
    private javax.swing.JTable tblLaboratorios;
    private javax.swing.JTable tblMedicamentos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCedFarm;
    private javax.swing.JTextField txtCedProv;
    private javax.swing.JTextField txtCodigo;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtLaboratorio;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
