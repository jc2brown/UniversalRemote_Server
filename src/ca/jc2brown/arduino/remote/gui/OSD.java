package ca.jc2brown.arduino.remote.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import ca.jc2brown.arduino.remote.controller.OSDController;

public class OSD extends Shell {

	private OSDController osdController;
	
	private Label lText;
	
	public OSD(OSDController osdController, Display display) {
		super(display, SWT.NO_TRIM | SWT.ON_TOP);
		this.osdController = osdController;
		
		Font font = new Font(display, "Consolas", 36, SWT.BOLD);
		lText = new Label(this, SWT.CENTER);
		lText.setSize(500, 60);
		lText.setFont(font);
		
		pack();
		open();
		Rectangle bounds = display.getBounds();
		setLocation(0,0);
		System.out.println(bounds.x + bounds.width - 500);
		setVisible(false);
	}

	
	
	// Don't touch this
	protected void checkSubclass() {}



	public void setContent(String text) {
		lText.setText(text);
		
	}

}
