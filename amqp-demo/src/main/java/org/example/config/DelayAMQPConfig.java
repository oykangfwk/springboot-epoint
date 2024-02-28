package org.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 延迟队列、交换机bean声明
 * 延迟交换机类型 x-delayed-message 需要下载延迟插件
 * 此消息延迟是由交换机来决定的
 */
@Configuration
public class DelayAMQPConfig {

    private static final String DELAY_EXCHANGE = "deley_exchange";
    private static final String DELAY_QUEUE = "deley_queue";

    /**
     * 定义延迟交换机，需要使用到延迟插件
     */
    @Bean
    public CustomExchange customExchange(){
        HashMap<String, Object> map = new HashMap<>();
        //自定义交换机类型
        map.put("x-delayed-type","direct");
        return new CustomExchange(DELAY_EXCHANGE,"x-delayed-message",true,true,map);
    }

    /**
     * 定义延迟队列
     * @return
     */
    @Bean
    public Queue queueDelay(){
        return new Queue(DELAY_QUEUE);
    }

    /**
     * 定义延迟队列绑定关系
     */
    @Bean
    public Binding queueDelayBindCustomExchange(@Qualifier("queueDelay")Queue delyaqueue,@Qualifier("customExchange")CustomExchange customExchange){
        return BindingBuilder.bind(delyaqueue).to(customExchange).with("delay-custom").noargs();
    }
}
