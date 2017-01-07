package demo;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DeviceUiApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port:0", "security.user.password:foo"})
public class ApplicationTests {

    @Value("${local.server.port}")
    private int port;

    private RestTemplate template = new TestRestTemplate();

    @Test
    public void homePageLoads() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void userEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/user", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void resourceEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/resource", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void loginSucceeds() {
        RestTemplate template = new TestRestTemplate("user", "foo");
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + port
                + "/user", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void archaiusLoadMetricsConsoleRefreshInterval() {
        ConfigurationManager.getDeploymentContext().setDeploymentEnvironment("development");
        long metricsConsoleRefreshInterval = DynamicPropertyFactory.getInstance().getLongProperty("metricsConsoleRefreshInterval", 60).get();
        assertEquals(Long.parseLong("90"), metricsConsoleRefreshInterval);
    }

    @Test
    public void archaiusGatherStatistics() {
        ConfigurationManager.getDeploymentContext().setDeploymentEnvironment("development");
        DynamicBooleanProperty dynamicBooleanProperty = DynamicPropertyFactory.getInstance().getBooleanProperty("gatherStatistics", false); //Checks every Minute
        assertEquals(true, dynamicBooleanProperty.get());
    }
}
