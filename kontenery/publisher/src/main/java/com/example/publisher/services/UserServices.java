package com.example.publisher.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.everit.json.schema.ValidationException;
import java.io.FileNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.publisher.dto.User;
import com.example.publisher.publisher.UserPub;


@RestController
public class UserServices {

    @Autowired
    private RabbitTemplate template;

    Logger logger = LogManager.getLogger();

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) throws FileNotFoundException, JsonProcessingException {
        try {
            UserPub userPub = new UserPub();
            userPub.sendMessage(user, template);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (ValidationException e) {
            e.printStackTrace();
            logger.error("Validation failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
