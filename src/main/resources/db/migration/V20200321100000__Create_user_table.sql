CREATE TABLE IF NOT EXISTS user
(
    `id`              bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`            varchar(255) NOT NULL UNIQUE,
    `enabled`         tinyint(1)   NOT NULL DEFAULT '0',
    `email`           varchar(255) NOT NULL UNIQUE,
    `name`            varchar(255)          DEFAULT NULL,
    `password_digest` varchar(255) NOT NULL,

    `created_at`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`      timestamp             DEFAULT NULL
);
