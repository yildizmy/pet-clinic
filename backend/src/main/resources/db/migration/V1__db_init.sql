CREATE SEQUENCE IF NOT EXISTS sequence_person START WITH 1 INCREMENT BY 5;

CREATE SEQUENCE IF NOT EXISTS sequence_pet START WITH 1 INCREMENT BY 5;

CREATE SEQUENCE IF NOT EXISTS sequence_type START WITH 1 INCREMENT BY 5;

CREATE TABLE owner
(
    id         BIGINT      NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL,
    CONSTRAINT pk_owner PRIMARY KEY (id)
);

CREATE TABLE pet
(
    id         BIGINT      NOT NULL,
    name       VARCHAR(50) NOT NULL,
    birth_date date,
    type_id    BIGINT,
    owner_id   BIGINT,
    CONSTRAINT pk_pet PRIMARY KEY (id)
);

CREATE TABLE type
(
    id          BIGINT      NOT NULL,
    name        VARCHAR(50) NOT NULL,
    description VARCHAR(50),
    CONSTRAINT pk_type PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id         BIGINT       NOT NULL,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(50)  NOT NULL,
    username   VARCHAR(50)  NOT NULL,
    password   VARCHAR(120) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE owner
    ADD CONSTRAINT uc_owner_email UNIQUE (email);

ALTER TABLE type
    ADD CONSTRAINT uc_type_name UNIQUE (name);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE pet
    ADD CONSTRAINT fk_pet_on_owner FOREIGN KEY (owner_id) REFERENCES owner (id);

ALTER TABLE pet
    ADD CONSTRAINT fk_pet_on_type FOREIGN KEY (type_id) REFERENCES type (id);