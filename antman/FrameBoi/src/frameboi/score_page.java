/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameboi;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author aakash
 */
public class score_page {
    
    public  void calculateScore(String time,int score,String username,String difficulty){
            
        PreparedStatement ps;
        String query = "INSERT INTO `score`(`username`,`score`, `time`,`dateOfScore`,`difficulty`) VALUES (?,?,?,?,?)";
        
        try {
            ps = Connection_Sql.getConnection().prepareStatement(query);
            
            ps.setString(1, "chinnu");
            ps.setInt(2, score);
            ps.setString(3, time);
            
            long millis=System.currentTimeMillis();  
            java.sql.Date date=new java.sql.Date(millis);  
            ps.setDate(4,date);
            
            ps.setString(5,difficulty);
            
            if(ps.executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Your Score has been recorded!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(score_page.class.getName()).log(Level.SEVERE, null, ex);
            //ex.printStackTrace();
        }
    }
}
