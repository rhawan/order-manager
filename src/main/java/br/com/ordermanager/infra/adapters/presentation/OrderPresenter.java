package br.com.ordermanager.infra.adapters.presentation;

import br.com.ordermanager.infra.adapters.dtos.request.CreateOrderRequest;

public interface OrderPresenter {
    void process(final CreateOrderRequest orderRequest);
}
