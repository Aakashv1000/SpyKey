/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameboi;

import static frameboi.First_Medium_Level.overlayImage;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Anant
 */
public class First_Difficult_Level extends javax.swing.JFrame {

    /**
     * Creates new form First_Difficult_Level
     */
    
    int i = 0,itemsLeft = 6, ScoreV = 100;
    
    First_Login f_l = new First_Login();
    
    Border border = BorderFactory.createLineBorder(Color.yellow, 2);
 
    Timer timer = new Timer();
    
    int CoinsL = 2, KeyL = 1, TrophyL = 1, diamondL = 1, necklaceL=1;
    String filePath = "C:\\Users\\MAHE\\Documents\\NetBeansProjects\\FrameBoi\\src\\frameboi\\";
    ArrayList<Mat> mat_images = new ArrayList<>();
    ArrayList<String> mat_labels = new ArrayList<>();
    ArrayList<Rect> rects = new ArrayList<>();
    
    public First_Difficult_Level() {
        initComponents();
        String query = "SELECT * FROM hard_level ORDER BY RAND() LIMIT 1";
        PreparedStatement ps;
        
        try {
            
            ps = Connection_Sql.getConnection().prepareStatement(query);
       
            ResultSet rs;
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Mat img = Imgcodecs.imread(filePath + rs.getString("back_img"));
                Imgproc.resize(img, img, new Size(Background.getWidth(), Background.getHeight()));
                BufferedImage image = new BufferedImage(img.width(), img.height(), BufferedImage.TYPE_3BYTE_BGR);
                byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                img.get(0, 0, data);
                Background.setIcon(new ImageIcon(image));
                
                String templates[] = rs.getString("temp_img").split(";");
                List<String> positions = Arrays.asList(rs.getString("positions").split(";"));
                Collections.shuffle(positions);
                positions = positions.subList(0, templates.length);
                itemsLeft = positions.size();
                for (int i = 0; i < positions.size(); ++i) {
                    JCheckBox cb = new JCheckBox(templates[i].split("\\.")[0]);
                    jPanel4.add(cb);
                    JLabel lb = new JLabel();
                    lb.setIcon(new ImageIcon(First_Difficult_Level.class.getResource(templates[i])));
                    Point p = new Point();
                    p.x = Integer.parseInt(positions.get(i).split(",")[0]);
                    p.y = Integer.parseInt(positions.get(i).split(",")[1]) - lb.getIcon().getIconHeight();
                    lb.setBounds(p.x, p.y, lb.getIcon().getIconWidth(), lb.getIcon().getIconHeight());
                    lb.setVisible(true);
                    Background.add(lb);
                    final int pos = i;
                    lb.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            lb.setBorder(border);
                            JCheckBox cb = (JCheckBox) jPanel4.getComponent(pos);
                            cb.setSelected(true);
                            itemsLeft -= 1;
                        }
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        TimerTask task;
        task = new TimerTask()
        {
            public void run()
            {
                if(i==100 || itemsLeft == 0)
                {
                    score_page frm = new score_page();
                    String uname = First_Login_Page.getUsername();
                    frm.calculateScore(Time_Field.getText(),ScoreV,uname,"Hard");
                    timer.cancel();
                    timer.purge();
                    JOptionPane.showMessageDialog(null,"Score = " + ScoreV);
                }
                else{
                    i++;
                    Time_Field.setText(Integer.toString(i));
                    Time_Bar.setValue(i);
                    if(ScoreV==0)
                    {
                        timer.cancel();
                        timer.purge();
                        JOptionPane.showMessageDialog(null,"Score = " + ScoreV);                        
                        Score_Text1.setText(Integer.toString(ScoreV));
                    }
                    else
                    {
                        ScoreV = ScoreV - 1;
                        Score_Text1.setText(Integer.toString(ScoreV));
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 2000, 1000);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Necklace = new javax.swing.JLabel();
        trophy = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();
        scoreTime = new javax.swing.JLabel();
        Time_Field = new javax.swing.JTextField();
        scoreLabel = new javax.swing.JLabel();
        Score_Text1 = new javax.swing.JTextField();
        Home = new javax.swing.JButton();
        Time_Bar = new javax.swing.JProgressBar();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        Necklace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NecklaceMouseClicked(evt);
            }
        });
        jPanel1.add(Necklace);
        Necklace.setBounds(380, 200, 30, 40);

        trophy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trophyMouseClicked(evt);
            }
        });
        jPanel1.add(trophy);
        trophy.setBounds(570, 280, 0, 50);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frameboi/dragonTreasure(1).jpg"))); // NOI18N
        Background.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackgroundMouseClicked(evt);
            }
        });
        jPanel1.add(Background);
        Background.setBounds(0, 0, 680, 440);

        scoreTime.setText("Time Elapsed :");
        jPanel1.add(scoreTime);
        scoreTime.setBounds(30, 450, 90, 30);

        Time_Field.setText("Timer");
        Time_Field.setName("timer_one"); // NOI18N
        Time_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Time_FieldActionPerformed(evt);
            }
        });
        jPanel1.add(Time_Field);
        Time_Field.setBounds(110, 450, 70, 30);

        scoreLabel.setText("Score :");
        scoreLabel.setToolTipText("");
        jPanel1.add(scoreLabel);
        scoreLabel.setBounds(280, 450, 70, 30);

        Score_Text1.setText("Score");
        Score_Text1.setName("Score_Text"); // NOI18N
        Score_Text1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Score_Text1ActionPerformed(evt);
            }
        });
        jPanel1.add(Score_Text1);
        Score_Text1.setBounds(340, 450, 50, 30);

        Home.setText("Home");
        Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeActionPerformed(evt);
            }
        });
        jPanel1.add(Home);
        Home.setBounds(570, 450, 70, 30);

        Time_Bar.setName("Time_Bar"); // NOI18N
        Time_Bar.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Time_Bar, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Time_Bar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public static void overlayImage(Mat background, Mat foreground, Mat output, org.opencv.core.Point location) {

        background.copyTo(output);

        for (int y = (int) Math.max(location.y, 0); y < background.rows(); ++y) {

            int fY = (int) (y - location.y);

            if (fY >= foreground.rows()) {
                break;
            }

            for (int x = (int) Math.max(location.x, 0); x < background.cols(); ++x) {
                int fX = (int) (x - location.x);
                if (fX >= foreground.cols()) {
                    break;
                }

                double opacity;
                double[] finalPixelValue = new double[4];

                opacity = foreground.get(fY, fX)[3];

                finalPixelValue[0] = background.get(y, x)[0];
                finalPixelValue[1] = background.get(y, x)[1];
                finalPixelValue[2] = background.get(y, x)[2];
                finalPixelValue[3] = background.get(y, x)[3];

                for (int c = 0; c < output.channels(); ++c) {
                    if (opacity > 0) {
                        double foregroundPx = foreground.get(fY, fX)[c];
                        double backgroundPx = background.get(y, x)[c];

                        float fOpacity = (float) (opacity / 255);
                        finalPixelValue[c] = ((backgroundPx * (1.0 - fOpacity)) + (foregroundPx * fOpacity));
                        if (c == 3) {
                            finalPixelValue[c] = foreground.get(fY, fX)[3];
                        }
                    }
                }
                output.put(y, x, finalPixelValue);
            }
        }
    }
    private void trophyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trophyMouseClicked

    }//GEN-LAST:event_trophyMouseClicked

    private void NecklaceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NecklaceMouseClicked

        
    }//GEN-LAST:event_NecklaceMouseClicked

    private void Time_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Time_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Time_FieldActionPerformed

    private void Score_Text1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Score_Text1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Score_Text1ActionPerformed

    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        // TODO add your handling code here:
        new First_Difficulty().setVisible(true);
        dispose();
    }//GEN-LAST:event_HomeActionPerformed

    private void BackgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackgroundMouseClicked
       for(int count=0; count<rects.size(); ++count){
            if(rects.get(count).contains(new org.opencv.core.Point(evt.getX(), evt.getY()))){
                JCheckBox cb = (JCheckBox) jPanel3.getComponent(count);
                cb.setSelected(true);
                itemsLeft-=1;
                break;
            }
        }  
    }//GEN-LAST:event_BackgroundMouseClicked

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
            java.util.logging.Logger.getLogger(First_Difficult_Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(First_Difficult_Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(First_Difficult_Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(First_Difficult_Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new First_Difficult_Level().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JButton Home;
    private javax.swing.JLabel Necklace;
    private javax.swing.JTextField Score_Text1;
    private javax.swing.JProgressBar Time_Bar;
    private javax.swing.JTextField Time_Field;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel scoreTime;
    private javax.swing.JLabel trophy;
    // End of variables declaration//GEN-END:variables
}
