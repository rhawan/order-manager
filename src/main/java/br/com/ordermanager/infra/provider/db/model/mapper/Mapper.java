package br.com.ordermanager.infra.provider.db.model.mapper;

import br.com.ordermanager.domain.entities.Order;
import br.com.ordermanager.domain.entities.Product;
import br.com.ordermanager.domain.entities.Client;
import br.com.ordermanager.infra.provider.db.model.OrderModel;
import br.com.ordermanager.infra.provider.db.model.ProductModel;
import br.com.ordermanager.infra.provider.db.model.ClientModel;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    private Mapper() {
        throw new UnsupportedOperationException("Utility Class");
    }

    public static OrderModel toOrderModel(Order order) {
        OrderModel orderModel = OrderModel.builder()
                .id(Long.valueOf(order.id()))
                .client(toClientModel(order.client()))
                .build();

        orderModel.setProducts(order.products().stream()
                .map(product -> {
                    ProductModel productModel = Mapper.toProductModel(product);
                    productModel.setOrder(orderModel);
                    return productModel;
                })
                .collect(Collectors.toList()));

        return orderModel;
    }

    public static Order toOrder(OrderModel orderModel) {
        Client client = new Client(
                orderModel.getClient().getId().toString(),
                orderModel.getClient().getName(),
                orderModel.getClient().getDocument(),
                orderModel.getClient().getEmail(),
                orderModel.getClient().getCellphone()
        );

        List<Product> products = orderModel.getProducts().stream()
                .map(Mapper::toProduct)
                .collect(Collectors.toList());

        return new Order(orderModel.getId().toString(), client, products);
    }

    public static ProductModel toProductModel(Product product) {
        return ProductModel.builder()
                .id(Long.valueOf(product.id()))
                .name(product.name())
                .description(product.description())
                .salesPrice(product.salesPrice())
                .build();
    }

    public static Product toProduct(ProductModel productModel) {
        return new Product(
                productModel.getId().toString(),
                productModel.getName(),
                productModel.getDescription(),
                productModel.getSalesPrice()
        );
    }

    public static ClientModel toClientModel(Client client) {
        return ClientModel.builder()
                .id(Long.valueOf(client.id()))
                .name(client.name())
                .document(client.document())
                .email(client.email())
                .cellphone(client.cellphone())
                .build();
    }

    public static Client toClient(ClientModel clientModel) {
        return new Client(
                clientModel.getId().toString(),
                clientModel.getName(),
                clientModel.getDocument(),
                clientModel.getEmail(),
                clientModel.getCellphone()
        );
    }
}
