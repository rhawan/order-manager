package br.com.ordermanager.infra.provider.db.model;

import jakarta.persistence.*;

import javax.net.ssl.SSLSession;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", allocationSize = 1, sequenceName = "order_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientModel client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductModel> products;

    public OrderModel() {}

    public OrderModel(Long id, ClientModel client, List<ProductModel> products) {
        this.id = id;
        this.client = client;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public ClientModel getClient() {
        return client;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private ClientModel client;
        private List<ProductModel> products;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder client(ClientModel client) {
            this.client = client;
            return this;
        }

        public Builder products(List<ProductModel> products) {
            this.products = products;
            return this;
        }

        public OrderModel build() {
            return new OrderModel(id, client, products);
        }
    }

}
