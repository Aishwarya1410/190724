package com.weather.report.demo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.weather.report.demo.models.Metric;
import com.weather.report.demo.repository.WeatherRepository;

public class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMetricsWithDate() {
        LocalDate date = LocalDate.of(2024, 7, 18);
        int sensorId = 1;
        String metricName = "humidity";

        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime month = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(LocalTime.MAX);
        Double min=50.0;
        Metric metric1 = new Metric(1L, sensorId,"humidity", 50.0, startDateTime);
        Metric metric2 = new Metric(2L, sensorId,"humidity", 60.0, endDateTime);

        when(weatherRepository.findMinByMetricNameIdAndTimestamp(eq(metricName), eq(startDateTime), eq(endDateTime), eq(sensorId)))
            .thenReturn(min);
        Double minr = weatherService.getMetricData("min",sensorId, metricName,  date, null, null, null);

        assertEquals(min, minr);
    }

    @Test
    void testGetMetricsWithoutDate() {
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        int sensorId = 1;
        String metricName = "temperature";

        Metric metric1 = new Metric(1L, sensorId,"temperature", 22.0, todayStart.plusHours(1) );
        Metric metric2 = new Metric(2L, sensorId,"temperature", 24.0, todayEnd.minusHours(1));
        List<Metric> expectedMetrics = Arrays.asList(metric1, metric2);

        when(weatherRepository.findLatestMetric(eq(metricName), eq(sensorId), eq(todayStart), eq(todayEnd)))
            .thenReturn(expectedMetrics);

        Double avg = weatherService.getMetricData(null,sensorId, metricName,  null, null, null, null);

        assertEquals(23.0, avg);
    }
    
    @Test
    void testGetMetricsWithDateSum() {
        LocalDate date = LocalDate.of(2024, 7, 18);
        int sensorId = 1;
        String metricName = "humidity";

        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime month = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(LocalTime.MAX);
        Double sum=110.0;
        Metric metric1 = new Metric(1L, sensorId,"humidity", 50.0, startDateTime);
        Metric metric2 = new Metric(2L, sensorId,"humidity", 60.0, endDateTime);

        when(weatherRepository.findMinByMetricNameIdAndTimestamp(eq(metricName), eq(startDateTime), eq(endDateTime), eq(sensorId)))
            .thenReturn(sum);
        Double response = weatherService.getMetricData("min",sensorId, metricName,  date, null, null, null);

        assertEquals(sum, response);
    }
}

