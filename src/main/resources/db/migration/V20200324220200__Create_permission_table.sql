CREATE TABLE IF NOT EXISTS permission
(
    id          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    source_type varchar(255) NOT NULL,
    source_id   bigint       NOT NULL,
    code        tinyint      NOT NULL,
    description text,

    created_at  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at  timestamp             DEFAULT NULL
);

CREATE INDEX index_permission_on_deleted_at ON permission (deleted_at);
