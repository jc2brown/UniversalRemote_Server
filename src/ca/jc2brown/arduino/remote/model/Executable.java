package ca.jc2brown.arduino.remote.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import ca.jc2brown.arduino.remote.util.StreamGobbler;
import ca.jc2brown.arduino.remote.util.Util;

public class Executable implements Serializable, Comparable<Executable> {
	private static final long serialVersionUID = 1L;
	
	
	public final static int iCode = 0;
	public final static int iName = 1;
	public final static int iKey = 2;
	public final static int iCmd = 3;
	public final static int iArgs = 4;
	
	

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
	
	
	private String name;
	private String key;
	private Long code;
	private String cmd;
	private String args;

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
	
	public Executable(Long code, String key, String name, String cmd, String args) {
		this.code = code;
		this.key = key;
		this.name = name;
		this.cmd = cmd;
		this.args = args;
	}
	
	public String[] toStringArray() {
		String[] a = new String[5];
		a[iName] = name;
		a[iKey] = key;
		a[iCode] = Util.toHex(code);
		a[iCmd] = cmd;
		a[iArgs] = args;
		return a;
	}
	
	public String toString() {
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
		sb.append( " } " );
		return sb.toString();
	}
	
	public Executable clone() {
		return new Executable(code, key, name, cmd, args);
	}

	public int compareTo(Executable e) {
		return code.compareTo(e.code);
	}
	

}