# Order Manager

## Overview

Order Manager is an order management system that allows the creation of orders through a REST endpoint. Once an order is created, it is processed and the data is published to an AWS SQS queue. This project provides a simple and effective way to manage orders and interact with AWS SQS for message queuing.

## Swagger Documentation

To access the Swagger UI for testing and exploring the API:

```
http://localhost:8080/swagger-ui/index.html
```

## Checking Messages in AWS SQS

To verify the messages that are published to the AWS SQS queue, you can use the following AWS CLI command:

```bash
aws sqs receive-message --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/order-queue --endpoint-url=http://localstack:4566 --max-number-of-messages 10 --visibility-timeout 30 --wait-time-seconds 0
```

This command will retrieve messages from the queue to verify the order data that was published.

## Creating an Order via REST Endpoint

You can create a new order by sending a POST request to the following endpoint:

```bash
curl -X 'POST' \
  'http://localhost:8080/v1/api/orders' \
  -H 'accept: */*' \
  -H 'access-token: 123' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "12345",
  "client": {
    "id": "1",
    "name": "John Doe",
    "document": "123.456.789-00",
    "email": "johndoe@example.com",
    "cellphone": "+1234567890"
  },
  "products": [
    {
      "id": "12",
      "name": "Product 1",
      "description": "This is a sample product",
      "salesPrice": 99.99
    },
    {
      "id": "13",
      "name": "Product 2",
      "description": "Another sample product",
      "salesPrice": 49.99
    }
  ]
}'
```

This will create an order with the given details and trigger the process that will eventually publish the message to the SQS queue.

## Example of a Published Message in the Queue

After a successful order creation, the message that is published to the SQS queue will look like the following:

```json
{
    "Messages": [
        {
            "MessageId": "0bfc0e4c-f579-4f38-9d7e-d60c96a7ad8b",
            "ReceiptHandle": "NzQ4Y2M5OTAtYzgyZC00MzgwLWFmY2ItMTgwMDA3ZTkwYTViIGFybjphd3M6c3FzOnVzLWVhc3QtMTowMDAwMDAwMDAwMDA6b3JkZXItcXVldWUgMGJmYzBlNGMtZjU3OS00ZjM4LTlkN2UtZDYwYzk2YTdhZDhiIDE3Mzg5NTc3MzMuMjY0MzE2Mw==",
            "MD5OfBody": "37242ed848c77ab5822a1adf53634e14",
            "Body": "{\"eventId\":\"f0735be8-b3ba-4c22-a8ee-33ef87bbfb12\",\"reference\":\"reference\",\"data\":\"{\\\"eventId\\\":\\\"f0735be8-b3ba-4c22-a8ee-33ef87bbfb12\\\",\\\"reference\\\":\\\"reference\\\",\\\"data\\\":{\\\"orderId\\\":\\\"7\\\",\\\"clientId\\\":\\\"5\\\",\\\"amount\\\":149.98}}\"}"
        }
    ]
}
```

## Running the Project

1. **Set Up Local Environment**: To start the project, run the `docker-compose.yml` file located at the root of the project. This will bring up the necessary services (such as LocalStack for SQS).

2. **Run the Application**: After starting the Docker containers, run the application by executing the main class `br.com.ordermanager.Application` in your IDE or via the command line.

This will start the server and you will be able to interact with the application locally.