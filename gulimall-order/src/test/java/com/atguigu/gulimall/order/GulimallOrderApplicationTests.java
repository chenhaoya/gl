package com.atguigu.gulimall.order;

import com.atguigu.gulimall.order.entity.OrderReturnApplyEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallOrderApplicationTests {


    @Resource
    private AmqpAdmin amqpAdmin;

    /**
     * 1、创建Exchange、Queue、Binging
     *    1)、使用AmqpAdmin创建
     * 2、收发消息
     * */
    @Test
    public void createExchange() {
        // 1.1、创建交换机
        Exchange directExchange = new DirectExchange(
                "hello-java-exchange",
                true,
                false
                );
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange has been created：hello-java-exchange");
    }

    @Test
    public void createQueue() {
        // 1.2、创建队列   参数：exclusive ：排他（只允许一个连接连接）
        Queue queue = new Queue(
                "hello-java-queue",
                true,
                false,
                false
        );
        amqpAdmin.declareQueue(queue);
        log.info("Queue has been created：hello-java-queue");
    }

    @Test
    public void createBinding() {
        // 1.3、创建绑定关系
        // 目的地：String destination,
        // 目的地类型：Binding.DestinationType destinationType,
        // 交换机：String exchange,
        // 路由键String routingKey,
        // 参数：Map<String, Object> arguments
        Binding binding = new Binding(
            "hello-java-queue",
                Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",
                null
        );
        amqpAdmin.declareBinding(binding);
        log.info("Binding has been created");
    }

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void publishMessage() {
        // 2.2 发送消息
        String message = "Hello World";
        // 发送的对象必须支持序列化
        OrderReturnApplyEntity orderReturnApplyEntity = new OrderReturnApplyEntity();
        orderReturnApplyEntity.setOrderId(1L).setCreateTime(new Date()).setReturnName("哈哈");

        // 对象json序列化
        rabbitTemplate.convertAndSend(
                "hello-java-exchange",
                "hello.java",
                orderReturnApplyEntity,
                new CorrelationData(UUID.randomUUID().toString())//消息唯一id，往Broker发送失败时回调取出
        );
        log.info("Message has been send：{}",orderReturnApplyEntity);
    }

}
