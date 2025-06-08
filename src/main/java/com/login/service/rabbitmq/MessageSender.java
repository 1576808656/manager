package com.login.service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendOrder(String order) {
        rabbitTemplate.convertAndSend(
            "worker_manage_exchange",   // 交换机名称
            "worker_manage_routingKey",// 路由键
            order              // 发送的对象
        );
    }
    
}
