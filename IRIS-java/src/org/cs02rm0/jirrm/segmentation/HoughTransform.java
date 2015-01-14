package org.cs02rm0.jirrm.segmentation;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class HoughTransform {
    
    public HoughTransform(BufferedImage bimg, float radius, int maxCircles) {
        this.bimg = bimg;
        this.radius = radius;
        this.maxCircles = maxCircles;
        this.width = bimg.getWidth();
        this.height = bimg.getHeight();
        getImageValues();
        doHoughTransform();
        centerPoint = new Point[maxCircles];
        getCenterPoints(maxCircles);
    }
    public Point getCenterPoint(int cp) {
        return centerPoint[cp];
    }
    private synchronized void getImageValues() {
	int pixels[] = new int[width * height];
	PixelGrabber pixGrabber = new PixelGrabber(bimg, 0, 0, width, height, pixels, 0, width);
	try {
		pixGrabber.grabPixels();
	} catch(InterruptedException e) {
		System.out.println("Interrupted Exception: grabPixels()");
	}
	imageValues = new int[width][height];
	for(int y = 0; y < height; y++) {
		for(int x = 0; x < width; x++)
			imageValues[x][y] = pixels[y*width + x] & 0xff;
	}
    }
    public int getMaxNumCircles() {
        return maxCircles;
    }
    public void setRadius(float rad) {
        radius = rad;
        doHoughTransform();
        getCenterPoints(maxCircles);
    }
    public void setMaxNumCircles(int maxCirc) {
        maxCircles = maxCirc;
        getCenterPoints(maxCircles);
    }
    public int[] getHoughImageValues() {
        return pixels;
    }
// What the fuck is this method?
	private void houghPixels() {
		double maxHough = -1D;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++)
			if (houghValues[x][y] > maxHough) maxHough = houghValues[x][y];
		}
		pixels = new int[width*height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int tempVal = (int)Math.round((houghValues[x][y]*(double)255) / maxHough);
				pixels[y*width + x] = tempVal << 16 | tempVal << 8 | tempVal | 0xff000000;
			}
		}
	}
    public Point nthMaxCenter(int n) {
        return centerPoint[n];
    }
// Hough transform method
    private synchronized void doHoughTransform() {
        int numCirclePoints = 0;
        int min = 1;
        int maxW = width - min;
        int maxH = height - min;
        houghValues = new double[width][height];
        int numPts = Math.round((float)8 * radius);
        int circle[][] = new int[2][numPts];
        for(int i = 0; i < numPts; i++) {
            double theta = (6.2831853071795862D*(double)i)/(double)numPts;
            int xx = (int)Math.round((double)radius*Math.cos(theta));
            int yy = (int)Math.round((double)radius*Math.sin(theta));
            if((numCirclePoints == 0) | (xx != circle[0][numCirclePoints]) & (yy != circle[1][numCirclePoints])) {
                circle[0][numCirclePoints] = xx;
                circle[1][numCirclePoints] = yy;
                numCirclePoints++;
            }
        }

        for(int y = min; y < maxH; y++) {
            for(int x = min; x < maxW; x++) {
                double tempValue = sobel(x, y);
                if(tempValue == (double)0)
                    continue;
                for(int i = 0; i < numCirclePoints; i++) {
                    int xCoord = x + circle[0][i];
                    int yCoord = y + circle[1][i];
                    if((xCoord >= 0) & (xCoord < width) & (yCoord >= 0) & (yCoord < height))
                        houghValues[xCoord][yCoord] += tempValue;
                }
            }
        }
        houghPixels();
    }
// Edge detection method
	private double sobel(int xPos, int yPos) {
		double sobelSum = 0.0D;
		int d[][] = new int[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++)
			d[x][y] = imageValues[(xPos + x) - 1][(yPos + y) - 1];
		}
		double sobelH = ((double)d[0][2] + (double)(2*d[1][2]) + (double)d[2][2]) - (double)d[0][0] - (double)(2*d[1][0]) - (double)d[2][0];
		double sobelV = ((double)d[2][0] + (double)(2 * d[2][1]) + (double)d[2][2]) - (double)d[0][0] - (double)(2 * d[0][1]) - (double)d[0][2];
		sobelSum = (Math.abs(sobelH) + Math.abs(sobelV)) / (double)2;
		return sobelSum;
	}
// Circle centre point detection method
    private synchronized void getCenterPoints(int num) {
        double maxPoint = -1D;
        double halfRadius = radius / (float)2;
        double halfRadiusSq = halfRadius * halfRadius;
        int xLoc = 0;
        int yLoc = 0;
        for(int i = 0; i < num; i++) {
            maxPoint = -1D;
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++)
                    if(houghValues[x][y] > maxPoint) {
                        maxPoint = houghValues[x][y];
                        xLoc = x;
                        yLoc = y;
                    }
            }
            centerPoint[i] = new Point(xLoc, yLoc);
            int yMin = (int)Math.floor((double)yLoc - halfRadius);
            if(yMin < 0)
                yMin = 0;
            int yMax = (int)Math.ceil((double)yLoc + halfRadius) + 1;
            if(yMax > height)
                yMax = height;
            int xMin = (int)Math.floor((double)xLoc - halfRadius);
            if(xMin < 0)
                xMin = 0;
            int xMax = (int)Math.ceil((double)xLoc + halfRadius) + 1;
            if(xMax > width)
                xMax = width;
            for(int y = yMin; y < yMax; y++) {
                for(int x = xMin; x < xMax; x++)
                    if(Math.pow(x - xLoc, 2D) + Math.pow(y - yLoc, 2D) < halfRadiusSq)
                        houghValues[x][y] = 0.0D;
            }
        }
    }
// Global variables
    BufferedImage bimg;
    public float radius;
    public int maxCircles;
    int imageValues[][];
    double houghValues[][];
    public int width;
    public int height;
    Point centerPoint[];
    int pixels[];
}
