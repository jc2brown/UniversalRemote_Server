package ca.jc2brown.arduino.remote.controller;

import ca.jc2brown.arduino.remote.service.SerialService;


public class SerialController {
	
	SerialService serialService;
	
	public SerialController(SerialService serialService) {
		this.serialService = serialService;
	}
	
	
	public Long getCode() {
		if ( isCodeAvailable() ) {
			Long code = serialService.readLong();
			return code;
		}
		return null; 
	}
	
	public boolean isCodeAvailable() {
		return serialService.isCodeAvailable();
	}
		
}