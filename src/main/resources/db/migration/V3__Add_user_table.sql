create table app_user (
    user_name  varchar(40) primary key,
    first_name varchar(255),
    last_name varchar(255),
    user_role varchar(255),
    password varchar(500)
);

insert into app_user (user_name, first_name, last_name, user_role, password)
values ('admin', 'John', 'Doe', 'ADMIN', '{bcrypt}$2a$12$rvC3VtPFdeubVO0pOeMBa.yYdygRBm2NT.lK7y4PC2FI4Mu88FLIK');

insert into app_user (user_name, first_name, last_name, user_role, password)
values ('user', 'James', 'Smith' ,'USER' , '{bcrypt}$2a$12$feoSS.Dx/rRdQWfWHeWYZu8txsYcy8Dxt89MWd9U3O8r4CaAKKY3S');

insert into app_user (user_name, first_name, last_name, user_role, password)
values ('manager', 'James', 'Smith' ,'MANAGER' , '{bcrypt}$2a$12$feoSS.Dx/rRdQWfWHeWYZu8txsYcy8Dxt89MWd9U3O8r4CaAKKY3S');