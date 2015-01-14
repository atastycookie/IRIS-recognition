package org.cs02rm0.jirrm.segmentation;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;

public class ViewImage {
    
    public ViewImage(BufferedImage img) {
	    JFrame f = new JFrame();
	    JLabel l = new JLabel();
	    l.setIcon(new ImageIcon(img));
	    f.getContentPane().add(l);
	    f.pack();
	    f.show();
    }
}
