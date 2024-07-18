package com.weather.report.demo.dom;

import com.weather.report.demo.models.Metric;

public class SuccessResponse {

	private String message;
    private Metric metric;

    public SuccessResponse(String message, Metric metric) {
        this.message = message;
        this.metric = metric;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
    
}
