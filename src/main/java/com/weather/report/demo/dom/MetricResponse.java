package com.weather.report.demo.dom;

import java.util.List;

public class MetricResponse {

	private String metricName;
	private List<MetricOperationResponse> operations;

	public MetricResponse(String metricName, List<MetricOperationResponse> operations) {
		super();
		this.metricName = metricName;
		this.operations = operations;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public List<MetricOperationResponse> getOperations() {
		return operations;
	}

	public void setOperations(List<MetricOperationResponse> operations) {
		this.operations = operations;
	}

}
