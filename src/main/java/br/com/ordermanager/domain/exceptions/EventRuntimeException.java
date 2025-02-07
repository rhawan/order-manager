package br.com.ordermanager.domain.exceptions;

public class EventRuntimeException extends RuntimeException {

    public EventRuntimeException(String message) {
        super(message);
    }
}
