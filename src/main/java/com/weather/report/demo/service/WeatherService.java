package com.weather.report.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weather.report.demo.models.Metric;
import com.weather.report.demo.repository.WeatherRepository;

@Service
public class WeatherService {
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
	
	@Autowired
	private WeatherRepository weatherRepository;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * @param operation
	 * @param sensorId
	 * @param metricName
	 * @param date
	 * @param month
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Double getMetricData(String operation,int sensorId, String metricName, LocalDate date, String month, LocalDate startDate, LocalDate endDate) {
		logger.info("Fetching metrics for {} between {} and {}", metricName, startDate, endDate);
		LocalDateTime startDateTime;
		LocalDateTime endDateTime;
		/**
		 * Condition if date range is given
		 */
		if(startDate != null && endDate != null) {
			startDateTime = startDate.atStartOfDay();
			endDateTime = endDate.atTime(LocalTime.MAX);
		} 
		/**
		 * Condition if only single date is given
		 */
		else if(date != null) {
			startDateTime = date.atStartOfDay();
			endDateTime = date.atTime(LocalTime.MAX);
		}		
		/**
		 * Condition if month is given
		 */
		else if(month != null) {
			startDateTime = YearMonth.parse(month).atDay(1).atStartOfDay();
			endDateTime = YearMonth.parse(month).atEndOfMonth().atTime(LocalTime.MAX);
		}
		/**
		 * Condition if no dates or month are given but just operation is given so considering latest date
		 */
		else if(operation != null) { 
			startDateTime = LocalDate.now().atStartOfDay();
			endDateTime = LocalDate.now().atTime(LocalTime.MAX);
		}
		/**
		 * Condition nothing is given so just return the average of metric
		 */
		else {
            LocalDateTime todayStart = LocalDate.now().atStartOfDay();
            LocalDateTime todayEnd = LocalDate.now().atTime(LocalTime.MAX);
			List<Metric> latestMetric = weatherRepository.findLatestMetric(metricName, sensorId,todayStart,todayEnd);
			if(latestMetric == null) {
				return null;
			}
			return latestMetric.stream().mapToDouble(Metric::getMetricValue).average().orElse(0);
		}
		
		switch(operation) {
		case "sum":
			return weatherRepository.findByMetricNameIdAndTimestamp(metricName, startDateTime,endDateTime, sensorId).stream().mapToDouble(Metric::getMetricValue).sum();
		case "avg":
			return weatherRepository.findAvgByMetricNameIdAndTimestamp(metricName,startDateTime, endDateTime, sensorId);
		case "min":
			return weatherRepository.findMinByMetricNameIdAndTimestamp(metricName,startDateTime,endDateTime,sensorId);
		case "max":
			return weatherRepository.findMaxByMetricNameIdAndTimestamp(metricName,startDateTime, endDateTime , sensorId);
		default:
			throw new IllegalArgumentException("Invalid operation:" + operation);

		}
	}
	
	/**
	 * @param metric
	 * @return
	 */
	public Metric saveMetricData(Metric metric) {
		logger.info("saving metrics in lowercase");
		metric.setMetricName(metric.getMetricName().toLowerCase());
		return weatherRepository.save(metric);
	}

}
