CREATE TABLE IF NOT EXISTS listing
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`        char(36)     NOT NULL UNIQUE DEFAULT (uuid()),
    `project_id`  bigint       NOT NULL,
    `creator_id`  bigint       NOT NULL,
    `title`       varchar(255) NOT NULL,
    `description` text,

    `created_at`  timestamp    NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  timestamp    NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`  timestamp                    DEFAULT NULL
);

CREATE INDEX index_listing_on_project_id ON listing (project_id);

CREATE INDEX index_listing_on_creator_id ON listing (creator_id);

CREATE INDEX index_listing_on_deleted_at ON listing (deleted_at);
