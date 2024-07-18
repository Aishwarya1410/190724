package com.weather.report.demo.dom;

public class MetricOperationResponse {
	
	private String operation;
	private Double value;
	
	public MetricOperationResponse(String operation, Double value) {
		super();
		this.operation = operation;
		this.value = value;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	
	

}
