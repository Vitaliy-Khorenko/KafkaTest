package mykafka.kafkaclient.controller;

import java.util.List;
import java.util.UUID;
import mykafka.kafkaclient.dto.UserDto;
import mykafka.kafkaclient.service.UserConsumer;
import mykafka.kafkaclient.service.UserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserProducer userProducer;
    private final UserConsumer userConsumer;

    @Autowired
    UserController(UserProducer userProducer, UserConsumer userConsumer) {
        this.userProducer = userProducer;
        this.userConsumer = userConsumer;
    }

    @PostMapping
    public void sendNewUser(@RequestParam("name") String name) {
        UserDto user = new UserDto()
                .setId(UUID.randomUUID().toString())
                .setName(name);
        userProducer.send(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userConsumer.getAllUsers());
    }
}
