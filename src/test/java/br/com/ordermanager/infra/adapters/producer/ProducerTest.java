package br.com.ordermanager.infra.adapters.producer;

import br.com.ordermanager.application.usecase.UseCase;
import br.com.ordermanager.application.usecase.impl.SendMessageUseCase;
import br.com.ordermanager.domain.exceptions.EventPublishException;
import br.com.ordermanager.infra.adapters.dtos.request.EventRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProducerTest {

    @Mock
    private UseCase<SendMessageUseCase.In, Void> sendMessageUseCase;

    @Mock
    private Environment environment;

    @InjectMocks
    private Producer producer;

    @BeforeEach
    public void setUp() {
        when(environment.getRequiredProperty("aws.sqs.order-queue")).thenReturn("test-queue");
    }

    @Test
    public void givenEventRequestWhenValidEventRequestThenSendEventSuccess() {

        EventRequest eventRequest = new EventRequest("123e4567-e89b-12d3-a456-426614174000",
                "reference", new String());
        Object data = new Object();

        producer.sendEvent(eventRequest, data);

        verify(sendMessageUseCase, times(1)).execute(any(SendMessageUseCase.In.class));
    }

    @Test
    public void givenEventRequestWhenInvalidEventRequestThenSendEventError() {

        EventRequest eventRequest = new EventRequest("invalid-uuid","EVENT", new String());
        Object data = new Object();

        EventPublishException eventPublishException = assertThrows(EventPublishException.class, () ->
                producer.sendEvent(eventRequest, data));

        assertNotNull(eventPublishException.getMessage());

        verify(sendMessageUseCase, never()).execute(any(SendMessageUseCase.In.class));
    }
}
