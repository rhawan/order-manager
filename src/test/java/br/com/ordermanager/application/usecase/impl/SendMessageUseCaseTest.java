package br.com.ordermanager.application.usecase.impl;

import br.com.ordermanager.application.gateway.MessageGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SendMessageUseCaseTest {

    private MessageGateway messageGateway;
    private SendMessageUseCase sendMessageUseCase;

    @BeforeEach
    public void setUp() {
        messageGateway = mock(MessageGateway.class);
        sendMessageUseCase = new SendMessageUseCase(messageGateway);
    }

    @Test
    public void testExecuteWhenCalledWithValidInputThenSendMessageAndReturnNull() {
        String destination = "testDestination";
        Object value = new Object();
        SendMessageUseCase.In input = new SendMessageUseCase.In(destination, value);

        Void result = sendMessageUseCase.execute(input);

        verify(messageGateway).send(value, destination);
        assertThat(result).isNull();
    }
}
