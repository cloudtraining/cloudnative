package demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class DeviceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceServiceApplication.class, args);
    }
}


@Api(value = "/device", authorizations = { @Authorization(value = "SSO Authorization",
        scopes = { @AuthorizationScope(scope = "device:id", description = "unique ID")
        })
}, tags = "IDGeneratorService")
@RestController
@RequestMapping("/device/idGenerator")
class DeviceService {

    @ApiOperation( value = "Returns a String Unique ID", response = String.class )
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Unable to complete request") })
    @HystrixCommand(fallbackMethod = "resilient")
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    String idGenerator() {
        return UUID.randomUUID().toString();
    }
}

