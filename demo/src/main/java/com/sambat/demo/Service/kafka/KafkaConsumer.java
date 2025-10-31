package com.sambat.demo.Service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "kafka-test", groupId = "test-1")
    public void listen(String message){
        log.info("Received message='{}'", message);
    }
}
