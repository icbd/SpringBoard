CREATE TABLE IF NOT EXISTS role
(
    id          bigint    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    creator_id  bigint    NOT NULL,
    title       varchar(255),
    description text,

    created_at  timestamp NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    deleted_at  timestamp                 DEFAULT NULL
);

CREATE INDEX index_role_on_creator_id ON role (creator_id);

CREATE INDEX index_role_on_deleted_at ON role (deleted_at);
