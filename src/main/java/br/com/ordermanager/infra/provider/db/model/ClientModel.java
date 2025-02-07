package br.com.ordermanager.infra.provider.db.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clients")
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @SequenceGenerator(name = "client_seq", allocationSize = 1, sequenceName = "client_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "cellphone", nullable = false)
    private String cellphone;

    public ClientModel() {}

    public ClientModel(Long id, String name, String document, String email, String cellphone) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.email = email;
        this.cellphone = cellphone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String document;
        private String email;
        private String cellphone;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder cellphone(String cellphone) {
            this.cellphone = cellphone;
            return this;
        }

        public ClientModel build() {
            return new ClientModel(id, name, document, email, cellphone);
        }
    }

}
