CREATE TABLE IF NOT EXISTS task
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`        varchar(255) NOT NULL UNIQUE,
    `creator_id`  bigint       NOT NULL,
    `parent_id`   bigint,
    `title`       varchar(255) NOT NULL,
    `description` text,

    `created_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`  timestamp             DEFAULT NULL
);

CREATE INDEX "index_task_on_creator_id" ON task ("creator_id");

CREATE INDEX "index_task_on_parent_id" ON task ("parent_id");
