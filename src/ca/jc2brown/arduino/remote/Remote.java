package ca.jc2brown.arduino.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import ca.jc2brown.arduino.remote.controller.OSDController;
import ca.jc2brown.arduino.remote.controller.SerialController;
import ca.jc2brown.arduino.remote.controller.ViewController;
import ca.jc2brown.arduino.remote.model.Executable;
import ca.jc2brown.arduino.remote.service.ExecutableService;
import ca.jc2brown.arduino.remote.service.SerialService;
import ca.jc2brown.arduino.remote.util.StreamGobbler;
import ca.jc2brown.arduino.remote.util.Util;

public class Remote {
	
	public static final boolean DEBUG = true; 
	public static final boolean INFO = true;
	
	private SerialService serialService;
	private ExecutableService exeService;
		
	private ViewController viewController;
	private OSDController osdController;
	private SerialController serialController;
	
	public Display display;
	private Executable lastExe;
	
	
	
	public static void main(String[] args) {
		Remote remote = new Remote();
		remote.setup();
		remote.run();
	}
	
	
	public void setup() {
		debug("Setup start");
		
		display = Display.getDefault();
		
		exeService = new ExecutableService();
		
		serialService = new SerialService(115200, display);

		serialController = new SerialController(serialService);
		viewController = new ViewController(serialService, exeService, display);
		osdController = new OSDController(display);
		
		exeService.load();
		serialService.initialize();	
		
		viewController.updateView();
		
		lastExe = null;
		
		new Thread() {
	        public void run() {
	            while( ! display.isDisposed() ) {
	                try {
	                    Thread.sleep(500);
	                } catch (Throwable th) {
	                }
		            display.wake();
	            }
	        }
	    }.start();
	}
	

	public void run() {
		while( ! viewController.isShellDisposed() ) {
			loop();
			if ( ! display.readAndDispatch() ) {
				display.sleep();
			}
		}
		viewController.destroy();
		osdController.destroy();
		System.exit(0);
	}
	
	
	public boolean loop() {
		osdController.handleOSD();
		// Nothing to do?
		if ( ! serialController.isCodeAvailable() ) {
			return true;
		}
		// Get input
		Long code = serialController.getCode();
		if ( viewController.isEdit() ) {
			viewController.setCode(code);
			return true;
		}
		Executable exe = null;
		if ( code.equals(0xffffffffL) ) {
			exe = lastExe;
		} else {
			exe = exeService.getExe(code);
			viewController.setActiveCode(code);
		}
		if ( exe == null ) {
			debug("Exe not found for code " + Util.toHex(code));
			return false;
		}
		debug("Executing " + exe.getCmd() + " " + exe.getArgs());
		execute(exe);
		lastExe = exe;
		osdController.showOSD(lastExe);
		return false;
	}
	
	
	public void execute(Executable exe) {
		if ( exe.getCmd().startsWith("hardware") ) {
			String args = exe.getArgs();
			Byte b = (byte) (Byte.parseByte(args) - 1);
			try {
				serialService.write(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		String[] argArray = exe.getArgs().split(" ");
		List<String> argList = new ArrayList<String>();
		argList.add(exe.getCmd());
		argList.addAll(Arrays.asList(argArray));
		ProcessBuilder pb = new ProcessBuilder(argList);
		try {
			Process p = pb.start();
			new Thread(new StreamGobbler(p.getInputStream())).start();
			p.waitFor();
			
		} catch (IOException e) {
			System.err.println("Execution error");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public static void debug(Object o) {
		if ( DEBUG ) {
			System.out.println("Main:\t\t" + o);
		}
	}
	public static void info(Object o) {
		if ( INFO ) {
			System.out.println("Main:\t\t" + o);
		}
	}
}