create database medcards;

create table doctors
(
    id             bigserial primary key,
    login          varchar not null,
    password       varchar,
    date_оf_birth    date,
    name           varchar not null,
    surname        varchar not null,
    specialization varchar not null,
    phone          varchar not null
);


create table patients
(
    id          bigserial primary key,
    name     varchar,
    surname     varchar,
    date_оf_birth date,
    phone       varchar,
    address     varchar
);

create table records
(
    id          bigserial primary key,
    patient_id     bigint,
    doctor_id     bigint,
    dateOfBirth timestamp,
    constraint records_patient_fkey foreign key (patient_id) references patients (id),
    constraint records_doctor_fkey foreign key (doctor_id) references doctors (id)
);


create table receptions
(
    id bigserial primary key,
    date_reception   timestamp without time zone,
    patient_id    bigint,
    doctor_id     bigint,
    diagnosis     varchar,
    dataReception varchar,
    constraint reception_patient_fkey foreign key (patient_id) references patients (id),
    constraint reception_doctor_fkey foreign key (doctor_id) references doctors (id)
);



