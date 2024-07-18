package com.weather.report.demo.exception;

public class WeatherValidationException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public WeatherValidationException(String message) {
        super(message);
    }
    
    public WeatherValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

