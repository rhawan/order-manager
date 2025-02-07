package br.com.ordermanager.infra.adapters.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClientRequest(@NotNull String id,
                            @NotEmpty String name,
                            @NotEmpty String document,
                            @Email @Size(min = 7, max = 50) String email,
                            @Size(min = 10, max = 12) String cellphone) {
}
