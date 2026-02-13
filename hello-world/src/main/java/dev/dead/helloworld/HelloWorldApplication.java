package dev.dead.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

}

@RestController
class HelloController {
    @GetMapping("/hello")
    public String sayHello(@RequestParam(required = false,
            defaultValue = "1500") String delay) {
        try {
            long delayMs = Long.parseLong(delay);
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
        }
        return "Hello, World!";
    }
}
