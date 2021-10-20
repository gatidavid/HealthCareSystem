package com.fount.david.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {

	public String generatePassword() {
		
		return UUID.randomUUID()
				.toString()
				.replace("-", "")
				.substring(0, 8);
		
	}
}
