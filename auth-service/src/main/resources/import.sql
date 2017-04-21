
insert into users(id, username, password, login, enabled) values (1,'user','user','skramer@visualsuccess.com',true);
insert into users(id, username, password, login, enabled) values (2,'admin','admin','sherjeel.ghouse@gmail.com',true);

insert into role(id, name) values (1,'ROLE_USER');
insert into role(id, name) values (2,'ROLE_ADMIN');


insert into user_role(user_id, role_id) values (1,1);
insert into user_role(user_id, role_id) values (2,2);

insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('user', 'ROLE_USER');
