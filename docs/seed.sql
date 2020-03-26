begin;
insert into user (id, uuid, enabled, email, name, password_digest)
values (1, 'uuid0000-0000-0000-0000-user00000001', 1, 'cbd@gmail.com', 'CBD',
        '$2a$10$bXKb5AEJDBHKeAOotCNk8.Mkr3j1Jl8/3UKwOl8aLdasg12Gt7Kz.');

insert into access_token (user_id, token, expired_at)
values (1, 'user1-token', '2038-01-01');

insert into access_token (user_id, token, expired_at)
values (1, 'token-expired', '2012-12-21');
commit;

insert into product (id, uuid, creator_id, title, description)
values (1, 'uuid0000-0000-0000-0000-product00001', 1, 'product A', 'Product details A');

insert into product (id, uuid, creator_id, title, description)
values (2, 'uuid0000-0000-0000-0000-product00002', 1, 'product B', 'Product details B');

insert into listing (id, uuid, project_id, creator_id, title, description)
values (1, 'uuid0000-0000-0000-0000-listing00001', 1, 1, 'Listing A', 'Listing details A');

insert into listing (id, uuid, project_id, creator_id, title, description)
values (2, 'uuid0000-0000-0000-0000-listing00002', 1, 1, 'Listing B', 'Listing details B');

commit;