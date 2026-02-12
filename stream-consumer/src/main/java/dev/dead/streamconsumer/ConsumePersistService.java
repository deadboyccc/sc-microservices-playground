package dev.dead.streamconsumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

@Slf4j
@Configuration
@Service
public class ConsumePersistService {

    private final StreamEventRepository repository;
    private final WebClient webClient;

    public ConsumePersistService(StreamEventRepository repository,
                                 WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClient = webClientBuilder.build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public Consumer<String> messageProcessor() {
        return payload -> {
            log.debug("Received message: {} - simulating persis ",
                    payload);
            // persistance to mongodb
            StreamEvent event = StreamEvent.builder()
                    .payload(payload)
                    .timestamp(java.time.LocalDateTime.now())
                    .build();


            webClient.get()
                    .uri("http://final-service:8080/hello-world")
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(info -> log.info("Producer info: {}", info));
        };
    }
}
