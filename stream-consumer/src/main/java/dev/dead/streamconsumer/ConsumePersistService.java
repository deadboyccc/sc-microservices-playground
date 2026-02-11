package dev.dead.streamconsumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConsumePersistService {

    private final StreamEventRepository repository;
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
            repository.save(event)
                    .subscribe(savedEvent -> log.debug("Saved event: {}", savedEvent));

        };
    }
}
