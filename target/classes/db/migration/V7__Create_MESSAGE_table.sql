create table message (

    id int primary key auto_increment,
    user_name varchar(40),
    subject varchar(255),
    content varchar(1000),
    sent_date date,
    is_read bit
);

insert into message(user_name, subject, content, sent_date, is_read)
values ( 'user', 'Test message', 'This is just test message', '2022-04-10', false);
insert into message(user_name, subject, content, sent_date, is_read)
values ( 'user', 'Test message 2','This is just test message', '2022-04-11', false);