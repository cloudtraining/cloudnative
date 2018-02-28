package demo;

import com.codahale.metrics.*;
import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableCircuitBreaker
@SessionAttributes("devices")
public class DeviceUiApplication {

    private final MetricRegistry metrics = new MetricRegistry();
    private final Meter requestsAddDeviceMetric = metrics.meter("requestsAddDeviceMetric");
    private final Timer timerAddDevice = metrics.timer("timerAddDevice");
    private long metricsConsoleRefreshInterval;
    private final String appName = "device-ui";
    private final Logger slfjLogger = LoggerFactory.getLogger(DeviceUiApplication.class);
    private DynamicBooleanProperty dynamicBooleanProperty;

    @Value("${graphite.host}")
    @Autowired
    private String graphiteHost;

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    @Bean
    public AlwaysSampler alwaysSampler() {
        return new AlwaysSampler();
    }

    //Metrics Reporting
    public DeviceUiApplication() {

        // Archaius get configuation properties
        try {
            ConfigurationManager.loadCascadedPropertiesFromResources(appName + "-config");
        } catch (IOException e) {
            slfjLogger.warn("(DeviceUiApplication) File not found: " + appName + "-config");
        }
        metricsConsoleRefreshInterval = DynamicPropertyFactory.getInstance().getLongProperty("metricsConsoleRefreshInterval", 60).get();
        dynamicBooleanProperty = DynamicPropertyFactory.getInstance().getBooleanProperty("gatherStatistics", false); //Checks every Minute

        // DropWizard Metrics Console Reporting
        ConsoleReporter metricsConsoleReporter = ConsoleReporter.forRegistry(metrics).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
        metricsConsoleReporter.start(metricsConsoleRefreshInterval, TimeUnit.SECONDS);

    }

    @PostConstruct
    protected void startGraphite() {
        // Graphite Reporting to Graphite Server using Dropwizard Metrics
        final Graphite graphite = new Graphite(new InetSocketAddress(graphiteHost, 2003));
        final GraphiteReporter graphiteReporter = GraphiteReporter.forRegistry(metrics).prefixedWith("demo.arity.com").convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL).build(graphite);
        graphiteReporter.start(10, TimeUnit.SECONDS);
    }


    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private IDGeneratorService idGeneratorService;

    @RequestMapping("/user")
    public Map<String, String> user(Principal user) {
        return Collections.singletonMap("name", user.getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(DeviceUiApplication.class, args);
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    .httpBasic().and()
                    .authorizeRequests()
                    .antMatchers("/index.html", "/", "/hystrix.stream", "/turbine.stream").permitAll()
                    .anyRequest().hasRole("USER");
            // @formatter:on
        }
    }

    @RequestMapping(value = "/deviceInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Device> deviceInfo(@ModelAttribute("devices") List<Device> devices) {
        return devices;
    }

    @Timed(name = "deviceUI-addDevice")
    @Counted(name = "deviceUI-Counter")
    @RequestMapping(value = "/addDevice", method = RequestMethod.POST)
    public
    @ResponseBody
    void addDevice(@ModelAttribute("devices") List<Device> devices, @RequestBody Device device) {


        //Archaius Dynamic Property Loading
        Boolean gatherStatistics = dynamicBooleanProperty.get();
        if (gatherStatistics) {
            requestsAddDeviceMetric.mark();
            timerAddDevice.time();
        }

        String identifier = idGeneratorService.generateIdentifier(serviceUrl() + "/device/idGenerator");
        device.setIdentifier(identifier);
        devices.add(device);

        // Sleuth/Zipkin
        //TODO: Add a Transactional SPAN - Span newSpan = tracer.createSpan("Span-SleuthTransactionTraceTag");
        slfjLogger.info("### <=== SLEUTH-TAGS === ###");

        if (gatherStatistics) { timerAddDevice.time().stop(); };
    }

    public String serviceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances("device-service");
        return toURLString(instances.stream().findFirst().get());
    }

    String toURLString(ServiceInstance server) {
        try {
            return server.getUri().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ModelAttribute("devices")
    public List<Device> getFormData() {
        return new ArrayList<Device>();
    }


    public String generateIdentifier(String serviceUrl) {
        String identifier = restTemplate.getForObject(serviceUrl, String.class);
        return identifier;
    }
}
