package ca.jc2brown.arduino.remote.util;



public class Util {

	
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	
	public static String toHex(Long value) {
		return "0x" + Long.toHexString(value);
	}
	
	public static Long toHex(String valueStr) throws NumberFormatException {
		valueStr = valueStr.replaceFirst("0x", "");
		return Long.parseLong( valueStr, 16 );
	}
	

}