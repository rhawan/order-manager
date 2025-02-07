package br.com.ordermanager.domain.entities;

public record Client(String id,
                     String name,
                     String document,
                     String email,
                     String cellphone) {
}
