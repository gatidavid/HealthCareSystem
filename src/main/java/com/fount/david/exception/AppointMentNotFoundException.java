package com.fount.david.exception;

public class AppointMentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1484394134017769416L;

	public AppointMentNotFoundException() {
		super();
	}
	
	public AppointMentNotFoundException(String message) {
		super(message);
	}
 
}
