import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @description:
 * @author：lzn
 * @date: 2021/11/25
 */
public class KafkaConsumerTest {

    public static void main(String[] args) {

        String topicName = "test8";
        String groupA = "Testtesta";
        String groupB = "Testtestb";

        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", groupA);

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumerA = new KafkaConsumer(props);
//        KafkaConsumer<String, String> consumerA2 = new KafkaConsumer<String, String>(props);
//
//
//        props.put("group.id", groupB);
//        KafkaConsumer<String, String> consumerB = new KafkaConsumer<String, String>(props);
//        KafkaConsumer<String, String> consumerB2 = new KafkaConsumer<String, String>(props);

        consumerA.subscribe(Arrays.asList(topicName));
//        consumerA2.subscribe(Arrays.asList(topicName));
//        consumerB.subscribe(Arrays.asList(topicName));
//        consumerB2.subscribe(Arrays.asList(topicName));

        try{
            while(true) {
                ConsumerRecords<String, String> recordas = consumerA.poll(0);

                for(ConsumerRecord<String, String> record: recordas) {
                    System.out.println("A: " + record.offset() + ", " + record.key() +"," +record.value());
                }

//                ConsumerRecords<String, String> recorda2s = consumerA2.poll(1000);
//
//                for(ConsumerRecord<String, String> record: recorda2s) {
//                    System.out.println("A2: " + record.offset() + ", " + record.key() +"," +record.value());
//                }
//
//                ConsumerRecords<String, String> recordbs = consumerB.poll(1000);
//
//                for(ConsumerRecord<String, String> record: recordbs) {
//                    System.out.println("B: " + record.offset() + ", " + record.key() +"," +record.value());
//                }
//
//                ConsumerRecords<String, String> recordb2s = consumerB2.poll(1000);
//
//                for(ConsumerRecord<String, String> record: recordb2s) {
//                    System.out.println("B2: " + record.offset() + ", " + record.key() +"," +record.value());
//                }
            }
        }catch (Exception e){
            System.out.println(e);
        }


    }
}
