CREATE TABLE IF NOT EXISTS task
(
    `id`           bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`         varchar(255) NOT NULL UNIQUE,
    `listing_id`   bigint       NOT NULL,
    `creator_id`   bigint       NOT NULL,
    `parent_id`    bigint,
    `title`        varchar(255) NOT NULL,
    `description`  text,
    `completed_at` timestamp,

    `created_at`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`   timestamp             DEFAULT NULL
);

CREATE INDEX index_task_on_listing_id ON task (listing_id);

CREATE INDEX index_task_on_creator_id ON task (creator_id);

CREATE INDEX index_task_on_parent_id ON task (parent_id);

CREATE INDEX index_task_on_deleted_at ON task (deleted_at);

CREATE INDEX index_task_on_completed_at ON task (completed_at DESC);
