CREATE TABLE IF NOT EXISTS role_and_permission
(
    id            bigint    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_id       bigint    NOT NULL,
    permission_id bigint    NOT NULL,

    created_at    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX index_role_and_permission_on_role_id_permission_id ON role_and_permission (role_id, permission_id);
