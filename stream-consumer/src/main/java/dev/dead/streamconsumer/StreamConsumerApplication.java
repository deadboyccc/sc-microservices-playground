package dev.dead.streamconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class StreamConsumerApplication {


    static void main(String[] args) {
        ConfigurableApplicationContext ctx =
                SpringApplication.run(StreamConsumerApplication.class, args);

        String mongodDbHost = ctx.getEnvironment()
                .getProperty("spring.data" +
                        ".mongodb.uri");
        log.info("mongodDbHost: {}", mongodDbHost);
    }


}
