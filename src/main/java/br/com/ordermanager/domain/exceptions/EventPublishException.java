package br.com.ordermanager.domain.exceptions;

public class EventPublishException extends RuntimeException {

    public EventPublishException(Throwable throwable) {
        super(throwable.getMessage());
    }
}
