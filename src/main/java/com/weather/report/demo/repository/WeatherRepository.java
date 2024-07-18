package com.weather.report.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weather.report.demo.models.Metric;

@Repository
public interface WeatherRepository extends JpaRepository<Metric, Long> {

	@Query("SELECT m FROM Metric m WHERE m.metricName = :metricName AND m.timestamp BETWEEN :startDate AND :endDate")
	List<Metric> findByMetricNameAndTimestamp(@Param("metricName") String metricName,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	@Query("SELECT m FROM Metric m WHERE m.metricName = :metricName AND m.timestamp BETWEEN :startDate AND :endDate AND m.sensorId = :sensorId")
	List<Metric> findByMetricNameIdAndTimestamp(@Param("metricName") String metricName,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
			@Param("sensorId") int sensorId);

	@Query("SELECT AVG(m.metricValue) FROM Metric m WHERE m.metricName = :metricName AND m.sensorId = :sensorId AND m.timestamp BETWEEN :startDate AND :endDate")
	Double findAvgByMetricNameIdAndTimestamp(@Param("metricName") String metricName,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
			@Param("sensorId") int sensorId);

	@Query("SELECT min(m.metricValue) FROM Metric m WHERE m.metricName = :metricName AND m.sensorId = :sensorId AND m.timestamp >= :startDate and  m.timestamp <= :endDate")
	Double findMinByMetricNameIdAndTimestamp(@Param("metricName") String metricName,@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,@Param("sensorId") int sensorId);

	@Query("SELECT MAX(m.metricValue) FROM Metric m WHERE m.metricName = :metricName AND m.sensorId = :sensorId AND m.timestamp BETWEEN :startDate AND :endDate")
	Double findMaxByMetricNameIdAndTimestamp(@Param("metricName") String metricName,
			@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate,
			@Param("sensorId") int sensorId);

	@Query("SELECT m FROM Metric m WHERE m.metricName = :metricName AND m.sensorId = :sensorId AND m.timestamp BETWEEN :todayStart AND :todayEnd")
	List<Metric> findLatestMetric(@Param("metricName") String metricName, @Param("sensorId") int sensorId,@Param("todayStart") LocalDateTime todayStart,@Param("todayEnd") LocalDateTime todayEnd);

}
