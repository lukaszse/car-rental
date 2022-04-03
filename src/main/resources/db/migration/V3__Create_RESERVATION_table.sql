create table reservation (
                id int unsigned auto_increment,
                reservation_date date,
                car_id int not null,
                price decimal not null,
                reservation_name varchar(50) not null,
                reservation_description varchar_casesensitive(500) not null,

                foreign key (car_id) references car (id)
);