create table car (
                id int primary key auto_increment,
                registration_no varchar(10),
                manufacturer varchar(50) not null,
                model varchar(100) not null ,
                fuel_type varchar(50) not null ,
                type varchar(50) not null ,
                engine_capacity int not null ,
                passenger_number int not null ,
                description varchar(200) not null,
                cost_per_day numeric(19,2) not null
);