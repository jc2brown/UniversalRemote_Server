package ca.jc2brown.arduino.remote.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;
import java.util.LinkedList;

import org.eclipse.swt.widgets.Display;

public class SerialService implements SerialPortEventListener {
	
	private static final int TIME_OUT = 2000;
	
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};

	public static final int SIZEOF_CODE = 4;
	
	public static final long REPEAT_RATE = 250L;
	
	Display display;
	SerialPort serialPort;
	private InputStream input;
	private OutputStream output; 
	private int dataRate;
	private LinkedList<Long> codeq;
	
	
	public SerialService(int dataRate, Display display) {
		this.dataRate = dataRate;		
		this.display = display;
		codeq = new LinkedList<Long>();
	}
	
	
	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}
		
		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(dataRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,	SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();
				byte chunk[] = new byte[available];
				input.read(chunk, 0, available);
				while ( available >= SIZEOF_CODE ) {
					codeq.add(readLong(chunk));
					available -= SIZEOF_CODE;
				}	
				display.wake();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

	public Long readLong() {
		return codeq.remove();
	}
	
	private Long readLong(byte[] b) {
		long l = 0L;
		for ( int i = 0; i < SIZEOF_CODE; i += 1 ) {
			l <<= 8;
			l |= b[i] & 0x00FF;
		}
		return l;
	}
	
	public void write(byte b) throws IOException {
		output.write(b);
	}

	public boolean isCodeAvailable() {
		return ! codeq.isEmpty();
	}
	
	public void simulate(Long code) {
		codeq.addFirst(code);
	}
	
	
	/*
	public static void main(String[] args) throws Exception {
		SerialService main = new SerialService(115200);
		main.initialize();
		System.out.println("Started");
	}*/
}