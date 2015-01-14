package org.cs02rm0.jirrm.segmentation;
import javax.imageio.*;
import java.io.*;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class FindCircle {
    
    static Point findCircle(BufferedImage bimg, int radius, int type) {
        //Canny edge detector
        Canny canny = new Canny();
        if (type == Segment.PUPIL) bimg = canny.cannypupil(bimg);
        else if (type == Segment.IRIS) bimg = canny.cannyiris(bimg);
        else System.out.println("Unrecognized findcircle request: " +type);

        HoughTransform ht = new HoughTransform(bimg, radius, 1);
        Point cp = ht.getCenterPoint(0);
        //To Do:
        //Adjust Gamma
        //Non-maxima suppression
        //Hysteresis or double thresholding
        //Hough circle transform (between upper radius a and lower radius b)
        //Mark on NaN values
        //return coordinates of circle center
	return cp;
    }
}
