package org.cs02rm0.jirrm;

import org.cs02rm0.jirrm.segmentation.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class Main {
    
    public Main(String filename) {
        System.out.println("\n\n\tIris Recognition\n\n");
        System.out.println("Reading image...");
	File file = new File(filename);
        if (file.exists()) {
            System.out.println("Image located...");
        } else {
            System.out.println("Error! Check you've specified the correct path to the image and you have the permissions to view it");
            System.exit(1);
        }
	BufferedImage bimg = null;
	try {
		bimg = ImageIO.read(file);
		/* Segmentation code */
		Segment segment = new Segment();
		segment.segment(bimg);
		/* Normal encoding */
		/* Feature encoding */
	} catch (Exception e)	{
		System.out.println(e);
	}
    }
    
    public static void main(String[] args) {
	    if (args.length != 1) System.out.println("Usage: java Main filename.jpg");
	    else new Main(args[0]);
		
    }
    private BufferedImage bimg;
}
