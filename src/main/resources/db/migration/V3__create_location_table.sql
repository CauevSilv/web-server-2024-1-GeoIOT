CREATE TABLE location (
    id_location serial,
    name varchar(80) NOT NULL,
    poly polygon,
    CONSTRAINT id_location_pk PRIMARY KEY (id_location)
);