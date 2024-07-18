package com.weather.report.demo.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "metric")
public class Metric {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sensorId", nullable = false)
	private int sensorId;

	@Column(name = "metricName", nullable = false, length = 100)
	private String metricName;

	@Column(name = "metricValue", nullable = false)
	private Double metricValue;

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public Double getMetricValue() {
		return metricValue;
	}

	public void setMetricValue(Double metricValue) {
		this.metricValue = metricValue;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Metric(Long id, int sensorId, String metricName, Double metricValue, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.sensorId = sensorId;
		this.metricName = metricName;
		this.metricValue = metricValue;
		this.timestamp = timestamp;
	}
	
	public Metric() {
		
	}



}
