CREATE TABLE person (
    id_person serial,
    ito_person_code VARCHAR(36) UNIQUE NOT NULL,
    name VARCHAR(120) NOT NULL,
    device_code VARCHAR(36) NOT NULL,

    CONSTRAINT id_person_pk primary key (id_person)
);