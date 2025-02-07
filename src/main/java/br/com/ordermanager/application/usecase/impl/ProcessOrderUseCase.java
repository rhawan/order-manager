package br.com.ordermanager.application.usecase.impl;

import br.com.ordermanager.application.usecase.UseCase;
import br.com.ordermanager.domain.entities.Order;
import br.com.ordermanager.domain.entities.Product;

public class ProcessOrderUseCase implements UseCase<ProcessOrderUseCase.In, ProcessOrderUseCase.Out> {

    @Override
    public Out execute(In in) {
        double totalAmount = in.order().products().stream()
                .mapToDouble(product -> product.salesPrice().doubleValue())
                .sum();

        return new Out(in.order().id(), in.order().client().id(), totalAmount);
    }

    public record In(Order order) {
    }

    public record Out(String orderId, String clientId, double amount) {
    }
}
