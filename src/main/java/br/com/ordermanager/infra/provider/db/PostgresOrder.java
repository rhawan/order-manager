package br.com.ordermanager.infra.provider.db;

import br.com.ordermanager.application.gateway.OrderGateway;
import br.com.ordermanager.domain.entities.Order;
import br.com.ordermanager.infra.provider.db.model.OrderModel;
import br.com.ordermanager.infra.provider.db.model.mapper.Mapper;
import br.com.ordermanager.infra.provider.db.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostgresOrder implements OrderGateway {

    private final OrderRepository orderRepository;

    public PostgresOrder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(Order order) {
        Optional<OrderModel> existingOrder = orderRepository.findById(Long.valueOf(order.id()));

        if (existingOrder.isPresent()) {
            return Mapper.toOrder(existingOrder.get());
        } else {
            OrderModel orderModel = Mapper.toOrderModel(order);
            OrderModel savedOrderModel = orderRepository.save(orderModel);
            return Mapper.toOrder(savedOrderModel);
        }
    }

}
