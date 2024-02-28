package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/ttl")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendmsg/{message}")
    public void sendMsg(@PathVariable String message){
        log.info("发送消息到队列=="+message);
        rabbitTemplate.convertAndSend("X","XA","10秒ttl="+message);
        rabbitTemplate.convertAndSend("X","XB","5秒ttl="+message);
    }

    @GetMapping("/sendttlmsg/{message}/{ttl}")
    public void sendMsg(@PathVariable String message,@PathVariable String ttl){
        log.info("发送消息到队列=="+message);
        rabbitTemplate.convertAndSend("X","XC",correlation->{
            correlation.getMessageProperties().setExpiration(ttl);
            return correlation;
        });
    }

    /**
     * 发送延迟消息
     */
    @GetMapping("/delaymessage/{msg}/{ttl}")
    public void sendDelayMessage(@PathVariable String msg,@PathVariable int ttl){
        log.info("开始发送延时消息=="+msg);
        rabbitTemplate.convertAndSend("deley_exchange","delay-custom",msg,(correlation)->{
            //此处设置的延迟，交换机到了时间后进行发送
            correlation.getMessageProperties().setDelay(ttl);
            return correlation;
        });
    }
}
