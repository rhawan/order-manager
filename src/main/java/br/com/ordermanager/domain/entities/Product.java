package br.com.ordermanager.domain.entities;

import java.math.BigDecimal;

public record Product(String id,
                      String name,
                      String description,
                      BigDecimal salesPrice) {
}
