package mykafka.kafkaclient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import mykafka.kafkaclient.TestAppKafka;
import mykafka.kafkaclient.dto.UserDto;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@Import(TestAppKafka.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserConsumerTest {

    private static final String SOME_USER_ID = "SomeUserId";
    private static final String SOME_NAME = "Some Name";
    private final String TOPIC_NAME = "myusers";
    @Captor
    ArgumentCaptor<UserDto> userArgumentCaptor;
    @Captor
    ArgumentCaptor<String> topicArgumentCaptor;
    @Captor
    ArgumentCaptor<Integer> partitionArgumentCaptor;
    @Captor
    ArgumentCaptor<Long> offsetArgumentCaptor;
    private Producer<String, String> producer;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private UserConsumer userConsumer;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(
                KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(),
                new StringSerializer()).createProducer();
    }

    @Test
    void consume() throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(new UserDto()
                .setId(SOME_USER_ID)
                .setName(SOME_NAME));
        producer.send(new ProducerRecord<>(TOPIC_NAME, 0, SOME_USER_ID, message));
        producer.flush();

        verify(userConsumer, timeout(5000).times(1))
                .consume(userArgumentCaptor.capture(), topicArgumentCaptor.capture(),
                        partitionArgumentCaptor.capture(), offsetArgumentCaptor.capture());

        UserDto user = userArgumentCaptor.getValue();
        assertNotNull(user);
        assertEquals(SOME_USER_ID, user.getId());
        assertEquals(SOME_NAME, user.getName());
        assertEquals(TOPIC_NAME, topicArgumentCaptor.getValue());
        assertEquals(0, partitionArgumentCaptor.getValue());
        assertEquals(0, offsetArgumentCaptor.getValue());
    }

    @AfterAll
    void shutdown() {
        producer.close();
    }

}
