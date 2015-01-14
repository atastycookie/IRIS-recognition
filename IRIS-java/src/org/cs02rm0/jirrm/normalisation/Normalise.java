package org.cs02rm0.jirrm.normalisation;
import org.cs02rm0.jirrm.*;
import java.awt.image.*;
import java.awt.*;

public class Normalise {
	public BufferedImage rubberMap(BufferedImage bimg,
			Point iris, int iRadius, Point p, int pRadius) {
		BufferedImage map = new BufferedImage(KEY_WIDTH, KEY_HEIGHT, BufferedImage.TYPE_INT_RGB);
		int iris_width = iRadius-pRadius;
		for (double i=0; i<KEY_WIDTH; i++) { // every 360/256 degrees
			final double radians = Math.toRadians(i*360/KEY_WIDTH+1);
			for (double j=0; j<KEY_HEIGHT; j++) { // every 1/32th out
				double r = pRadius +(iris_width)*(j+1)/KEY_HEIGHT;
				int x = (int) (p.getX() +r*Math.sin(radians));
				int y = (int) (p.getY() +r*Math.cos(radians));
				try {
					map.setRGB((int)i,(int)j,bimg.getRGB(x,y));
				} catch (Exception e) { e.printStackTrace(); }
			}
		}
		Utils.drawCircle(bimg, p, pRadius, Color.GREEN);
		Utils.drawCircle(bimg, iris, iRadius, Color.GREEN);
		return map;
	}
	private static final int KEY_WIDTH = 320;
	private static final int KEY_HEIGHT = 64;
}
