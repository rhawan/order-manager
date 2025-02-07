package br.com.ordermanager.infra.adapters.producer;

import br.com.ordermanager.application.usecase.UseCase;
import br.com.ordermanager.application.usecase.impl.SendMessageUseCase;
import br.com.ordermanager.domain.entities.Event;
import br.com.ordermanager.domain.exceptions.EventPublishException;
import br.com.ordermanager.infra.adapters.dtos.request.EventRequest;
import br.com.ordermanager.infra.utils.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Supplier;

@Service
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    private final UseCase<SendMessageUseCase.In, Void> sendMessageUseCase;
    private final Environment environment;

    public Producer(UseCase<SendMessageUseCase.In, Void> sendMessageUseCase, Environment environment) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.environment = environment;
    }

    public void sendEvent(final EventRequest eventRequest,
                           final Object data) {


        var queueName = environment.getRequiredProperty("aws.sqs.order-queue");

        log.info("Create supplier with: {} - {}",
                eventRequest.eventId(),
                eventRequest.reference());

        Supplier<Event> eventSupplier = () -> new Event(
                UUID.fromString(eventRequest.eventId()),
                eventRequest.reference(),
                data
        );

        log.info("Sending event: {}", eventRequest.eventId());

        Try.of(eventSupplier)
                .map(event -> sendMessageUseCase.execute(new SendMessageUseCase.In(queueName, event)))
                .getOrElseThrow(EventPublishException::new);
    }
}
