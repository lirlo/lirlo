package com.lirlo.core.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class ProducerConfiguration {

    @Value("${rocket.producer-group}")
    private String producerGroup;

    @Value("${rocket.namesrv-addr}")
    private String namesvAddr;

    @Value("${rocket.instance-name}")
    private String instanceName;

    @PostConstruct
    public void initRocketMqConfig(){
        log.info("RocketMq:producerGroup{}"+producerGroup);
        log.info("RocketMq:namesvAddr{}"+namesvAddr);
        log.info("RocketMq:instanceName{}"+instanceName);
    }

    @Bean
    public DefaultMQProducer producer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesvAddr);
        producer.setInstanceName(instanceName);
        producer.start();
        log.info("rocketmq producer server is starting....");
        return producer;
    }
}
