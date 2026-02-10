package dev.dead.streamconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumePersistService {
    @Bean
    public Consumer<String> messageProcessor() {
        return payload -> {
            log.debug("Received message: {} - simulating persis ",
                    payload);
        };
    }
}
