CREATE TABLE IF NOT EXISTS role_and_user
(
    id         bigint    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_id    bigint    NOT NULL,
    user_id    bigint    NOT NULL,

    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX index_role_and_user_on_role_id_and_user_id ON role_and_user (role_id, user_id);
