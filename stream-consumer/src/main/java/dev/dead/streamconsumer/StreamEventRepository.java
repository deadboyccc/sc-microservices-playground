package dev.dead.streamconsumer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamEventRepository extends ReactiveMongoRepository<StreamEvent, String> {
}

