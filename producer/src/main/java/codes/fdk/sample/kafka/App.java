package codes.fdk.sample.kafka;

import java.util.Properties;
import java.util.UUID;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import codes.fdk.sample.kafka.schema.OrderProtos.Order;

public class App {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("schema.registry.url", "http://localhost:8081");
        props.put("auto.register.schemas", true);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put(
                "value.serializer",
                "io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer"
        );

        KafkaProducer<String, Order> producer = new KafkaProducer<String, Order>(props);

        String orderId = UUID.randomUUID().toString();

        Order order = Order.newBuilder()
                           .setId(orderId)
                           .setProductId("13")
                           .setCustomerId("1337")
                           .setQuantity(4)
                           .build();

        producer.send(new ProducerRecord<String, Order>("orders", orderId, order));

        producer.close();
    }
}
