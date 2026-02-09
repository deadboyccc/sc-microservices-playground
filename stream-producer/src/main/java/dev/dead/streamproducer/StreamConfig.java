package dev.dead.streamproducer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Supplier;

@Configuration
public class StreamConfig {

    @Bean
    public Supplier<String> messageProducer() {
        return () -> "New message at " + System.currentTimeMillis();
    }
}