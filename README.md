# Java Kafka Schema Registry Sample

## Local Setup

### Preparation

```shell
docker-compose up
mvn clean install
```

### Run producer

```shell
mvn -pl producer exec:java -Dexec.mainClass=codes.fdk.sample.kafka.App

# Verify via console consumer
docker-compose exec schema-registry sh -c "kafka-protobuf-console-consumer --bootstrap-server kafka:29092 --topic orders --from-beginning"
```
