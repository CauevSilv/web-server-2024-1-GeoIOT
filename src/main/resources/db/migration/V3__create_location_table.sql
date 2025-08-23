CREATE TABLE location (
    id_location serial,
    name varchar(80) NOT NULL,
    poly geometry(Polygon, 4326),
    CONSTRAINT id_location_pk PRIMARY KEY (id_location)
);