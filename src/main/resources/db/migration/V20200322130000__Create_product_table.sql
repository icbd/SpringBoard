CREATE TABLE IF NOT EXISTS product
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`        char(36)     NOT NULL UNIQUE DEFAULT (uuid()),
    `creator_id`  bigint       NOT NULL,
    `title`       varchar(255) NOT NULL,
    `description` text,

    `created_at`  timestamp    NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  timestamp    NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`  timestamp                    DEFAULT NULL
);

CREATE INDEX index_product_on_creator_id ON product (creator_id);

CREATE INDEX index_product_on_deleted_at ON product (deleted_at);
