package mykafka.kafkaclient.service;

import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mykafka.kafkaclient.dto.UserDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserConsumer {

    private List<UserDto> storage = new LinkedList<>();

    @KafkaListener(topics = "${spring.kafka.topic.name}", concurrency = "${spring.kafka.consumer.level.concurrency:3}")
    public void consume(@Payload UserDto user,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset) {
        log.info("Received a message contains a user {}, from {} topic, " +
                "{} partition, and {} offset", user.toString(), topic, partition, offset);
        storage.add(user);
    }

    public List<UserDto> getAllUsers() {
        return storage;
    }
}
