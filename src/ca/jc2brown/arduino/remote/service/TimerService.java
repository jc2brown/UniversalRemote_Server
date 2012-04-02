package ca.jc2brown.arduino.remote.service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import ca.jc2brown.arduino.remote.model.Executable;

public class TimerService {
	
	public static final boolean DEBUG = true; 
	public static final boolean INFO = true;
	
	private SerialService serialService;
	
	private long lastCheck;
	
	private List<Entry<Integer, Long>> schedule = new ArrayList<Entry<Integer, Long>>();
	
	
	public TimerService(SerialService serialService) {
		this.serialService = serialService;
		lastCheck = 0L;
	}
	 
	public void check() {
		Calendar now = Calendar.getInstance();
		int minutes = getMinutes(now);
		
		if ( lastCheck >= minutes ) {
			return;			
		}
		for ( Entry<Integer, Long> entry : schedule ) {
			if ( entry.getKey() == minutes ) {
				serialService.simulate(entry.getValue());
			}
		}
		lastCheck = minutes;
	}
	
	public static void debug(Object o) {
		if ( DEBUG ) {
			System.out.println("TimerService:\t" + o);
		}
	}
	public static void info(Object o) {
		if ( INFO ) {
			System.out.println("TimerService:\t" + o);
		}
	}
	
	private int getMinutes(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE) ;	
	}
	
	private int getMinutes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getMinutes(calendar);	
	}

	public void add(Executable exe) {
		Long code = exe.getCode();
		Entry<Integer, Long> entry = null;
		for ( Iterator<Entry<Integer, Long>> i = schedule.iterator(); i.hasNext(); entry = i.next() ) {
			if ( entry != null && entry.getKey().equals(code) ) {
				i.remove();
			}
		}
		Entry<Integer, Long> onEntry  = new AbstractMap.SimpleEntry<Integer, Long>( getMinutes(exe.getTr1()), code ); 
		Entry<Integer, Long> offEntry = new AbstractMap.SimpleEntry<Integer, Long>( getMinutes(exe.getTr2()), code ); 
		schedule.add( onEntry );	
		schedule.add( offEntry );		
	}
}