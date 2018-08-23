
*Currently this app showcases the following technologies*:
*Spring Boot, REST, ZUUL, SSO, Redis, Swagger, Hystrix, Turbine, Graphite, DropWizard, DropWizard Metrics, Docker, Docker Compose, Maven, AngularJS, ReactJS, Eureka*

IMPORTANT
-----------------
Pre-requisites
* You must have a working Docker environment running locally.

> For Windows (prior to V10 with Hyper-V support) or Mac environments, you must use a docker machine locally. Make sure you have a docker machine running at 192.168.99.100.
> For Windows Pro or Enterprise with Hyper-V support, a docker-machine is not needed and docker services use 'localhost'. You must run and build under profile name 'localDocker'.

* <font color="red">You **MUST** start the app/microservices in specific order.</font>
* If your using IntelliJ, you must turn on Annotation Processors via menu option *Preferences > Build, Execution, Deployment > Compiler > Annotation Processors*.

Contributors: Sherjeel Ghouse, Scott Kramer, Dan Mohnen, Derek Ashmore

Contributing Process: We would love you to contribute to this reference application!  Please keep in mind at all times the project mission making this application simplistic and easy to learn from.

Things you can add to the project...
 1) Config Server
 2) Convert to Gradle Project.
 3) oWasp JSON libraries
 4) PIT testing; Unit Test
 5) Jenkins using Docker  and  CI Integration
 6) JMeter or Selenium
 7) RestEasy
 8) Spring Config
 9) oAuth
10) Splunk or elastic search or kibanna
22) Flyway
23) PCF Deployment?
24) Rest Assured
25) Artifactory
26) Spring Sleuth
27) Add React
28) Deploy scripts to Docker & AWS
29) Zipkin
30) Jest & Enzyme?
*Not sure if we will add Mock MVC, influxDB, CollectD, Archaius, PCF, Spring Integration, RabbitMQ, Connection Pooling, Kafka, Netflix Ribbon*

Start Up Instructions
----------------------
 1. START DOCKER CONTAINERS<br>
     a. Failure to start Docker first **will** cause errors.<br>
     b.At command/terminal prompt (in base project directory): <font color="blue">docker-compose up</font>

 2. BUILD ALL PROJECTS<br>
     a. At command/terminal prompt (in base project directory): <font color="blue">mvn clean package</font><br>
     <font color="gray" size="2">*Errors? fail to build? - make sure you have redis running on 192.168.99.100:6379 via command line docker ps -a*</font> 

 3. START GATEWAY APPLICATION<br>
     a. In your IDE (IntelliJ or Eclipse/STS), right click on "gateway" Microservice Project, src/ main/ java/ demo/ GatewayApplication <font color="blue">Run As Spring Boot Application</font>

 4. START SERVICE-REGISTRY APPLICATION<br>
     a. In your IDE (IntelliJ or Eclipse/STS), right click on "service-registry" Microservice Project, src/ main/ java/ demo/ ServiceRegistryApplication <font color="blue">Run As Spring Boot Application</font>

 5. Start Other Services<br>
     a. In your IDE (IntelliJ or Eclipse/STS), right click on *EACH* Microservice Project<br>
     b. <font color="blue">deviceservice/src/main/java/demo/DeviceServiceApplication Run As Spring Boot Application</font><br>
     c. <font color="blue">deviceui/src/main/java/demo/DeviceUiApplication Run As Spring Boot Application</font><br>
     d. <font color="blue">hystrixdashboard/src/main/java/demo/HystrixDashboardApplication Run As Spring Boot Application</font><br>
     e. <font color="blue">admin-ui/src/main/java/demo/AdminUIApplication Run As Dropwizard Application</font><br>

How to Use The Reference App
----------------------------
 1. TEST USER INTERFACE<br>
     Go to http://localhost:8080/<br>
     Login user/user  or admin/admin<br>
     Click 'Device' button<br>
     Add a device and click Submit<br>
     Click 'Admin' button<br>
     Click [refresh] to display health of one of the services listed<br>
     <br>   
 2. TEST HYSTRIX<br>
     Go to http://localhost:8686/hystrix<br>
     Enter stream http://localhost:8080/hystrix.stream<br>
     --> note that the monitor shows only methods failing, so initially it will show nothing<br>
     Click Monitor button<br>
     Test a failure Go to http://localhost:8080/epicfail/1<br>
     <br>
 3. TEST TURBINE<br>
     Go to http://localhost:8686/hystrix<br>
     Enter stream<br>
     *http://localhost:8686/turbine.stream?cluster=SAMPLE-HYSTRIX-AGGREGATE*<br>
     Click Monitor button<br>
     Test a failure Go to http://localhost:8080/epicfail/1<br>
     <br>
 4. TEST DROPWIZARD CONSOLE METRICS<br>
     Go to console for device-ui<br>
     <br>
 5. TEST GRAPHITE<br>
     Go to http://localhost:8780
     login: guest/guest
     <br>
 6. TEST SWAGGER<br>
     Go to http://localhost:9091/swagger-ui.html
     login: guest/guest
     <br>
 7. LOOK AT EUREKA<br>
    Go To http://localhost:8761<br>
    <br>
 8. LOOK AT ZIPKIN/SLEUTH<br>
    Start Zipkin Docker Container via:<br> 
    docker run -d -p 9411:9411 openzipkin/zipkin<br>
    Go To http://localhost:9411<br>   
