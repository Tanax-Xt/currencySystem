CREATE TABLE currency
(
    id                 UUID         NOT NULL,
    name               VARCHAR(255) NOT NULL,
    base_currency      VARCHAR(255) NOT NULL,
    price_change_range VARCHAR(255) NOT NULL,
    description        VARCHAR(255),
    is_deleted         BOOLEAN DEFAULT false,
    CONSTRAINT pk_currency PRIMARY KEY (id),
    CONSTRAINT uc_currency_basecurrency UNIQUE (base_currency)
);

CREATE UNIQUE INDEX idx_currency_name ON currency (name) WHERE is_deleted = false;
