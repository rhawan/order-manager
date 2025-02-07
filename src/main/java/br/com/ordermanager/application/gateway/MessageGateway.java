package br.com.ordermanager.application.gateway;

public interface MessageGateway {
    void send(Object data, String destination);
}
