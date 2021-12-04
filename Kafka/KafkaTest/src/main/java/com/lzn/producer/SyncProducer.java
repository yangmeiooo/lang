package com.lzn.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author：lzn
 * @date: 2021/11/24
 */
public class SyncProducer {


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("buffer.memory", 33554432);   //1.
        properties.put("compression.type", "lz4");  //2.
        properties.put("batch.size", 32768);  //3.
        properties.put("linger.ms", 100);  //4.    1234 的配置能提升吞吐量
        properties.put("retries", 10);
        properties.put("retry.backoff.ms", 300);
        properties.put("request.required.acks", "1");



        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>("test8", "pqy is pig");

        // kafka 发送数据有2种方式：
        // 1.异步方式
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    System.out.println("消息发送成功");
                } else {
                    System.out.println("发送失败，请重试");
                }
            }
        });

        Thread.sleep(10*1000);

        // 2.同步发送
//        producer.send(record).get();


        producer.close();

    }
}
