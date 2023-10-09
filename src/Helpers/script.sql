create database EasyBank;
drop table "person" ;
create table person (
                        id serial primary key ,
                        firstName varchar(50),
                        lastNAme varchar(50),
                        dateOfBirth date,
                        phoneNumber varchar(10)
);
CREATE TABLE "client" (
                          id int references  person(id) on delete cascade on update cascade primary key ,
                          code varchar(100) unique ,
                          adress varchar(100)
);
create table "employe" (
                           id int references person(id) primary key on delete cascade on update cascade,
                           registrationNumber varchar(100) unique ,
                           recrutmentDate date,
                           email varchar(100)
);
create type "status" as enum ('active','inactive');
create table "account" (
                           accountNumber varchar(100) primary key ,
                           balance double precision ,
                           creationDate date,
                           client_code varchar(100) references client(code) on delete cascade on update cascade ,
                           status status
);
create table "currentAccount" (
                                  id varchar(100) references account(accountNumber) on delete cascade on update cascade primary key ,
                                  maxPrice double precision
);
create table "SavingAccount" (
                                 interestRate double precision,
                                 id varchar(100) references account(accountNumber) on delete cascade on update cascade primary key
);
create type "operationType" as enum ('payment','withdrawal');
select * from mission;
select * from account;
create table "operation" (
                             operationNumber serial primary key ,
                             date date ,
                             type "operationType",
                             price double precision,
                             accountNumber varchar(100) references account(accountNumber) on delete cascade on update cascade ,
                             registrationNumber varchar(100) references employe(registrationNumber) on delete cascade on update cascade
);
create table "mission" (
                           code varchar(100) primary key ,
                           name varchar(100),
                           description varchar(500)
);
create table "affectation" (
                               startDate date,
                               endDate date,
                               mission_code varchar(100) references mission(code) on delete cascade on update cascade ,
                               emloye_registrationNumber varchar(100) references employe(registrationNumber) on delete cascade on update cascade
);
create table employeAffectation (
                               creationDate date,
                               agence_code varchar(100) references agence(code) on delete cascade on update cascade ,
                               employe_registrationNumber varchar(100) references employe(registrationNumber) on delete cascade on update cascade
);