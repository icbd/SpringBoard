CREATE TABLE IF NOT EXISTS role_and_permission
(
    id            bigint    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_id       bigint    NOT NULL,
    permission_id bigint    NOT NULL,

    created_at    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted_at    timestamp          DEFAULT NULL
);

CREATE INDEX index_permission_on_deleted_at_role_id__permission_id ON role_and_permission (deleted_at, role_id, permission_id);
