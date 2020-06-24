/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameboi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author aakash
 */
public class Connection_Sql {
    
    public static Connection getConnection(){
     
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + "ec2-13-59-254-127.us-east-2.compute.amazonaws.com" + ":"+ "3306/SpyKey", "rup", "password");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return con;
    }
    
}
