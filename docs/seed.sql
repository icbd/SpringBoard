begin;
insert into user (id, uuid, enabled, email, name, password_digest)
values (1, 'uuid0000-0000-0000-0000-user00000001', 1, 'cbd@gmail.com', 'CBD',
        '$2a$10$bXKb5AEJDBHKeAOotCNk8.Mkr3j1Jl8/3UKwOl8aLdasg12Gt7Kz.');

insert into access_token (user_id, token, expired_at)
values (1, 'user1-token', '2038-01-01');

insert into access_token (user_id, token, expired_at)
values (1, 'token-expired', '2012-12-21');

insert into product (id, uuid, creator_id, title, description)
values (1, 'uuid0000-0000-0000-0000-product00001', 1, 'product A', 'Product details A');

insert into product (id, uuid, creator_id, title, description)
values (2, 'uuid0000-0000-0000-0000-product00002', 1, 'product B', 'Product details B');

insert into listing (id, uuid, project_id, creator_id, title, description)
values (1, 'uuid0000-0000-0000-0000-listing00001', 1, 1, 'Listing A', 'Listing details A');

insert into listing (id, uuid, project_id, creator_id, title, description)
values (2, 'uuid0000-0000-0000-0000-listing00002', 1, 1, 'Listing B', 'Listing details B');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (1, 'uuid0000-0000-0000-0000-task00000001', 1, 1, null, 'task-1', 't1');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (2, 'uuid0000-0000-0000-0000-task00000002', 1, 1, null, 'task-2', 't2');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (3, 'uuid0000-0000-0000-0000-task00000003', 1, 1, null, 'task-3', 't3');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (4, 'uuid0000-0000-0000-0000-task00000004', 1, 1, 2, 'task-2-4', 't4');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (5, 'uuid0000-0000-0000-0000-task00000005', 1, 1, 2, 'task-2-5', 't5');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (6, 'uuid0000-0000-0000-0000-task00000006', 1, 1, 2, 'task-2-6', 't6');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (7, 'uuid0000-0000-0000-0000-task00000007', 1, 1, 3, 'task-3-7', 't7');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (8, 'uuid0000-0000-0000-0000-task00000008', 1, 1, 7, 'task-3-7-8', 't8');

insert into task (id, uuid, listing_id, creator_id, parent_id, title, description)
values (9, 'uuid0000-0000-0000-0000-task00000009', 1, 1, 7, 'task-3-7-9', 't9');



commit;