/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameboi;

import static frameboi.First_Medium_Level.overlayImage;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
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
public class First_Easy_Level extends javax.swing.JFrame {

    /**
     * Creates new form First_Easy_Level
     */
    
    First_Login f_l = new First_Login();
    
    int i = 0,itemsLeft = 4, ScoreV = 100;
    
    Border border = BorderFactory.createLineBorder(Color.yellow, 2);
 
    Timer timer = new Timer();
    
    int CoinsL = 2, KeyL = 1, HBL = 1;
    String filePath="/home/aakash/antman/FrameBoi/src/frameboi";
    ArrayList<Mat> mat_images = new ArrayList<>();
    ArrayList<String> mat_labels = new ArrayList<>();
    ArrayList<Rect> rects = new ArrayList<>();
    
    public First_Easy_Level() {
        initComponents();
        
        String query = "SELECT * FROM easy_level ORDER BY RAND() LIMIT 1";
        PreparedStatement ps;
        
        try {
            
            ps = Connection_Sql.getConnection().prepareStatement(query);
       
            ResultSet rs;
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Mat img = Imgcodecs.imread(filePath + rs.getString("back_img"), Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
                Imgproc.resize(img, img, new Size(Background.getWidth(), Background.getHeight()));
                Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2BGRA);
                String templates[] = rs.getString("temp_img").split(";");
                List<String> positions = Arrays.asList(rs.getString("positions").split(";"));
                Collections.shuffle(positions);
                positions = positions.subList(0, templates.length);
                for (int i = 0; i < positions.size(); ++i) {
                    mat_labels.add(templates[i].split("/")[0]);
                    JCheckBox cb = new JCheckBox(templates[i].split("/")[0]);
                    jPanel3.add(cb);
                    Mat temp = Imgcodecs.imread(filePath + templates[i], Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
                    Mat oput = new Mat(img.rows(), img.cols(), img.type());
                    Point p = new Point();
                    p.x = Integer.parseInt(positions.get(i).split(",")[0]);
                    p.y = Integer.parseInt(positions.get(i).split(",")[1]) - temp.height();
                    overlayImage(img, temp, oput, new org.opencv.core.Point(p.x, p.y));
                    mat_images.add(new Mat(oput, new Rect(new org.opencv.core.Point(p.x, p.y), new Size(temp.width(), temp.height()))));
                    img = oput;
                }
                Imgproc.cvtColor(img, img, Imgproc.COLOR_BGRA2BGR);
                mat_images.add(img);
                BufferedImage image = new BufferedImage(img.width(), img.height(), BufferedImage.TYPE_3BYTE_BGR);
                byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                img.get(0, 0, data);

                Background.setIcon(new ImageIcon(image));

                for (int i = 0; i < mat_images.size() - 1; ++i) {
                    rects.add(TemplateMatching.findMatch(img, mat_images.get(i)));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(First_Medium_Level.class.getName()).log(Level.SEVERE, null, e);
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
                    frm.calculateScore(Time_Field.getText(),ScoreV,uname,"Easy");
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
                        Score_Text.setText(Integer.toString(ScoreV));
                    }
                    else
                    {
                        ScoreV = ScoreV - 1;
                        Score_Text.setText(Integer.toString(ScoreV));
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

        BlueScreen = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Solution = new javax.swing.JButton();
        Hint = new javax.swing.JButton();
        Home = new javax.swing.JButton();
        Time_Field = new javax.swing.JTextField();
        scoreTime = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        Score_Text = new javax.swing.JTextField();
        Background = new javax.swing.JLabel();
        Time_Bar = new javax.swing.JProgressBar();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jPanel2.setLayout(null);

        Solution.setText("Solution");
        Solution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SolutionActionPerformed(evt);
            }
        });
        jPanel2.add(Solution);
        Solution.setBounds(10, 10, 90, 30);

        Hint.setText("Hint");
        Hint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HintActionPerformed(evt);
            }
        });
        jPanel2.add(Hint);
        Hint.setBounds(110, 10, 79, 30);

        Home.setText("Home");
        Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeActionPerformed(evt);
            }
        });
        jPanel2.add(Home);
        Home.setBounds(570, 10, 70, 30);

        Time_Field.setText("Timer");
        Time_Field.setName("timer_one"); // NOI18N
        Time_Field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Time_FieldActionPerformed(evt);
            }
        });
        jPanel2.add(Time_Field);
        Time_Field.setBounds(310, 10, 70, 30);

        scoreTime.setText("Time Elapsed :");
        jPanel2.add(scoreTime);
        scoreTime.setBounds(220, 10, 90, 30);

        scoreLabel.setText("Score :");
        scoreLabel.setToolTipText("");
        jPanel2.add(scoreLabel);
        scoreLabel.setBounds(410, 16, 70, 20);

        Score_Text.setText("Score");
        Score_Text.setName("Score_Text"); // NOI18N
        Score_Text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Score_TextActionPerformed(evt);
            }
        });
        jPanel2.add(Score_Text);
        Score_Text.setBounds(460, 10, 50, 30);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 480, 660, 50);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frameboi/mystery_easy.jpg"))); // NOI18N
        Background.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackgroundMouseClicked(evt);
            }
        });
        jPanel1.add(Background);
        Background.setBounds(0, 60, 650, 420);

        Time_Bar.setName("Time_Bar"); // NOI18N
        Time_Bar.setStringPainted(true);
        jPanel1.add(Time_Bar);
        Time_Bar.setBounds(-4, -6, 660, 70);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        // TODO add your handling code here:
        new First_Difficulty().setVisible(true);
        dispose();
    }//GEN-LAST:event_HomeActionPerformed

    private void Time_FieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Time_FieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Time_FieldActionPerformed

    private void HintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HintActionPerformed
        // TODO add your handling code here:
              ScoreV = ScoreV - 20;
              JOptionPane.showMessageDialog(null, "Items Left: " + itemsLeft);
    }//GEN-LAST:event_HintActionPerformed

    private void SolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SolutionActionPerformed
        ScoreV = 0;
        Score_Text.setText("0");
        Background.setIcon(new ImageIcon(filePath+"sonuc.jpg"));
    }//GEN-LAST:event_SolutionActionPerformed

    private void Score_TextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Score_TextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Score_TextActionPerformed

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
            java.util.logging.Logger.getLogger(First_Easy_Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(First_Easy_Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(First_Easy_Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(First_Easy_Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new First_Easy_Level().setVisible(true);
            }
        });
    }
    
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JLabel BlueScreen;
    private javax.swing.JButton Hint;
    private javax.swing.JButton Home;
    private javax.swing.JTextField Score_Text;
    private javax.swing.JButton Solution;
    private javax.swing.JProgressBar Time_Bar;
    private javax.swing.JTextField Time_Field;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JLabel scoreTime;
    // End of variables declaration//GEN-END:variables
}
