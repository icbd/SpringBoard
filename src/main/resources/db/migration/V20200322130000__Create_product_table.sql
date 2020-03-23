CREATE TABLE IF NOT EXISTS product
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`        varchar(255) NOT NULL UNIQUE,
    `creator`     bigint       NOT NULL,
    `title`       varchar(255) NOT NULL,
    `description` text,

    `created_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
