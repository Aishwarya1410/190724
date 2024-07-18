package com.weather.report.demo.dom;

import java.util.List;

public class MetricIdResponse {
	
	private int sensorId;
	private List<MetricResponse> metrics;
	
	public MetricIdResponse(int sensorId, List<MetricResponse> metrics) {
		super();
		this.sensorId = sensorId;
		this.metrics = metrics;
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public List<MetricResponse> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<MetricResponse> metrics) {
		this.metrics = metrics;
	}
	
	
	
	

}
