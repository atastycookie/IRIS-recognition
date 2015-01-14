package org.cs02rm0.jirrm.segmentation;
import java.awt.image.*;
import java.awt.*;

public class Canny {

	/*
	 * Thresholds should be intelligently calculated based upon
	 * the percentage of black to white pixels that you want
	 * left over to be able to pick out either the iris or pupil.
	 */
    public BufferedImage cannypupil(BufferedImage image) {
	bimg = image;
        threshold(50);
	//pupiledge();
        //pupilThreshold();
        //ViewImage v = new ViewImage(bimg);
	return bimg;
    }
    public BufferedImage cannyiris(BufferedImage image) {
        bimg = image;
	threshold(150);
        //irisedge();
        //ViewImage v = new ViewImage(bimg);
        return bimg;
    }
    private void blur()	{
	float ninth = 1.0f / 9.0f;
	float[] blurKernel = {
        ninth, ninth, ninth,
        ninth, 1.0f/5.0f, ninth,
        ninth, ninth, ninth,
	};
	ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, blurKernel));
	BufferedImage img = new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_RGB);
	cop.filter(bimg, img);
	bimg = img;
    }
    private void pupiledge() {
	float[] edgeKernel = {
        0.0f, -1.0f, 0.0f,
        -1.0f, 9.0f, -1.0f,
        0.0f, -1.0f, 0.0f
	};
	ConvolveOp co = new ConvolveOp(new Kernel(3, 3, edgeKernel));
	BufferedImage img = new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_RGB);
	co.filter(bimg, img);
	bimg = img;
    }
    private void irisedge() {
	float[] edgeKernel = {
        0.0f, -1.0f, 0.0f,
        -1.0f, 4.0f, -1.0f,
        0.0f, -1.0f, 0.0f
	};
	ConvolveOp co = new ConvolveOp(new Kernel(3, 3, edgeKernel));
	BufferedImage img = new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_RGB);
	co.filter(bimg, img);
	bimg = img;
    }

    private void threshold(int t) {
	for (int y = 0; y < bimg.getHeight(); y++) {
      		for (int x = 0; x < bimg.getWidth(); x++) {
			int srcPixel = bimg.getRGB(x, y);
		        Color c = new Color(srcPixel);
		        int red = thresh(t,c.getRed());
		        int green = thresh(t,c.getGreen());
		        int blue = thresh(t,c.getBlue());
		        bimg.setRGB(x, y, new Color(red, green, blue).getRGB());
		}
	}
    }

    private int thresh(int t, int c) {
	    if (c > t) return 255;
	    else return 0;
    }
    
    private BufferedImage bimg;
}
