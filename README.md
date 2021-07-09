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
--form 'name="myusers"' \
--form 'partitionsNumber="1"' \
--form 'replicationFactor="1"'
````

Step 3 Use

create user

```
curl --location --request POST 'http://localhost:8181/user' \
--form 'name="hello1"'
```
you can see log or getting allUsers

```
curl --location --request GET 'http://localhost:8181/user' \
--form 'name="hello1"'
```