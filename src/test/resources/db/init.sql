-- Criação das sequências para as tabelas
CREATE SEQUENCE order_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE product_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE client_seq
    START WITH 1
    INCREMENT BY 1;

-- Criação da tabela para 'clients'
CREATE TABLE clients (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('client_seq'),
    name VARCHAR(255) NOT NULL,
    document VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cellphone VARCHAR(255) NOT NULL
);

-- Criação da tabela para 'orders'
CREATE TABLE orders (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('order_seq'),
    client_id BIGINT NOT NULL,
    CONSTRAINT fk_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id)
        ON DELETE CASCADE
);

-- Criação da tabela para 'products'
CREATE TABLE products (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('product_seq'),
    order_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    sales_price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE
);
