package com.atguigu.gulimall.order.service.impl;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.atguigu.gulimall.order.entity.OrderReturnApplyEntity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.order.dao.OrderItemDao;
import com.atguigu.gulimall.order.entity.OrderItemEntity;
import com.atguigu.gulimall.order.service.OrderItemService;


@RabbitListener(queues = {"hello-java-queue"})
@Slf4j
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * queues：生命需要间的所有队列
     * <br></br>
     * org.springframework.amqp.core.Message
     * <br></br>
     * 参数类型：
     * 1、Message message：原生消息详细信息。头+体    <br></br>
     * 2、T<发送消息的类型>：实体类                   <br></br>
     * 3、Channel channel：当前传输数据的通道         <br></br>
     * <br></br>
     * Queue：可以很多人都来监听。只要接收到消息，队列删除消息，只能有一个人收到
     * */
//    @RabbitListener(queues = {"hello-java-queue"})
    @RabbitHandler
    public void consumerMessage(Message message,
                                OrderReturnApplyEntity returnApplyEntity,
                                Channel channel) {
        log.info("内容：{}", returnApplyEntity);
        log.info("属性信息：{}", message.getMessageProperties());

        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            if (deliveryTag % 2 == 0) {
                channel.basicAck(deliveryTag, false);
                System.out.println("签收");
            } else {
                // 可批量回退
                /**
                 * long deliveryTag, 消息标签
                 * boolean multiple, 批量
                 * boolean requeue, 是否返回queue【返回队列后有可能会再次发送过来】
                 * */
                channel.basicNack(deliveryTag,false,true);
                // 单条回退
                //channel.basicReject(deliveryTag,true);
                System.out.println("回退");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // 网络中断
        }
    }

    /**
     * 可以更具实体类类型
     * */
    @RabbitHandler
    public void consumerMessage2(Message message,
                                OrderEntity orderEntity,
                                Channel channel) {
        log.info("内容：{}", orderEntity);
        log.info("属性信息：{}", message.getMessageProperties());

    }

}