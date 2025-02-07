package br.com.ordermanager.infra.adapters.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(String id,
                                 @NotNull ClientRequest client,
                                 @NotEmpty List<ProductRequest> products) {
}
