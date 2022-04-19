package com.musicmanager.validation;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorRespone {

	private HttpStatus status;
	private String message;
	private List<String> errors;

	public ErrorRespone() {
		// TODO Auto-generated constructor stub
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
