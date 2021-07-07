package com.architgo.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoWithCallbackWithKeys {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // create producer properties
        Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallbackWithKeys.class);
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //create producer

            KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
            //crate producer report
        for(int i=0;i<15;i++) {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>("my_topic","id_"+Integer.toString(i), "Hello WOrld" + Integer.toString(i));
            logger.info("Key is : " + "id_"+i);
            //send date -- async
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    //executes everytime
                    if (e == null) {
                        logger.info("Received new metadata \n" +
                                "Topic : " + recordMetadata.topic() +
                                "\n partitions : " + recordMetadata.partition() +
                                "\n offsets : " + recordMetadata.offset() +
                                "\n timestamp : " + recordMetadata.timestamp());
                    } else {
                        logger.error("Error while producing", e);
                    }
                }
            }).get();//only for testing
        }
            producer.flush();
            producer.close();
    }
}
