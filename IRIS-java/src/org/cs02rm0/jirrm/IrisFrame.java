package org.cs02rm0.jirrm;
import org.cs02rm0.jirrm.segmentation.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class IrisFrame extends javax.swing.JFrame {
    
    public IrisFrame() {
        initComponents();
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setTitle("Iris Recognition");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.X_AXIS));

        jPanel2.setPreferredSize(new java.awt.Dimension(460, 300));
        jButton1.setText("Select image");
        jButton1.setActionCommand("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.add(jButton1);
        jPanel2.add(jPanel3);
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.add(jLabel1, java.awt.BorderLayout.CENTER);
        jPanel2.add(jPanel4);
        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jMenu1.setText("File");
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItem1);
        jMenuBar1.add(jMenu1);
        setJMenuBar(jMenuBar1);

        pack();
    }//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        exit();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // File select
        JFileChooser ch = new JFileChooser();
        BufferedImage img;
        File f;
        ch.setCurrentDirectory(new File("."));  
        ch.setDialogTitle("Pick an appropriate iris image");
        ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
		///new Thread() {
			//public void run() {
            			f = ch.getSelectedFile();
			            try {
			            	img = ImageIO.read(f);
			            	Segment segment = new Segment();
			            	segment.segment(img);
			            	ImageIcon iris = new ImageIcon(img);
			            	jLabel1.setIcon(iris);
			            } catch (Exception e)	{
					e.printStackTrace();
			            }
			//}
		//}.start();
        } else f = null;
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        exit();
    }//GEN-LAST:event_exitForm
    private void exit() {
        System.exit(0);
    }
    public static void main(String args[]) {
        System.setProperty("java.util.prefs.syncInterval","2000000");
        new IrisFrame().show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
    
}
