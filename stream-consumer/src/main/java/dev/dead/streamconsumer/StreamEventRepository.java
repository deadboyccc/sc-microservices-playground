package dev.dead.streamconsumer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StreamEventRepository extends ReactiveMongoRepository<StreamEvent, String> {
}

