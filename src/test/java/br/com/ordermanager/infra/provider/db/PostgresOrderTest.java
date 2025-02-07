package br.com.ordermanager.infra.provider.db;

import br.com.ordermanager.domain.entities.Client;
import br.com.ordermanager.domain.entities.Order;
import br.com.ordermanager.domain.entities.Product;
import br.com.ordermanager.infra.provider.db.model.ClientModel;
import br.com.ordermanager.infra.provider.db.model.OrderModel;
import br.com.ordermanager.infra.provider.db.model.ProductModel;
import br.com.ordermanager.infra.provider.db.model.mapper.Mapper;
import br.com.ordermanager.infra.provider.db.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostgresOrderTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PostgresOrder postgresOrder;

    private Order order;
    private OrderModel orderModel;

    @BeforeEach
    public void setUp() {
        Client client = new Client("2", "Carl", "123456", "aaa@aaa.com", "11985744512");

        Product product = new Product("1", "Product A", "Description of Product A", new BigDecimal("99.99"));

        order = new Order("1", client, List.of(product));

        ClientModel clientModel = new ClientModel(2L, "Carl", "123456", "aaa@aaa.com", "11985744512");

        orderModel = new OrderModel();
        orderModel.setId(1L);
        orderModel.setClient(clientModel);
        ProductModel productModel = new ProductModel(1L, orderModel, "Product A", "Description of Product A", new BigDecimal("99.99"));
        orderModel.setProducts(List.of(productModel));
    }

    @Test
    public void testCreateWhenOrderDoesNotExistThenSaveAndReturnNewOrder() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        when(orderRepository.save(any(OrderModel.class))).thenReturn(orderModel);

        Order result = postgresOrder.create(order);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(order.getId());
        verify(orderRepository).save(any(OrderModel.class));
    }

    @Test
    public void testCreateWhenOrderExistsThenReturnExistingOrder() {
        when(orderRepository.findById(any())).thenReturn(Optional.of(orderModel));

        Order result = postgresOrder.create(order);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(order.getId());
        verify(orderRepository, never()).save(any(OrderModel.class));
    }

    @Test
    public void testCreateWhenOrderAlreadyExistsThenReturnExistingOrderWithoutSaving() {
        when(orderRepository.findById(any())).thenReturn(Optional.of(orderModel));

        Order result = postgresOrder.create(order);

        assertThat(result).isEqualTo(Mapper.toOrder(orderModel));
        verify(orderRepository, never()).save(any(OrderModel.class));
    }
}