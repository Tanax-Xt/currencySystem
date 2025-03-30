CREATE TABLE currency
(
    id                 UUID         NOT NULL,
    name               VARCHAR(255) NOT NULL UNIQUE,
    base_currency      VARCHAR(255) NOT NULL,
    price_change_range VARCHAR(255) NOT NULL,
    description        VARCHAR(255),
    CONSTRAINT pk_currency PRIMARY KEY (id)
);
