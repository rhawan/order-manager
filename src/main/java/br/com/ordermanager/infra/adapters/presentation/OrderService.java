package br.com.ordermanager.infra.adapters.presentation;

import br.com.ordermanager.application.usecase.UseCase;
import br.com.ordermanager.application.usecase.impl.CreateOrderUseCase;
import br.com.ordermanager.application.usecase.impl.ProcessOrderUseCase;
import br.com.ordermanager.domain.entities.Order;
import br.com.ordermanager.infra.adapters.dtos.request.CreateOrderRequest;
import br.com.ordermanager.infra.adapters.dtos.request.EventRequest;
import br.com.ordermanager.infra.adapters.producer.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements OrderPresenter {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final ObjectMapper objectMapper;
    private final UseCase<CreateOrderUseCase.In, CreateOrderUseCase.Out> createOrderUseCase;
    private final UseCase<ProcessOrderUseCase.In, ProcessOrderUseCase.Out> processOrderUseCase;
    private final Producer producer;

    public OrderService(@Qualifier("createOrderUseCase")
                        UseCase<CreateOrderUseCase.In, CreateOrderUseCase.Out> createOrderUseCase,
                        @Qualifier("processOrderUseCase")
                        UseCase<ProcessOrderUseCase.In, ProcessOrderUseCase.Out> processOrderUseCase,
                        Producer producer,
                        ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.producer = producer;
        this.createOrderUseCase = createOrderUseCase;
        this.processOrderUseCase = processOrderUseCase;
    }

    @Override
    public void process(final CreateOrderRequest orderRequest) {
        log.info("process called for event {}", orderRequest);
        Optional.ofNullable(orderRequest)
                .map(request -> objectMapper.convertValue(request, Order.class))
                .map(this::createOrder)
                .map(order -> processOrder(order.savedOrder()))
                .ifPresent(this::sendMessage);
    }

    private CreateOrderUseCase.Out createOrder(Order order) {
        return createOrderUseCase.execute(new CreateOrderUseCase.In(order));
    }

    private ProcessOrderUseCase.Out processOrder(Order order) {
        return processOrderUseCase.execute(new ProcessOrderUseCase.In(order));
    }

    public void sendMessage(ProcessOrderUseCase.Out processedOrder) {
        EventRequest<ProcessOrderUseCase.Out> eventRequest = new EventRequest<>(
                UUID.randomUUID().toString(),
                "reference",
                processedOrder
        );
        String data = null;
        try {
            data = objectMapper.writeValueAsString(eventRequest);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        producer.sendEvent(eventRequest, data);
    }
}
