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
import ca.jc2brown.arduino.remote.service.TimerService;
import ca.jc2brown.arduino.remote.util.StreamGobbler;
import ca.jc2brown.arduino.remote.util.Util;

public class Remote {
	
	public static final boolean DEBUG = true; 
	public static final boolean INFO = true;
	
	private SerialService serialService;
	private ExecutableService exeService;
	private TimerService timerService;
		
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

		Display.setAppName("Remote Control");
		display = Display.getDefault();
		
		serialService = new SerialService(115200, display);		
		timerService = new TimerService(serialService);
		exeService = new ExecutableService(timerService);

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
		timerService.check();
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
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lastExe = exe;
		osdController.showOSD(lastExe);
		return false;
	}
	
	
	public void execute(Executable exe) {
		String[] argArray = exe.getArgs().split(" ");
		if ( exe.getCmd().startsWith("hardware") ) {
			int addr = Integer.parseInt(argArray[0]) - 1;
			int ctrl = 2;
			
			if ( argArray[1].contains("high") ) {
				ctrl = 1;
			} else if ( argArray[1].contains("low") ) {
				ctrl = 0;
			} else if ( argArray[1].contains("toggle") ) {
				ctrl = 3;
			}

			byte b = (byte)((ctrl << 6) | (addr & 0x3F));
			try {
				serialService.write( b );
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		
		List<String> argList = parseArgString( exe.getArgs() );
		argList.add(0, exe.getCmd() );
		ProcessBuilder pb = new ProcessBuilder(argList);
		try {
			Process p = pb.start();
			new Thread(new StreamGobbler(p.getInputStream())).start();
			//p.waitFor();
			
		} catch (IOException e) {
			System.err.println("Execution error");
			e.printStackTrace();
		/*} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
		}
	}
	
	
	public List<String> parseArgString(String str) {
		List<String> list = new ArrayList<String>();
		String spacejunk = "KSPSAPCAECJEUJNUKN";
		String q = "\"";
		String space = "\\s";
		StringBuilder sb = new StringBuilder();
		String[] quotes = str.split(q);
		for ( int i = 0; i < quotes.length; i++ ) {
			if ( str.contains(q+quotes[i]+q) ) {
				quotes[i] = quotes[i].replaceAll(space, spacejunk);
			}
			sb.append(quotes[i]);
		}
		String[] args = sb.toString().split(space);
		for ( int i = 0; i < args.length; i++ ) {
			args[i] = args[i].replaceAll(spacejunk, " ");
			list.add(args[i]);
		}
		return list;
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