package ca.jc2brown.arduino.remote.controller;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import ca.jc2brown.arduino.remote.gui.OSD;
import ca.jc2brown.arduino.remote.model.Executable;

public class OSDController {
	
	private final static long OSD_TIME = 1000L; 
	
	Display display;
	private OSD osd;
	
	private long showTime;
	
	public OSDController(Display display) {
		this.display = display;
		osd = new OSD(this, display);
		showTime = 0L;
	}
	
	public void handleOSD() {
		if ( System.currentTimeMillis() > showTime + OSD_TIME ) {
			display.wake();
			hideOSD();
		}
	}
	
	public void showOSD(Executable exe) {
		osd.setContent(exe.getName());
		osd.setBackgroundMode(SWT.FILL);
		osd.setBackground(new Color(display, 0, 255, 255));
		osd.open();
		osd.pack();
		osd.setVisible(true);
		showTime = System.currentTimeMillis();
	}
	
	private void hideOSD() {
		if ( ! osd.isDisposed() ) {
			osd.setVisible(false);
		}
	}

	public void destroy() {
		osd.dispose();		
	}
	
	
}