package br.com.ordermanager.infra.adapters.presentation;

import br.com.ordermanager.application.usecase.impl.CreateOrderUseCase;
import br.com.ordermanager.application.usecase.impl.ProcessOrderUseCase;
import br.com.ordermanager.domain.entities.Order;
import br.com.ordermanager.infra.adapters.dtos.request.ClientRequest;
import br.com.ordermanager.infra.adapters.dtos.request.CreateOrderRequest;
import br.com.ordermanager.infra.adapters.dtos.request.EventRequest;
import br.com.ordermanager.infra.adapters.dtos.request.ProductRequest;
import br.com.ordermanager.infra.adapters.producer.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @Mock
    private ProcessOrderUseCase processOrderUseCase;

    @Mock
    private Producer producer;

    @Mock
    private ObjectMapper objectMapper;

    private OrderService orderService;

    private CreateOrderRequest createOrderRequest;
    private Order order;
    private ProcessOrderUseCase.Out processedOrder;
    private CreateOrderUseCase.Out createdOrder;

    @BeforeEach
    public void setUp() {
        ProductRequest product1 = new ProductRequest("prod1", "Product One", "Description of Product One", new BigDecimal("19.99"));
        ProductRequest product2 = new ProductRequest("prod2", "Product Two", "Description of Product Two", new BigDecimal("29.99"));

        ClientRequest clientRequest = new ClientRequest("client1", "John Doe", "123456789", "john.doe@example.com", "1234567890");

        createOrderRequest = new CreateOrderRequest("order1", clientRequest, List.of(product1, product2));

        order = new Order("1", null, null);
        createdOrder = new CreateOrderUseCase.Out(order);
        processedOrder = new ProcessOrderUseCase.Out("1", "client1", 49.98);

        this.orderService = new OrderService(createOrderUseCase, processOrderUseCase, producer, objectMapper);
    }

    @Test
    public void testProcessWhenOrderIsCreatedAndProcessedSuccessfully() throws JsonProcessingException {
        when(objectMapper.convertValue(createOrderRequest, Order.class)).thenReturn(order);
        when(createOrderUseCase.execute(any())).thenReturn(createdOrder);
        when(processOrderUseCase.execute(any())).thenReturn(processedOrder);
        when(objectMapper.writeValueAsString(any())).thenReturn("eventData");

        orderService.process(createOrderRequest);

        verify(createOrderUseCase).execute(any());
        verify(processOrderUseCase).execute(any());
        verify(producer).sendEvent(any(EventRequest.class), eq("eventData"));
    }

    @Test
    public void testProcessWhenJsonProcessingExceptionOccurs() throws JsonProcessingException {
        when(objectMapper.convertValue(createOrderRequest, Order.class)).thenReturn(order);
        when(createOrderUseCase.execute(any())).thenReturn(createdOrder);
        when(processOrderUseCase.execute(any())).thenReturn(processedOrder);
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

        orderService.process(createOrderRequest);

        verify(createOrderUseCase).execute(any());
        verify(processOrderUseCase).execute(any());
        verify(producer, never()).sendEvent(any(EventRequest.class), anyString());
    }

    @Test
    public void testProcessWhenRequestIsNull() {
        orderService.process(null);

        verifyNoInteractions(createOrderUseCase);
        verifyNoInteractions(processOrderUseCase);
        verifyNoInteractions(producer);
    }


}
