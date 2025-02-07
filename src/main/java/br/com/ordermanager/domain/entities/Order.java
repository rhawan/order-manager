package br.com.ordermanager.domain.entities;

import java.util.List;

public record Order(String id,
                    Client client,
                    List<Product> products) {

    public String getId() {
        return this.id;
    }
}
