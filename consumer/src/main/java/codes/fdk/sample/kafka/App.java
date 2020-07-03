package codes.fdk.sample.kafka;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import codes.fdk.sample.kafka.schema.OrderProtos.Order;

public class App {

    private static final Logger LOG = System.getLogger(App.class.getName());

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "orders-consumer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer"
        );
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer"
        );
        props.put("schema.registry.url", "http://localhost:8081");

        KafkaConsumer<String, Order> consumer = new KafkaConsumer<String, Order>(props);
        consumer.subscribe(List.of("orders"));

        try {
            while (true) {
                consumer.poll(Duration.ofMillis(100)).forEach(r -> LOG.log(Level.INFO, r.value()));
            }
        } finally {
            consumer.close();
        }
    }
}
