/**
 * 开发团队：
 * 开发团队领导人：
 * 开发人员姓名：陈浩
 * 学号/工号：2019112102
 * 个人/公司邮箱：ch111222@qq.com
 * 时间：2023/3/26 15:34
 * 开发名称：MyRabbitConfig
 * 开发工具：IntelliJ IDEA
 * 当前用户：CH
 * 描述：
 */
package com.atguigu.gulimall.order.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class MyRabbitConfig {
    /**
     * 使用Json序列化对象
     * */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送方ack
     * 定制RabbitTemplate
     * 1、服务器收到消息就回调（publisher-->Broker）
     *      1.1、spring.rabbitmq.publisher-confirms=true
     *      1.2、确认回调  ConfirmCallback
     * 2、消息正确抵达队列回调（Exchange-->Queue）
     *      2.1、spring.rabbitmq.publisher-returns=true 发送端消息抵达队列确认（Exchange-->Queue）
     *           spring.rabbitmq.template.mandatory=true 以异步方式回调 returnConfirm
     * 3、消费端确认（保证每个消息正常处理）
     *      3.1、消费端默认自动确认，队列消息自动移除
     *      配置：spring.rabbitmq.listener.simple.acknowledge-mode=manual
     */
    @PostConstruct
    public void initRabbitTemplate() {
        // 1、服务器收到消息就回调（publisher-->Broker）
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 1、只要消息抵达Broker就ack=true
             * @param correlationData 当前消息的唯一关联数据（消息唯一id）
             * @param ack 消息是否成功收到
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm..correlationData：["+correlationData+"]==>ack：["+ack+"]==>cause：["+cause+"]");
            }
        });

        // 2、消息正确抵达队列回调（Exchange-->Queue）
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 1、只要消息没有抵达指定的队列，出发此失败回调
             * @param message 投递失败的消息，详细信息
             * @param replyCode 回复状态码
             * @param replyText 回复文本内容
             * @param exchange 目标交换机
             * @param routingKey 目标路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Fail Message：["+message+"]==>replyCode：["+replyCode+"]==>replyText：["+replyText+"]==>exchange：["+exchange+"]==>routingKey：["+routingKey+"]");
            }
        });
    }
}