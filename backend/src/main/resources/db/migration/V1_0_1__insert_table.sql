insert into user_table (username, password, email, enabled, date_time_created) values
('admin','$2y$10$KALIxGcFpKd2RZ//A.n7zuTHYU5v3ERtzAb8WrUgumQcCFiYP9lUq', 'admin@admin.com', true, '2020-05-01 13:53:20');

insert into user_table (username, password, email, enabled, date_time_created) values
('userone','$2y$10$JIAgfHgF.m46.NxudvmHf.SyW/SfiNv9Xtut3r9C.hqYV3wrg1/Je', 'userone@user.com', true, '2020-05-02 12:53:20');

insert into user_table (username, password, email, enabled, date_time_created) values
('usertwo','$2y$10$WvB95PkzBTd25B11C1xrMu81adKm.Jvuy5VgV4hcV/b.zWrL8q6L2', 'usertwo@user.com', false, now());

insert into authority_table (name) values ('ROLE_ADMIN');
insert into authority_table (name) values ('ROLE_USER');

insert into user_authority_table (user_id, authority_id) values (1, 1);
insert into user_authority_table (user_id, authority_id) values (1, 2);
insert into user_authority_table (user_id, authority_id) values (2, 2);
insert into user_authority_table (user_id, authority_id) values (3, 2);

insert into category_table (name, date_time_created)
values ('Chuck Norris', '2020-05-05 13:52:20');

insert into category_table (name, date_time_created)
values ('Programming', '2020-05-07 11:46:05');

insert into joke_table (description, category_id, date_time_created, user_id)
values ('Time waits for no man. Unless that man is Chuck Norris.', 1, '2020-05-05 13:53:20', 1);

insert into joke_table (description, category_id, date_time_created, user_id)
values ('If you spell Chuck Norris in Scrabble, you win. Forever.', 1, '2020-05-06 18:24:12', 2);

insert into joke_table (description, category_id, date_time_created, user_id)
values ('Programming is a machine that turns coffee into code.', 2, '2020-05-07 11:46:05', 1);

insert into joke_table (description, category_id, date_time_created, user_id)
values ('A Programmer was walking out of door for work, his wife said “while you’re out, buy some milk” and he never came home.', 2, '2020-05-06 18:11:54', 1);

insert into joke_table (description, category_id, date_time_created, user_id)
values ('I took a programming class in high school and I got a C++.', 2, '2020-05-09 20:26:35', 2);