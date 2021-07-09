package mykafka.kafkaclient.service;

import lombok.extern.slf4j.Slf4j;
import mykafka.kafkaclient.dto.UserDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserProducer {

    private final KafkaTemplate<String, UserDto> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topic;

    @Value("${spring.kafka.replication.factor:1}")
    private int replicationFactor;

    @Value("${spring.kafka.partition.number:1}")
    private int partitionNumber;

    public UserProducer(KafkaTemplate<String, UserDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserDto user) {
        kafkaTemplate.send(topic, user.getId(), user);
    }

    @Bean
    @Order(-1)
    public NewTopic createNewTopic() {
        return new NewTopic(topic, partitionNumber, (short) replicationFactor);
    }
}
