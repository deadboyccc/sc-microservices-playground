package dev.dead.streamproducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import java.util.List;

@SpringBootApplication
public class StreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamProducerApplication.class, args);
    }

}

@Slf4j
@RestController
class TestController {
    private final StreamBridge streamBridge;
    private final Scheduler scheduler;
    private final WebClient webClient;

    public TestController(StreamBridge streamBridge, Scheduler scheduler,
                          WebClient.Builder webClientBuilder) {
        this.streamBridge = streamBridge;
        this.scheduler = scheduler;
        this.webClient = webClientBuilder.build();
    }

    @PostMapping("/manual-send")
    public String test(@RequestBody String payload) {
        log.info(">>> Received payload: {}", payload);
        streamBridge.send("messageProducer-out-0", payload);
        return "Message sent: " + payload.toUpperCase();
    }

    @PostMapping("/manual-send-async")
    public String testAsync(@RequestBody String payload) {
        scheduler.schedule(() -> {
            log.info(">>> Sending message asynchronously: {}", payload);
            streamBridge.send("messageProducer-out-0", payload);
        });
        return "Message scheduled for async send: " + payload.toUpperCase();
    }

    @PostMapping("/manual-send-reactive")
    public String testReactive(@RequestBody String payload) {
        Flux.fromIterable(List.of(1, 2, 3, 4, 5))
                .delayElements(java.time.Duration.ofSeconds(1))
                .doOnNext(i -> {
                    String msg = payload + " - " + i;
                    log.info(">>> Sending reactive message: {}", msg);
                    streamBridge.send("messageProducer-out-0", msg);
                })
                .subscribeOn(scheduler)
                .subscribe();
        return "Message scheduled for reactive message: " + payload.toUpperCase();
    }


    @PostMapping("/weblcient-send")
    public String testAsyncWebClient(@RequestBody String payload) {
        //jsonholder
        webClient.post()
                .uri("https://jsonplaceholder.typicode.com/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .subscribeOn(scheduler)
                .subscribe(response -> {
                    log.info(">>> Received response from external API: {}", response);
                    streamBridge.send("messageProducer-out-0", payload);
                }, error -> {
                    log.error(">>> Error calling external API: {}", error.getMessage());
                });

        return payload;
    }
}
