package dev.dead.streamproducer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.function.Supplier;

@Configuration
public class StreamConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public Scheduler producerScheduler() {
        return Schedulers.newBoundedElastic(10, 100, "producer-scheduler");
    }
    @Bean
    public Supplier<String> messageProducer() {
        return () -> {
            String msg = "New message at " + System.currentTimeMillis();
            System.out.println(">>> SUPPLIER PRODUCING: " + msg);
            return msg;
        };
    }
}