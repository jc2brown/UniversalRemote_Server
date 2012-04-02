package ca.jc2brown.arduino.remote.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ca.jc2brown.arduino.remote.model.Executable;



public class ExecutableService {
	
	public static final boolean DEBUG = true; 
	public static final boolean INFO = true;
	
	private static final String DAT_FILE = "exes.dat";
	
	private TimerService timerService;
	
	private Set<Executable> exeSet;
	private Map<Long, Executable> exeMap;
	
	public ExecutableService(TimerService timerService) {
		
		this.timerService = timerService;
		
		exeSet = new TreeSet<Executable>();
		exeMap = new HashMap<Long, Executable>();		
		/*
		add(new Executable(	0xa55a50afL, "VolUp", 	"Volume Up", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "changesysvolume 500" ));
		add(new Executable(	0xa55ad02fL, "VolDn", 	"Volume Down", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "changesysvolume -500" ));
		add(new Executable(	0xa55a48b7L, "Mute", 	"Volume Mute", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "mutesysvolume 2" ));
		add(new Executable(	0x9eb92L, "Up", 		"Up", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "movecursor 0 -10" ));
		add(new Executable(	0x5eb92L, "Down", 	"Down", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "movecursor 0 10" ));
		add(new Executable(	0xdeb92L, "Left", 	"Left", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "movecursor -10 0" ));
		add(new Executable(	0x3eb92L, "Right", 	"Right", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "movecursor 10 0" ));
		add(new Executable(	0xd0b92L, "Click", 	"Click", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "sendmouse left click" ));
		add(new Executable(	0x4cb92L, "Play", 	"Play", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "sendkey 0xb3 press" ));
		add(new Executable(	0x70b92L, "Exit", 	"Quit", 	"C:/Users/chris/Desktop/Useful/nircmd/nircmd.exe", "sendkeypress alt+F4" ));
		*/
	}
	 
	public Set<Executable> getExeSet() {
		return exeSet;
	}
	
	
	public void store() {
		List<Executable> exeList = new ArrayList<Executable>(exeSet);
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try	{
			fos = new FileOutputStream(DAT_FILE);
			out = new ObjectOutputStream(fos);
			out.writeObject(exeList);
			out.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void add(Executable exe) {
		exeSet.remove(exe);
		exeSet.add(exe);
		exeMap.put(exe.getCode(), exe);
		timerService.add(exe);
	}
	
	public void save(Executable exe) {
		debug("Saving exe: " + exe);
		add(exe);
		store();
	}
	
	@SuppressWarnings("unchecked")
	public void load() {
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(DAT_FILE);
			in = new ObjectInputStream(fis);
			List<Executable> exeList = (ArrayList<Executable>)in.readObject();
			for ( Executable exe : exeList ) {
				add(exe);
			}
			in.close();
		} catch(FileNotFoundException ex)	{
			info(DAT_FILE + " not found");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


	public Executable getExe(Long code) {
		return exeMap.get(code);
	}
	
	public void deleteExe(Long code) {
		Executable exe = exeMap.remove(code);
		exeSet.remove(exe);
		store();
	}
	
	
	public static void debug(Object o) {
		if ( DEBUG ) {
			System.out.println("ExeService:\t" + o);
		}
	}
	public static void info(Object o) {
		if ( INFO ) {
			System.out.println("ExeService:\t" + o);
		}
	}
}