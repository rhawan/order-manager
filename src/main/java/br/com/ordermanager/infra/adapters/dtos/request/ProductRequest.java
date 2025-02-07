package br.com.ordermanager.infra.adapters.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(@NotNull String id,
                             @NotEmpty String name,
                             @NotEmpty String description,
                             @NotNull BigDecimal salesPrice) {
}
