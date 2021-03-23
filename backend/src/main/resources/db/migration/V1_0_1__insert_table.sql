insert into category_table (title, date_time_created)
values ('Chuck Norris', NOW() - INTERVAL '7 DAY');

insert into category_table (title, date_time_created)
values ('Mujo', NOW() - INTERVAL '6 DAY');

insert into category_table (title, date_time_created)
values ('Programming', NOW() - INTERVAL '5 DAY');

insert into category_table (title, date_time_created)
values ('School', NOW() - INTERVAL '4 DAY');

insert into joke_table (category_id, description, date_time_created)
values (1, 'Time waits for no man. Unless that man is Chuck Norris.', NOW() - INTERVAL '6 DAY');

insert into joke_table (category_id, description, date_time_created)
values (1, 'If you spell Chuck Norris in Scrabble, you win. Forever.', NOW() - INTERVAL '5 DAY');

insert into joke_table (category_id, description, date_time_created)
values (1, 'Why is Chuck Norris the strongest?\n Because he exercises two days a day!', NOW() - INTERVAL '4 DAY');

insert into joke_table (category_id, description, date_time_created)
values (2, 'Mujo came to the pizzeria and ordered a pizza.\n The waiter asked him, "Do you want me to cut your pizza into 6 or 12 pieces?\n At 6 pieces, there''s no way I''m going to eat 12."', NOW() - INTERVAL '5 DAY');

insert into joke_table (category_id, description, date_time_created)
values (3, 'Programming is a machine that turns coffee into code.', NOW() - INTERVAL '4 DAY');

insert into joke_table (category_id, description, date_time_created)
values (3, 'I took a programming class in high school and I got a C++.', NOW() - INTERVAL '2 DAY');

insert into joke_table (category_id, description, date_time_created)
values (4, 'Two high school girls are saying:\n I don''t have a parent at home this weekend!\n God, how lucky you are! You can study out loud!', NOW() - INTERVAL '1 DAY');