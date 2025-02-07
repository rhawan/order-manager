package br.com.ordermanager.application.usecase.impl;

import br.com.ordermanager.application.gateway.OrderGateway;
import br.com.ordermanager.application.usecase.UseCase;
import br.com.ordermanager.domain.entities.Order;

public class CreateOrderUseCase implements UseCase<CreateOrderUseCase.In, CreateOrderUseCase.Out> {

    private final OrderGateway orderGateway;

    public CreateOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public Out execute(final In in) {
        var savedOrder = orderGateway.create(in.order);
        return new Out(savedOrder);
    }

    public record In(Order order) {
    }


    public record Out(Order savedOrder) {
    }
}
