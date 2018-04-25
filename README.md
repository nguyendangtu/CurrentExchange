Current Exchange Java Application:
----------------------- 
Current Exchange Application is Java Application. It expose three APIs which allow for users:
1) Given a date, get the exchange rate of all the currencies (wrt USD)
2) Given a date and 2 currencies, find the exchange rate between them
3) Given a date range and a given currency, find the exchange rate of that currency (wrt USD) for the entire date range
------------
Technology:
------------
- This application build on Spring Boot 2.0.1.RELEASE, Java 8, Swagger2 and JUnit
- We run the jar file standalone by repackage all source code and dependencies. 
-----------------
How to run and use APIs:
-----------------
STEP 1: SETUP
1. Setup JRE 8 on your local machine.
2. Download the jar file CurrentExchange-1.0-SNAPSHOT.jar to your local folder.
3. Go to download folder and run the command: java -jar CurrentExchange-1.0-SNAPSHOT.jar
4. Open Chrome, parse the link 
 -     http://localhost:8080/swagger-ui.html#/rate-exchange-controller

STEP 2: USE APIs
##### getUSDXRates: navigate to the link 
 -      http://localhost:8080/swagger-ui.html#!/rate-exchange-controller/getUSDXRatesUsingGET 
 then input a business day with format YYYY-MM-DD
 - e.g: 
 -       businessDate: 2017-01-01
  Click 'Try it out!' and see the result
 If data existing, API will return a list of currency and their rates. If date is not existing, they will return error message.
 
##### getXRatesBy2CCY. navigate to the link
 -      http://localhost:8080/swagger-ui.html#!/rate-exchange-controller/getXRatesBy2CCYUsingGET 
 then input business date (format YYYY-MM-DD), input the first currency (3 characters), input the second currency (3 characters)
 - e.g: 
 -      businessDate  : 2017-01-01
 -      firstCurrency : GBP
 -      secondCurrency: OMR
 Click 'Try it out!' and see the result
 If data existing, they will return the rate between two currency. If not they will return zero
 
##### getUSDXRatesByRange. navigate to the link 
 -     http://localhost:8080/swagger-ui.html#!/rate-exchange-controller/getUSDXRatesByRangeUsingGET 
 then input startDate (YYYY-MM-DD), input endDate (YYYY-MM-DD), input currency (3 characters)
 - e.g: 
 -     startDate: 2017-01-01
 -     endDate  : 2017-01-03
 -     currency : SGD
Click 'Try it out!' and see the result. 
If data existing, they will return a list of Rate (date, currency, rate). rate will be empty 

--------------------
Download source and Maven build:
--------------------
1) Setup JDK 8 and JRE 8, maven in your local machine. 
2) Download source code from git or zip file, unzip source to your local folder
3) Import source code by using IntelliJ or Eclipse. Change IDE setting to Java 8.
4) Go to folder which contains source code, type command: [ mvn clean install ] to build source code.
------------
Source code explanation:
------------
I. OVERVIEW
Current Exchange implemented by using Spring Boot, Java 8, Swagger2, JUnit and Maven.
1. Maven: using to build source
2. Spring Boot: wanna to embedded Tomcat and run application standalone.
3. Swagger: expose application APIs [ http://localhost:8080/swagger-ui.htm ]
4. JUnit: implement unit testing.

II. DETAILS 
 
Application having Controller, Service, Constants and Bean package.
##### Application.java: 
- boot application and scan and init all beans into com.singtel.currentexchange
we enable cache by using annotation @EnableCaching
##### SwaggerConfig.java: 
- Swagger 2 is enabled through the @EnableSwagger2 annotation. This configuration integrate with 
with swagger 2 into spring boot project to make entire APIs will be available through swagger.
##### RateExchangeController.java:
 - a. getUSDXRates
     - `@RequestMapping(method = RequestMethod.GET, value = "/getUSDXRates")
     @Cacheable(value = "USDXRates", key = "#businessDate")
     public TreeMap<String, String> getUSDXRates(@RequestParam("businessDate") @DateTimeFormat(pattern = Constants.YYYY_MM_DD) LocalDate businessDate){
     }`
     
     - This function return exchange rate of all currencies for one business day.
     - @Cacheable: Caching ability. (The first execution should cache the results so that it can be used in the next execution).
     If the second request the same business day, this function will return immediately. 
     - @DateTimeFormat(pattern = Constants.YYYY_MM_DD): define format pattern for input business day.
 - b. getXRatesBy2CCY 
     - `@RequestMapping(method = RequestMethod.GET, value = "/getXRatesBy2CCY")
     @Cacheable(value = "XRatesBy2CCY", key = "#businessDate + '-' +#firstCurrency + '-' + #secondCurrency")
     public Double getXRatesBy2CCY(@RequestParam("businessDate") @DateTimeFormat(pattern = Constants.YYYY_MM_DD) LocalDate businessDate,
                                      @RequestParam("firstCurrency") String firstCurrency,
                                      @RequestParam("secondCurrency") String secondCurrency) {}`
     - This function return the rate between two currencies for one business day.
     - @Cacheable: Caching ability. The first execution will be cache the results. If the next execution the same business day,
     first currency and second currency, it will return immediately.
     - @DateTimeFormat(pattern = Constants.YYYY_MM_DD): define format pattern for input business day.
 - c. getUSDXRatesByRange
     - `@Cacheable(value = "USDXRatesByRange", key = "#startDate + '-' + #endDate + '-' + #currency")
          @RequestMapping(method = RequestMethod.GET, value = "/getUSDXRatesByRange")
          public List<Rate> getUSDXRatesByRange(@RequestParam("startDate") @DateTimeFormat(pattern = Constants.YYYY_MM_DD) LocalDate startDate,
                                                @RequestParam("endDate") @DateTimeFormat(pattern = Constants.YYYY_MM_DD) LocalDate endDate,
                                                @RequestParam("currency") String currency) throws Exception {}`
     - This function return all rates for one currency in date range (from startDate to endDate) 
     - @Cacheable: Caching ability. The first execution will be cache the results. If the next execution the same (start date, end date and currency), it will return immediately.
     - @DateTimeFormat(pattern = Constants.YYYY_MM_DD): define format pattern for input business day.          

##### RateExchangeService.java  
 - a. getUSDXRates: provide service to get all CCY from given local date.
 - b. getXRatesBy2CCY: provide a service to get rate between two currency from a locate.
 - c. getUSDXRatesByRange: provide a service to get all rates for one CCY from and range (from startDate to endDate).
 this service designed by using multi threads. each thread will be executed for one local date. If a range are years data,
 this service will call RateCallable to running parallel. When all threads completed it return a list of rates.
     - `List<RateCallable> rateCallables = fileNames.stream().map(fileName -> new RateCallable(fileName, currency)).collect(Collectors.toList())`
     - `List<Future<Rate>> results = service.invokeAll(rateCallables);`
##### All Unit test class
- Unit testing and code coverage.
##### Data example:
The data contains the currency Exchange rates for a set of
currencies on different dates.
- The data for one day will be stored in one file. i.e There will be
one file for one day.
- The file name format is yyyy-mm-dd.txt (eg: 2017-01-01.txt).
- These files store the exchange rate of following currencies (wrt USD):
1. United States Dollar: USD
2. Swiss Franc: CHF
3. Euro: EUR
4. British Pound: GBP
5. Omani Rial: OMR
6. Bahraini Dinar: BHD
7. Kuwaiti Dinar: KWD
8. Singapore Dollar: SGD
- The sample data for 2017-01-01 is shown as below:
- file name: 2017-01-01.txt
- 1 CHF traded at 1.04 times USD
- 1 EUR traded at 1.19 times USD
- 1 GBP traded at 1.30 times USD
- 1 OMR traded at 2.60 times USD
- 1 BHD traded at 2.65 times USD
- 1 KWD traded at 3.32 times USD
- 1 SGD traded at 0.74 times USD
