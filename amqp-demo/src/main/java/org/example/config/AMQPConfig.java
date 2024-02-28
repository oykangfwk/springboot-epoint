package org.example.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class AMQPConfig {

    private final static String X_EXCHANGE = "X";
    private final static String Y_DEAD_LETTER_EXCHANGE = "Y";
    private final static String QUEUE_A = "QA";
    private final static String QUEUE_B = "QB";
    private final static String QUEUE_D = "QD";

    @Bean
    public DirectExchange XdirectExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean
    public DirectExchange YdirectExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue queueA(){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        //绑定死信交换机
        objectObjectHashMap.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        objectObjectHashMap.put("x-dead-letter-routing-key","YD");
        //声明队列的TTL
        objectObjectHashMap.put("x-message-ttl",10000);
        //定义队列A
        return QueueBuilder.durable(QUEUE_A).withArguments(objectObjectHashMap).build();
    }

    @Bean
    public Queue queueB(){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        objectObjectHashMap.put("x-dead-letter-routing-key","YD");
        objectObjectHashMap.put("x-message-ttl",5000);
        return QueueBuilder.durable(QUEUE_B).withArguments(objectObjectHashMap).build();
    }

    @Bean
    public Queue queueD(){
        return new Queue(QUEUE_D);
    }

    //绑定队列A和X交换机关系
    @Bean
    public Binding queueabindx(@Qualifier("queueA") Queue a,
                               @Qualifier("XdirectExchange")DirectExchange x){
        return BindingBuilder.bind(a).to(x).with("XA");
    }

    @Bean
    public Binding queuebbindx(@Qualifier("queueB") Queue b,@Qualifier("XdirectExchange") DirectExchange x){
        return BindingBuilder.bind(b).to(x).with("XB");
    }

    @Bean
    public Binding queuedbingy(@Qualifier("queueD") Queue d,@Qualifier("YdirectExchange")DirectExchange y){
        return BindingBuilder.bind(d).to(y).with("YD");
    }

}
