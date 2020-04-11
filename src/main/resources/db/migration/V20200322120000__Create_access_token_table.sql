CREATE TABLE IF NOT EXISTS access_token
(
    id         bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    bigint       NOT NULL,
    token      varchar(255) NOT NULL UNIQUE,
    expired_at timestamp    NOT NULL,

    created_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at timestamp             DEFAULT NULL
);
