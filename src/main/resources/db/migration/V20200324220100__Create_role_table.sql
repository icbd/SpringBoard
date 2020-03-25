CREATE TABLE IF NOT EXISTS role
(
    `id`          bigint       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `uuid`        varchar(255) NOT NULL UNIQUE,
    `creator_id`  bigint       NOT NULL,
    `parent_id`   bigint,
    `title`       varchar(255),
    `description` text,

    `created_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted_at`  timestamp             DEFAULT NULL
);

CREATE INDEX "index_task_on_creator_id" ON role ("creator_id");

CREATE INDEX "index_task_on_parent_id" ON role ("parent_id");

CREATE INDEX "index_task_on_deleted_at" ON role ("deleted_at");
