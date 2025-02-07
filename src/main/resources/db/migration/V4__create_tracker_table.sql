CREATE TABLE tracker (
    id_tracker serial ,
    id_person int NOT NULL,
    ito_tracker_code VARCHAR(36) UNIQUE NOT NULL,
    created_at DATE DEFAULT now() NOT NULL,
    latitude numeric(11,8) NOT NULL,
    longitude numeric(11,8) NOT NULL,

    CONSTRAINT id_tracker_pk PRIMARY KEY (id_tracker),

    CONSTRAINT tracker_person_fk FOREIGN KEY (id_person)
        REFERENCES person(id_person)
);