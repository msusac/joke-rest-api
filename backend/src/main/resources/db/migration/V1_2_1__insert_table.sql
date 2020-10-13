insert into user_table (username, password, email, enabled, date_time_created) values
('userfour','$2y$10$xPdY8YwbVSLfpdhlD.X3qOX9vaIBcIxvxcFGMyWMJpfjAdzbioeVu', 'four@user.com', true, '2020-10-04 13:53:20');

insert into user_table (username, password, email, enabled, date_time_created) values
('userfive','$2y$10$YJnAWVGfAZtsXq3w12nRg.hcGXkG4Yz7FDk9dh/NsdmzbhRT7vJWe', 'five@user.com', true, '2020-10-05 13:53:20');

insert into user_table (username, password, email, enabled, date_time_created) values
('usersix','$2y$10$tPyF.tPhnyA1evkWPctsmeiyl6IeTTODHMbtGE1qkXrEN7rctWYTy', 'six@user.com', true, '2020-10-06 13:53:20');

insert into user_authority_table (user_id, authority_id) values (4, 2);
insert into user_authority_table (user_id, authority_id) values (5, 2);
insert into user_authority_table (user_id, authority_id) values (6, 2);

insert into rating_table (type, user_id, joke_id, date_time_created) values
('FUNNY', 1, 1, '2020-10-01 14:53:20');
insert into rating_table (type, user_id, joke_id, date_time_created) values
('FUNNY', 2, 1, '2020-10-02 14:53:20');
insert into rating_table (type, user_id, joke_id, date_time_created) values
('LIKED', 3, 1, '2020-10-03 14:53:20');
insert into rating_table (type, user_id, joke_id, date_time_created) values
('NOT_FUNNY', 5, 1, '2020-10-04 14:53:20');
insert into rating_table (type, user_id, joke_id, date_time_created) values
('WOW', 6, 1, '2020-10-05 14:53:20');

insert into rating_table (type, user_id, joke_id, date_time_created) values
('FUNNY', 1, 3, '2020-10-04 14:53:20');
insert into rating_table (type, user_id, joke_id, date_time_created) values
('LIKED', 2, 3, '2020-10-04 14:53:20');
insert into rating_table (type, user_id, joke_id, date_time_created) values
('LIKED', 3, 3, '2020-10-04 14:53:20');
insert into rating_table (type, user_id, joke_id, date_time_created) values
('WOW', 7, 3, '2020-10-04 14:53:20');