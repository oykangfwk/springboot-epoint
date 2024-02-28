import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest {

    private final String QUEUENAME = "queue1";

    private final static String EXCHANGENAME="logs";


    private final static String NORMAL_EXCHANGE = "normal_exchange";
    private final static String DEAD_EXCHANGE = "dead_exchange";

    /**
     * 死信队列-生产者
     * 生产者只管往队里里面发送消息
     */
    @Test
    public void deadLetterExchangeProducer() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        //定义交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE,BuiltinExchangeType.DIRECT);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.nextLine();
            //设置延迟TTL  设置10秒到期
            AMQBasicProperties amqBasicProperties = new AMQP.BasicProperties().builder().expiration("10000").build();
            channel.basicPublish(NORMAL_EXCHANGE,"zs",null,message.getBytes());
            System.out.println("发送消息=="+message);
        }
    }

    @Test
    public void deadLetterConsumerNormal() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        //声明普通交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE,BuiltinExchangeType.DIRECT);
        //声明死信交换机
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);
        //声明死信队列
        channel.queueDeclare("dead_queue",false,false,false,null);
        //正常队列和死信队列绑定关系
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        map.put("x-dead-letter-routing-key","ls");
        //声明普通队列  参数为绑定关系
        channel.queueDeclare("normal_queue",false,false,false,map);
        //普通队列与交换机绑定关系
        channel.queueBind("normal_queue",NORMAL_EXCHANGE,"zs");
        //死信队列绑定关系
        channel.queueBind("dead_queue",DEAD_EXCHANGE,"ls");

        DeliverCallback deliverCallback = (tag,delivery)->{
            System.out.println("收到的消息为="+delivery.getBody());
        };
        channel.basicConsume("normal_queue",true,deliverCallback,(string)->{});

    }

    @Test
    public void deadLetterConsumerDead() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);
        channel.queueDeclare("dead_queue",false,false,false,null);
        channel.queueBind("dead_queue",DEAD_EXCHANGE,"ls");
        DeliverCallback deliverCallback = (tag,delivery)->{
            System.out.println("死信队列接收到的消息为="+delivery.getBody());
        };
        channel.basicConsume("dead_queue",true,deliverCallback,(string)->{});
    }


    /**
     * 生产者只将消息发送给交换机
     * @throws IOException
     * @throws TimeoutException
     */
    @Test
    void testExchangePublish() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        //定义交换机
        channel.exchangeDeclare(EXCHANGENAME,BuiltinExchangeType.FANOUT);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.nextLine();
            channel.basicPublish(EXCHANGENAME,"",null,message.getBytes());
            System.out.println("生产者发出消息="+message);
        }
    }

    @Test
    void testExchangeConsumer() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGENAME,BuiltinExchangeType.FANOUT);
        //临时队列，消费者不存在时就会删除
        String queue = channel.queueDeclare().getQueue();
        /**
         * 队列名称，
         * 交换机名称
         * 路由routingkey
         */
        channel.queueBind(queue,EXCHANGENAME,"");

        DeliverCallback deliverCallback =(tag,delivery)->{
            //获取接收到的消息
            String msaage = new String(delivery.getBody(), "UTF-8");
            System.out.println("接收到的消息为="+msaage);
        };
        //true消费成功后自动应答
        channel.basicConsume(queue,true,deliverCallback,(string)->{});
    }

    @Test
    public void testConnectionProducer() throws IOException, TimeoutException, InterruptedException {
        Connection connection = getConnection();
        //创建Channel信道
        Channel channel = connection.createChannel();
        String randomqueue = channel.queueDeclare().getQueue();
        //开启发布确认
        channel.confirmSelect();
        //定义简单队列
        /**
         * 参数：
         * 1.队列名称
         * 2.消息是否持久化 默认存储在内存中
         * 3，该消息队列是否只被一个消费者消费，还是共享  true代表多个消费者可以共享
         * 4.是否自动删除，最后一个消费者断开连接后，该队列是否自动删除 true代表自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUENAME,false,false,false,null);

        int batchSize = 100;

        //异步发布确认 最优方式 只管发送消息，然后使用一个监听器来监听消息是否成功和失败
        //收到消息的回调
        //tag=消息序列号  multiple=是否为批量确认
        ConfirmCallback ackCallback = (tag,multiple)->{
            System.out.println("发送成功的消息，消息标签="+tag +",multiple="+multiple);
        };
        //未收到消息的回调
        ConfirmCallback nackCallback = (tag,multiple)->{
            System.out.println("未确认的消息，消息标签="+tag +",");
        };
        /**
         * 异步通知
         * 1.收到确认消息的回调
         * 2.未收到确认消息的回调
         */
        channel.addConfirmListener(ackCallback,nackCallback);

        //发布确认 发送1000个
        for (int i = 0; i < 100; i++) {
            /**
             * 参数：
             * 1.发送到哪个交换机
             * 2.路由key
             * 3.其他参数信息  消息持久化 messageproperties.persistent_text_plain
             * 4.消息体
             */
            channel.basicPublish("",QUEUENAME,MessageProperties.PERSISTENT_TEXT_PLAIN,String.valueOf(i).getBytes());
            //单个确认发布 发一个确认一个 服务端返回false或者超时内没有返回，可以重新发送
//            boolean ret = channel.waitForConfirms();
//            if(ret){
//                System.out.println(String.valueOf(i) + "  ==发送成功");
//            }

            //批量发布确认，批量进行消息确认
//            if(i%batchSize == 0){
//                channel.waitForConfirms();
//            }



        }


        System.out.println("发送消息完毕");

    }

    public void testConsumer() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        //channel.queueDeclare(QUEUENAME,false,false,false,null);
        System.out.println("等待消息");
        DeliverCallback deliverCallback = (consumerTag,deliver)->{
            String message = new String(deliver.getBody());
            System.out.println("接收消息=="+message+",consumerTag="+consumerTag);
            /**
             * 1.消息标记tag
             * 2.是否批量应答未应答消息
             */
            channel.basicAck(deliver.getEnvelope().getDeliveryTag(),false);
        };
        //取消消费的回调接口，如在消费的时候队列被删除了
        CancelCallback cancelCallback = S->{
            System.out.println("消费消息被中断=="+S);
        };
        /**
         * 消费者消费消息
         * 1.队列名称
         * 2.消费成功后是否自动应答 true为自动应答
         * 3.当队列中存在消息时，rabbitmq调用的回调接口，用于将消息传递过来
         * 4.cancelCallback 消费者未成功消费的回调
         */
        channel.basicConsume(QUEUENAME,false,deliverCallback,cancelCallback);
        System.out.println("消费结束");
    }


    public Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.232.188/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
