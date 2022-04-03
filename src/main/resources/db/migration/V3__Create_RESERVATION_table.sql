create table reservation (
                id int unsigned auto_increment,
                order_date date,
                car_id int not null,
                price decimal not null,
                order_name varchar(50) not null,
                order_description varchar_casesensitive(500) not null,

                foreign key (car_id) references car (id)
);