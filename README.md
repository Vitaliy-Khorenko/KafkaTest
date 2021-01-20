# KafkaTest
KafkaTest


Step 1

Start environment

``docker-compose up -d``


Info

Stop environment

``docker-compose down``


Open the Kafdrop, this is UI for viewing Kafka

``localhost:9000``


http://localhost:9000/topic
http://localhost:9000/v2/api-docs
http://localhost:9000/topic
/topic/{topicName}/{consumerId}

Step 2

Create topic

````
curl --location --request POST 'http://localhost:9000/topic' \
--header 'Accept: application/json' \
--form 'name="firsttopic"' \
--form 'partitionsNumber="1"' \
--form 'replicationFactor="1"'
````


