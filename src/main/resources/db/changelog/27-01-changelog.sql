-- liquibase formatted sql

-- changeset danila:1743060558982-1
CREATE TABLE currency
(
    id                 UUID         NOT NULL,
    name               VARCHAR(255) NOT NULL,
    base_currency      VARCHAR(255) NOT NULL,
    price_change_range VARCHAR(255) NOT NULL,
    description        VARCHAR(255),
    CONSTRAINT pk_currency PRIMARY KEY (id)
);

