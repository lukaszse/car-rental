create table car (
                id int primary key auto_increment,
                registration_no varchar(255),
                manufacturer varchar(255) not null,
                model varchar(255) not null ,
                fuel_type varchar(255) not null ,
                type varchar(255) not null ,
                engine_capacity int not null ,
                passenger_number int not null ,
                description varchar(255) not null,
                cost_per_day numeric(19,2) not null
);