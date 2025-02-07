package br.com.ordermanager.infra.provider.message;

import br.com.ordermanager.application.gateway.MessageGateway;
import br.com.ordermanager.domain.exceptions.EventRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Service
public class SqsMessageSender implements MessageGateway {

    private static final Logger log = LoggerFactory.getLogger(SqsMessageSender.class);
    private final ObjectMapper objectMapper;
    private final SqsAsyncClient sqsAsyncClient;

    public SqsMessageSender(ObjectMapper objectMapper, SqsAsyncClient sqsAsyncClient) {
        this.objectMapper = objectMapper;
        this.sqsAsyncClient = sqsAsyncClient;
    }

    @Override
    public void send(Object data, String destination) {
        try {
            var body = objectMapper.writeValueAsString(data);
            sqsAsyncClient.sendMessage(builder -> builder
                            .messageBody(body)
                            .queueUrl(destination)
                            .build())
                    .thenAccept(response -> log.info("Message sent with ID {}", response.messageId()))
                    .get();
        } catch (final Exception e) {
            throw new EventRuntimeException(e.getMessage());
        }
    }
}
