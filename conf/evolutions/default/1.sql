-- !Ups

CREATE TABLE IF NOT EXISTS oil_price
(
    id         SERIAL,
    price      float8 NOT NULL,
    price_date date   NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX CONCURRENTLY IF NOT EXISTS price_price_date_uniq
    ON oil_price (price, price_date);
