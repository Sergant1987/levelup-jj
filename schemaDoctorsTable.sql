create database medcards;

create table doctors
(
    id             bigserial primary key,
    login          varchar not null,
    password       varchar,
    dateOfBirth    date,
    name           varchar not null,
    surname        varchar not null,
    specialization varchar not null,
    phone          varchar not null
);


create table patient
(
    id          bigserial primary key,
    name     varchar,
    surname     varchar,
    dateOfBirth date,
    phone       varchar,
    address     varchar
);

create table reception
(
    dateOfBirth   timestamp without time zone,
    patient_id    bigint,
    doctor_id     bigint,
    diagnosis     varchar,
    dataReception varchar,
    primary key (dateOfBirth, patient_id),
    constraint reception_patient_fkey foreign key (patient_id) references patient (id),
    constraint reception_doctor_fkey foreign key (doctor_id) references doctors (id)
);



