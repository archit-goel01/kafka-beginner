package com.architgo.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDemoAssignAndSeek {
    private static Logger logger = LoggerFactory.getLogger(ConsumerDemoAssignAndSeek.class.getName());
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        KafkaConsumer<String,String> kafkaConsumer =
                new KafkaConsumer<String, String>(properties);
        //kafkaConsumer.subscribe(Collections.singleton("my_topic"));
        TopicPartition topicPartition =
                new TopicPartition("my-topic",0);
        kafkaConsumer.assign(Arrays.asList(topicPartition));
        kafkaConsumer.seek(topicPartition,15L);
        while(true){
            ConsumerRecords<String,String> consumerRecord =
                    kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String,String> record: consumerRecord) {
                logger.info("Key is : " + record.key()+
                "\n value is : "+record.value()+
                "\n topic is : "+record.topic()+
                "\n offset is : "+record.offset()+
                "\n partition is : "+ record.partition()+
                "\n timestamp is : "+record.timestamp());

            }
        }
    }
}
