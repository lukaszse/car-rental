create table car_reservation (
    id int primary key auto_increment,
    user_name varchar(255) not null,
    car_id int not null,
    reservation_date date,
    date_from date,
    date_to date,
    total_cost decimal not null,
    invoice_id varchar(255),
    rented bit
);