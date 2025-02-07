CREATE TABLE itouser (
    id_user serial,
    user_email varchar UNIQUE NOT NULL,
    password int NOT NULL,
    role varchar NOT NULL CHECK ( role in ('ADMIN', 'USER') ),

    CONSTRAINT id_user_pk PRIMARY KEY (id_user)
);