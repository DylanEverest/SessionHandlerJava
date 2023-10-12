create database javasession;
\c javasession

create table sessions (
    sessionsIDpk serial primary key,
    cryptedSessionID varchar(255) ,
    key varchar(255) not null,
    value varchar(255) 
);
