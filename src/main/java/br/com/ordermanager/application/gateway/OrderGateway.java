package br.com.ordermanager.application.gateway;

import br.com.ordermanager.domain.entities.Order;

public interface OrderGateway {
    Order create(Order order);
}