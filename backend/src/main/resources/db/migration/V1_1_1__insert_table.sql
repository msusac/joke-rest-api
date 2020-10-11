insert into verification_table (token, verified, user_id, date_expiry, date_time_created)
values ('testtwo', false, 3, current_date + 3, now());

insert into user_table (username, password, email, enabled, date_time_created) values
('faileduser','$2y$10$WvB95PkzBTd25B11C1xrMu81adKm.Jvuy5VgV4hcV/b.zWrL8q6L2', 'faileduser@user.com', false, now() - INTERVAL '5 DAY');

insert into verification_table (token, verified, user_id, date_expiry, date_time_created)
values ('failed', false, 4, current_date - 5, now() - INTERVAL '5 DAY');