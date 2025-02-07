package br.com.ordermanager.infra.provider.db.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", allocationSize = 1, sequenceName = "product_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderModel order;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "sales_price", nullable = false)
    private BigDecimal salesPrice;

    public ProductModel() {}

    public ProductModel(Long id, OrderModel order, String name, String description, BigDecimal salesPrice) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.description = description;
        this.salesPrice = salesPrice;
    }

    public Long getId() {
        return id;
    }

    public OrderModel getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private OrderModel order;
        private String name;
        private String description;
        private BigDecimal salesPrice;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder order(OrderModel order) {
            this.order = order;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder salesPrice(BigDecimal salesPrice) {
            this.salesPrice = salesPrice;
            return this;
        }

        public ProductModel build() {
            return new ProductModel(id, order, name, description, salesPrice);
        }
    }

}

