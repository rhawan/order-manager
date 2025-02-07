package br.com.ordermanager.application.usecase.impl;

import br.com.ordermanager.application.gateway.MessageGateway;
import br.com.ordermanager.application.usecase.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMessageUseCase implements UseCase<SendMessageUseCase.In, Void> {
    private final static Logger log = LoggerFactory.getLogger(SendMessageUseCase.class);
    private final MessageGateway messageGateway;

    public SendMessageUseCase(MessageGateway messageGateway) {
        this.messageGateway = messageGateway;
    }

    @Override
    public Void execute(final In in) {
        log.info("called send message use case to destination {}", in.destination());
        messageGateway.send(in.value(), in.destination());
        return null;
    }

    public record In(
            String destination,
            Object value) {
    }
}