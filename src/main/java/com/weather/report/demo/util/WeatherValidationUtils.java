package com.weather.report.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeatherValidationUtils {

    public static boolean isValidMetricName(String metricName) {
    	List<String> metrics = new ArrayList<>(Arrays.asList("humidity", "temperature", "wind"));
        return metrics.contains(metricName);
    }
    
    public static boolean isValidOperationName(String metricName) {
    	List<String> metrics = new ArrayList<>(Arrays.asList("min", "max", "sum","avg"));
        return metrics.contains(metricName);
    }
}

