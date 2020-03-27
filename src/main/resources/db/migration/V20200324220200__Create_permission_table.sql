CREATE TABLE IF NOT EXISTS permission
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`        varchar(255) NOT NULL UNIQUE,
    `creator_id`  bigint       NOT NULL,
    `title`       varchar(255),
    `description` text,

    `created_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`  timestamp             DEFAULT NULL
);

CREATE INDEX index_permission_on_deleted_at ON permission (deleted_at);