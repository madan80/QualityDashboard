package com.tui.sonar.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Job Name Not Found")
public class JobNameNotFoundException extends Exception {
	
	public JobNameNotFoundException(String message, final Throwable exceptionThrown){
		super(message, exceptionThrown);
	}

}
