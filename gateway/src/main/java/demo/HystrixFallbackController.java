package demo;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HystrixFallbackController {

    @RequestMapping(value = "/epicfail/{id}", method = RequestMethod.GET, produces = "application/json")
    @HystrixCommand(fallbackMethod = "sample")
    public String epicfail(@PathVariable int id)  {

        throw new RuntimeException("this command always fails");

    }

    public String sample(int id,Throwable t) {
        System.out.println("### Sample Hystrix Failure ### - "+ t.toString());

        return "### Sample Hystrix Failure Recovery ###";
    }

}
