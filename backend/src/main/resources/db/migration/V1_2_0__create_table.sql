create table rating_table(
    id integer generated by default as identity primary key,
    type varchar(50) not null,
    user_id integer not null,
    joke_id integer not null,
    date_time_created timestamp not null,
    date_time_updated timestamp,
    constraint fk_user foreign key (user_id) references user_table,
    constraint fk_joke foreign key (joke_id) references joke_table
);

alter sequence rating_table_id_seq RESTART WITH 1;