package br.com.ordermanager.infra.adapters.controller;

import br.com.ordermanager.infra.adapters.dtos.request.CreateOrderRequest;
import br.com.ordermanager.infra.adapters.presentation.OrderPresenter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/api/orders")
public class OrderController {

    private final OrderPresenter orderPresenter;

    public OrderController(OrderPresenter orderPresenter) {
        this.orderPresenter = orderPresenter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createOrder(@RequestHeader(name = "access-token") final String accessToken,
                                      @RequestBody final CreateOrderRequest request) {
        orderPresenter.process(request);
    }
}
