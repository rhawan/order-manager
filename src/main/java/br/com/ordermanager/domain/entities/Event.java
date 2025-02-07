package br.com.ordermanager.domain.entities;

import java.util.UUID;

public record Event(
        UUID eventId,
        String reference,
        Object data
) {
}
