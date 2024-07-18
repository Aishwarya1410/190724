package com.weather.report.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.weather.report.demo.dom.ErrorResponse;
import com.weather.report.demo.dom.MetricIdResponse;
import com.weather.report.demo.dom.MetricOperationResponse;
import com.weather.report.demo.dom.MetricResponse;
import com.weather.report.demo.dom.SuccessResponse;
import com.weather.report.demo.exception.WeatherValidationException;
import com.weather.report.demo.models.Metric;
import com.weather.report.demo.service.WeatherService;
import com.weather.report.demo.util.WeatherValidationUtils;

@RestController
@RequestMapping("/api/v1/weather/metrics")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	/**
	 * @param operations
	 * @param sensorIds
	 * @param metrics
	 * @param date
	 * @param month
	 * @param startDate
	 * @param endDate
	 * @return List of Metric Response
	 */
	@GetMapping
	public List<MetricIdResponse> getMetrics(@RequestParam(required = false) List<String> operations,
			@RequestParam List<Integer> sensorIds, @RequestParam List<String> metrics,
			@RequestParam(value = "date", required = false) LocalDate date,
			@RequestParam(required = false) String month,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		if (operations == null || operations.isEmpty()) {
			operations = List.of("avg");
		}

		List<MetricIdResponse> results = new ArrayList<>();
		for (int sensorId : sensorIds) {
			List<MetricResponse> metricResponse = new ArrayList<>();
			for (String metric : metrics) {
				if (!WeatherValidationUtils.isValidMetricName(metric.toLowerCase())) {
					throw new WeatherValidationException("Invalid metric name: " + metric);
				}
				List<MetricOperationResponse> operationResponse = new ArrayList<>();
				for (String operation : operations) {
					if (!WeatherValidationUtils.isValidOperationName(operation.toLowerCase())) {
						throw new WeatherValidationException("Invalid operation: " + operation);
					}
					Double result = weatherService.getMetricData(operation.toLowerCase(), sensorId,
							metric.toLowerCase(), date, month, startDate, endDate);
					operationResponse.add(new MetricOperationResponse(operation.toLowerCase(), result));
				}
				metricResponse.add(new MetricResponse(metric.toLowerCase(), operationResponse));
			}

			results.add(new MetricIdResponse(sensorId, metricResponse));
		}

		return results;

	}

	/**
	 * @param metric
	 * @return
	 */
	@PostMapping
	public SuccessResponse createMetrics(@RequestBody Metric metric) {
		if (!WeatherValidationUtils.isValidMetricName(metric.getMetricName())) {
			throw new WeatherValidationException("Invalid metric name: " + metric.getMetricName());
		}
		Metric savedMetric = weatherService.saveMetricData(metric);
		return new SuccessResponse("Metric saved successfully", savedMetric);

	}

	// Exception Handler for Handling any Invalid request
	@ExceptionHandler(WeatherValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleWeatherValidationException(WeatherValidationException ex) {
		return new ErrorResponse(ex.getMessage());
	}

}
