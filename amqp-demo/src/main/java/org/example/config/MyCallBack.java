package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 交换机确认回调接口
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 需要将该类注入到rabbittemplate中
     */
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        //注入重写的属性
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 交换机不管成功收到消息还是失败 都会调用
     * @param correlationData correlation data for the callback. 交换机接收成功、失败的回调 数据 保存回调消息的id、数据等
     * @param ack true for ack, false for nack   如果收到消息 为true ,收不到为false
     * @param cause An optional cause, for nack, when available, otherwise null.   如果失败，失败原因  成功为空
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId() == null ? "" : correlationData.getId();
        //如果成功
        if(ack){
            log.info("交换机已经收到id为:{}的消息",id);
        }else{
            log.info("交换机未收到id:{}的消息，原因为:{}",id,cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

    }
}
