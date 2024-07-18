# Weather Metrics API

This is a Spring Boot application for managing and querying weather metrics from various sensors. The API allows users to store metric values and retrieve summary statistics such as minimum, maximum, average, and sum of metric values over specified time periods.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)

## Features

- Store metric values from sensors.
- Retrieve multiple summary statistics (sum, average, minimum, maximum) for specified metrics or multiple metrics over specified time periods with given sensors.
- Support for querying by date, date range, and sensor ID.
- Default to the latest day's avg data if no date or operation is specified.
- API Documentation using Swagger
- Logback for Logging

## Requirements

- Java 17 or higher
- Spring Boot 3.0.0
- H2 Database (for development and testing)

## Installation

1. **Clone the repository:**

 ``` 
   
 git clone https://github.com/Aishwarya1410/190724.git
    
 cd 190724
	
``` 

2. **Build the project:**

```   
mvn clean install
```    

3. **Run the application:**

``` 
mvn spring-boot:run
```
The application will start on
```
http://localhost:8080
```
## Usage

**Swagger API**
APIs can be checked at:

   ```
   http://localhost:8080/swagger-ui/index.html
 ```
**H2 Database**

 H2 console can be accessed at :
 
   ```
   http://localhost:8080/h2-console
```

Login Credentials

 ```
 
JDBC URL: jdbc:h2:~/metricdata
username: sa
password:

```
## API-Endpoints

### Storing Metrics

You can use an API client like Postman or `curl` to send POST requests to store metric values.

Example:

```
curl -X POST "http://localhost:8080/api/v1/weather/metrics" -H "Content-Type: application/json" -d '{
	"metricName":"temperature",
    "sensorId": 1,
    "metricValue": 30,
    "timestamp": "2024-07-19T15:15:30"
}'
```

### Fetching Metrics

```
curl --location --request GET 'http://localhost:8080/api/v1/weather/metrics?operations=min&date=2024-07-19&metrics=temperature&sensorIds=1'
```

Note:
 
```
- sensorId and metric are mandatory
- operations allowed min,max,sum,avg
- multiple sensor, operations or metrics can be passed comma seperated like ?operations=min,max or ?metrics=humidity,temperature or ?sensorIds=1,2 for sensorIds
- month can be passed as yyyy-mm (2024-07), will give result of 7th month
- for specific date pass value of date parameter as yyyy-mm-dd
- for date range pass startDate and endDate as parameter in form yyyy-mm-dd
```

## Testing

**Postman Collection can be utilized to test all scenarios** :  src\test\resources\postman_collection
