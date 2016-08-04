/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Stalin
 */
public class conexion {
    
    
    Connection con=null;  
    public Connection conectar(){
    try{
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","FARMACIA_R_S","FARMACIA_R_S123");
        //el primer parametro hace referencia adonde se va a conectar(base de datos), el segundo parametro es root, clave (si no existe se pone cadena vacia)
//       JOptionPane.showMessageDialog(null, "Estas conectado a la base");
    }catch(Exception ex){
         JOptionPane.showMessageDialog(null, "ERROR EN LA CONEXION "+ ex);
    } return con; 
 }
        public static void main(String[] args) {
       conexion cone = new conexion();
       cone.conectar();
    }
    
}
