package com.weather.report.demo.integration;


import com.weather.report.demo.models.Metric;
import com.weather.report.demo.repository.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") 
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherRepository weatherRepository;

    @BeforeEach
    public void setup() {
        weatherRepository.deleteAll();

        LocalDate date = LocalDate.of(2024, 7, 18);
        LocalDateTime dateTime1 = date.atStartOfDay();
        LocalDateTime dateTime2 = date.atTime(LocalTime.MAX).minusHours(1);
        
        LocalDate dateNew = LocalDate.of(2024, 7, 19);
        LocalDateTime dateTime3 = dateNew.atStartOfDay();
        LocalDateTime dateTime4 = dateNew.atTime(LocalTime.MAX).minusHours(2);

        Metric metric1 = new Metric(1L, 1,"humidity", 50.0, dateTime1);
        Metric metric2 = new Metric(2L, 1,"humidity", 60.0, dateTime2);
        Metric metric3 = new Metric(3L, 2,"temperature", 22.0, LocalDateTime.now().minusHours(1));
        Metric metric4 = new Metric(4L, 1,"temperature", 24.0, LocalDateTime.now());
        Metric metric5 = new Metric(5L, 3,"humidity", 22.0, dateTime1);
        Metric metric6 = new Metric(6L, 3,"humidity", 22.0, dateTime3);
        Metric metric7 = new Metric(7L, 3,"humidity", 24.0, dateTime4);

        weatherRepository.saveAll(Arrays.asList(metric1, metric2, metric3, metric4,metric5,metric6,metric7));
    }

    @Test
    public void testGetMetricsWithDateForMin() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/weather/metrics")
                .param("metrics", "humidity")
                .param("date", "2024-07-18")
                .param("sensorIds", "1")
                .param("operations", "min")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("50.0")))
            .andReturn();
    }
    
    @Test
    public void testGetMetricsWithDateForMax() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/weather/metrics")
                .param("metrics", "humidity")
                .param("date", "2024-07-18")
                .param("sensorIds", "1")
                .param("operations", "max")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("60.0")))
            .andReturn();
    }

    @Test
    public void testGetMetricsWithDateForMinAndMax() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/weather/metrics")
                .param("metrics", "humidity")
                .param("date", "2024-07-18")
                .param("sensorIds", "1")
                .param("operations", "min,max")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("50.0")))
            .andExpect(content().string(containsString("60.0")))
            .andReturn();
    }

    @Test
    public void testGetMetricsWithStartDateAndEndDateGiveAvg() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/weather/metrics")
                .param("metrics", "humidity")
                .param("sensorIds", "3")
                .param("startDate", "2024-07-18")
                .param("endDate", "2024-07-19")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("22.66")))
            .andReturn();
    }
    
    @Test
    public void testGetMetricsWithWrongMetricName() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/weather/metrics")
                .param("metrics", "testing")
                .param("sensorIds", "1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(containsString("Invalid metric name: testing")))
            .andReturn();
    }
    
    @Test
    public void testGetMetricsWithWrongOperationName() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/weather/metrics")
                .param("metrics", "humidity")
                .param("sensorIds", "1")
                .param("operations", "maximum")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(containsString("Invalid operation: maximum")))
            .andReturn();
    }
}


