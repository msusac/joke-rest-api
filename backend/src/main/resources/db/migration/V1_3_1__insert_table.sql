insert into comment_table (description, joke_id, user_id, date_time_created)
values ('Good Joke!', 1, 2, now() - INTERVAL '3 DAY');

insert into comment_table (description, parent_id, joke_id, user_id, date_time_created)
values ('Thank You!', 1, 1, 1, now() - INTERVAL '2 DAY');

insert into comment_table (description, parent_id, joke_id, user_id, date_time_created)
values('You are welcome!', 2, 1, 2, now() - INTERVAL '1 DAY');

insert into comment_table (description, joke_id, user_id, date_time_created)
values('That joke made me laugh!', 4, 7, now());