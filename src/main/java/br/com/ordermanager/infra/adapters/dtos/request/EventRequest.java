package br.com.ordermanager.infra.adapters.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EventRequest<T>(
        String eventId,
        String reference,
        @JsonProperty("data") T data) {

    public EventRequest<T> copyData(T data) {
        return new EventRequest<T>(this.eventId,
                this.reference,
                data);
    }
}
