package ca.jc2brown.arduino.remote.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;


import ca.jc2brown.arduino.remote.util.Util;

public class Executable implements Serializable, Comparable<Executable> {
	private static final long serialVersionUID = 1L;
	
	
	public final static int	iCode 		= 0;
	public final static int	iName 		= 1;
	public final static int	iKey 		= 2;
	public final static int	iCmd 		= 3;
	public final static int	iArgs 		= 4;
	public final static int iAuto 		= 5;
	public final static int	iTr1 	= 6;
	public final static int	iTr2    = 7;
	public final static int	iNUM        = 8;
	
	
	private String name;
	private String key;
	private Long code;
	private String cmd;
	private String args;
	private Boolean auto;
	private Date onTime;
	private Date offTime;
	

	public String getName() {
		return name;
	}
	public String getKey() {
		return key;
	}
	public Long getCode() {
		return code;
	}
	public String getCmd() {
		return cmd;
	}
	public String getArgs() {
		return args;
	}
	public Boolean getAuto() {
		return auto;
	}
	public Date getTr1() {
		return onTime;
	}
	public Date getTr2() {
		return offTime;
	}
	
	
	public Executable(Long code, String key, String name, String cmd, String args, Boolean auto, Date onTime, Date offTime) {
		this.code = code;
		this.key = key;
		this.name = name;
		this.cmd = cmd;
		this.args = args;
		this.auto = auto;
		this.onTime = onTime;
		this.offTime = offTime;
	}
	
	
	
	private String basename(String path) {
		return path.replaceAll(".*[/|\\\\]", "").replaceAll("\"", "");
	}
	
	
	public String[] toStringArray() {
		Calendar cTr1 = Calendar.getInstance();
		cTr1.setTime(onTime);
		
		Calendar cTr2 = Calendar.getInstance();
		cTr2.setTime(offTime);
		
		String[] a = new String[iNUM];
		a[iName] = name;
		a[iKey] = key;
		a[iCode] = Util.toHex(code);
		a[iCmd] = basename(cmd);
		a[iArgs] = args;
		if ( auto == null ) {
			auto = false;
			onTime = new Date();
			offTime = new Date();
		}
		a[iAuto] = auto.toString();
		if ( auto ) {
			a[iTr1]  = cTr1.get(Calendar.HOUR_OF_DAY)  + ":" + cTr1.get(Calendar.MINUTE);
			a[iTr2] = cTr2.get(Calendar.HOUR_OF_DAY) + ":" + cTr2.get(Calendar.MINUTE);
		} else {
			a[iTr1]  = "";
			a[iTr2] = "";
		}
		return a;
	}
	
	public String toString() {
		Calendar cTr1 = Calendar.getInstance();
		cTr1.setTime(onTime);
		
		Calendar cTr2 = Calendar.getInstance();
		cTr2.setTime(offTime);
		
		StringBuilder sb = new StringBuilder();
		sb.append( " { " );
		sb.append( Util.toHex(code) );
		sb.append( " | " );
		sb.append( key );
		sb.append( " | " );
		sb.append( name);
		sb.append( " | " );
		sb.append( cmd );
		sb.append( " | " );
		sb.append( args );
		sb.append( " | " );
		sb.append( auto.toString() );
		if ( auto ) {
			sb.append( " | " );
			sb.append( cTr1.get(Calendar.HOUR_OF_DAY) );
			sb.append( ":" );
			sb.append( cTr1.get(Calendar.MINUTE) );
			sb.append( " | " );
			sb.append( cTr2.get(Calendar.HOUR_OF_DAY) );
			sb.append( ":" );
			sb.append( cTr2.get(Calendar.MINUTE) );
		}
		sb.append( " } " );
		return sb.toString();
	}
	
	public Executable clone() {
		return new Executable(code, key, name, cmd, args, auto, onTime, offTime);
	}

	
	public int compareTo(Executable e) {
		return code.compareTo(e.code);
	}
	
	public final static Comparator<Executable> CMP_NAME = new Comparator<Executable>() {
		public int compare(Executable e1, Executable e2) {
			return e1.name.compareTo(e2.name);
		}
	};
	
	public final static Comparator<Executable> CMP_KEY = new Comparator<Executable>() {
		public int compare(Executable e1, Executable e2) {
			return e1.key.compareTo(e2.key);
		}
	};
	
	public final static Comparator<Executable> CMP_CODE = new Comparator<Executable>() {
		public int compare(Executable e1, Executable e2) {
			return e1.code.compareTo(e2.code);
		}
	};
	
	public final static Comparator<Executable> CMP_CMD = new Comparator<Executable>() {
		public int compare(Executable e1, Executable e2) {
			return e1.cmd.compareTo(e2.cmd);
		}
	};
	
	public final static Comparator<Executable> CMP_ARGS = new Comparator<Executable>() {
		public int compare(Executable e1, Executable e2) {
			return e1.args.compareTo(e2.args);
		}
	};
	
	public final static Comparator<Executable> CMP_TR1 = new Comparator<Executable>() {
		public int compare(Executable e1, Executable e2) {
			int autoCmp = e1.auto.compareTo(e2.auto);
			if ( autoCmp != 0 ) {
				return autoCmp;
			}
			return e2.onTime.compareTo(e1.onTime);
		}
	};
	
	public final static Comparator<Executable> CMP_TR2 = new Comparator<Executable>() {
		public int compare(Executable e1, Executable e2) {
			int autoCmp = e1.auto.compareTo(e2.auto);
			if ( autoCmp != 0 ) {
				return autoCmp;
			}
			return e2.offTime.compareTo(e1.offTime);
		}
	};
	
	

}