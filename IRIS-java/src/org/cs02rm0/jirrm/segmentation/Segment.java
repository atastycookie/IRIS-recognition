package org.cs02rm0.jirrm.segmentation;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Segment {
    
    /*
     * 0.4 Scaling factor for hough transform
     * Use boundaries to locate top then bottom eyelid
     * Eliminate eyelids by thresholding - NaN values
     */
    
    public void segment(BufferedImage bimg) {
	BufferedImage img = new BufferedImage(bimg.getWidth(),
			bimg.getHeight(), bimg.getType());
	Graphics g = img.getGraphics();
	g.drawImage(bimg, 0, 0, null);
	circles = new HashMap();
	for (int p = 38; p<39; p=p+5) {
		final Point cp = FindCircle.findCircle(img, p, PUPIL);
		//System.out.println("Pupil X: "+cp.getX() +"\nPupil Y: " +cp.getY());
		circles.put("pupil", new Circle(cp, p));
	}
        
	img = new BufferedImage(bimg.getWidth(),
			bimg.getHeight(), bimg.getType());
	g = img.getGraphics();
	g.drawImage(bimg, 0, 0, null);
	for (int i = 100; i<105; i = i+5) {
		final Point cp = FindCircle.findCircle(img, i, IRIS);
		//System.out.println("Iris X: "+cp.getX() +"\nIris Y: " +cp.getY());
		circles.put("iris", new Circle(cp, i));
	}
	/*for (int i = 0; i<circles.size(); i++) {
		final Circle c = (Circle) circles.elementAt(i);
		Utils.drawCircle(bimg, c.cp, c.radius);
	}*/
	// To Do:
        // Find the iris boundary limited by the pupil boundary
	// Use the pupil boundary to help with the iris boundary
        // Find the upper and lower eyelid
        // Eliminate eyelids by thresholding then mark with NaN
        // return ...what! iris and pupil center coordinates and radii?
    }

    public class Circle {
	    public Point cp;
	    public int radius;
	    public Circle(final Point cp, final int radius) {
		    this.cp = cp;
		    this.radius = radius;
	    }
    }

    public Map getCircles() {
	    return circles;
    }

    private Map circles;
    public static final int IRIS = 101;
    public static final int PUPIL = 102;
}
