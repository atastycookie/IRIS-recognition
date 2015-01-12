package org.cs02rm0.jirrm;

import java.awt.image.*;
import java.util.*;

import org.cs02rm0.jirrm.segmentation.*;
import org.cs02rm0.jirrm.normalisation.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class SWTFrame {
	Shell shell;
	Display display;
	public static void main(String[] args) {
		new SWTFrame();
	}
	public SWTFrame() {
		display = new Display ();
		shell = new Shell (display);
		shell.setText("Iris Recognition");

		RowLayout rl = new RowLayout();
		rl.type = SWT.VERTICAL;
		shell.setLayout(rl);
		Menu menuBar = new Menu(shell, SWT.BAR);
		
		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("E&xit");
		fileExitItem.addSelectionListener(new MenuItemListener());

		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Help");
		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);
		MenuItem helpAboutItem = new MenuItem(helpMenu, SWT.PUSH);
		helpAboutItem.setText("&About");
		helpAboutItem.addSelectionListener(new MenuItemListener());
		shell.setMenuBar(menuBar);

		Button button = new Button(shell, SWT.PUSH);
		button.setText("Select Image");
		button.addSelectionListener(new SelectImageListener());

		shell.setMinimumSize(335,375);
		shell.pack();

		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = (bounds.width - rect.width) / 2;
		int y = (bounds.height - rect.height) / 2;
		shell.setLocation(x,y);
		
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}

	class MenuItemListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			if (((MenuItem) event.widget).getText().equals("&About")) {
				MessageBox messageBox = new MessageBox(shell, SWT.OK);
				messageBox.setMessage("JIRRM Iris Recognition\n\nhttp://jirrm.sf.net");
				messageBox.open();
			} else if (((MenuItem) event.widget).getText().equals("E&xit")) {
					shell.close();
			}
		}
	}

	class SelectImageListener extends SelectionAdapter {
		public void widgetSelected(SelectionEvent event) {
			FileDialog dialog = new FileDialog(shell, SWT.OPEN);
			dialog.setText("Select image file");
			dialog.setFilterPath(System.getProperty("user.dir"));
			dialog.setFilterExtensions(new String[] {
				"*.jpg", "*.gif", "*.png", "*.*"
			});
			String path = dialog.open();
			if (path != null) {
				Image image = new Image(display, path);
				Label label = new Label(shell, SWT.NONE);
				label.setImage(image);
				BufferedImage bimg = Utils.convertToAWT(image.getImageData());
				Segment segment = new Segment();
				segment.segment(bimg);
				HashMap circles = (HashMap) segment.getCircles();
				Segment.Circle iris = (Segment.Circle) circles.get("iris");
				Segment.Circle pupil = (Segment.Circle) circles.get("pupil");
				BufferedImage map = new Normalise().rubberMap(bimg, iris.cp, iris.radius, pupil.cp, pupil.radius);
				ImageData id = Utils.convertToSWT(bimg);
				image = new Image(display, id);
				label.setImage(image);
				id = Utils.convertToSWT(map);
				if (image != null) image.dispose();
				image = new Image(display, id);
				Label l2 = new Label(shell, SWT.NONE);
				l2.setImage(image);
				shell.pack();
			}
		}
	}
}
