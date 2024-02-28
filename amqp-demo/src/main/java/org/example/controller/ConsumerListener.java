package org.example.controller;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class ConsumerListener {

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) throws UnsupportedEncodingException {
        String ms = new String(message.getBody(), "utf-8");
        log.info("接收到的消息为="+ms);
    }
}
