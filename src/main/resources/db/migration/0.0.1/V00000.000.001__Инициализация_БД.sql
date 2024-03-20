CREATE TABLE app_user (
    id bigserial NOT NULL
        CONSTRAINT user_pk PRIMARY KEY,
    login character varying(30) NOT NULL
        CONSTRAINT user_login_unique UNIQUE,
    password character varying(64),
    full_name character varying(127)
);

ALTER TABLE app_user
    OWNER TO postgres;

CREATE TABLE role
(
    id bigserial NOT NULL
        CONSTRAINT role_pk PRIMARY KEY,
    name character varying(30) NOT NULL
        CONSTRAINT role_name_unique UNIQUE
);

ALTER TABLE role
    OWNER to postgres;

CREATE TABLE user_role (
    id bigserial NOT NULL
        CONSTRAINT service_role_pk PRIMARY KEY,
    user_id bigint NOT NULL
        CONSTRAINT user_id_fk REFERENCES app_user (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    role_id bigint NOT NULL
        CONSTRAINT role_id_fk REFERENCES role (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT user_role_unique UNIQUE (user_id, role_id)
);

ALTER TABLE user_role
    OWNER TO postgres;

CREATE TABLE delivery_point (
    id bigserial
        CONSTRAINT delivery_point_pk PRIMARY KEY,
    name character varying(30) NOT NULL
        CONSTRAINT delivery_point_name_unique UNIQUE,
    address character varying(127)
);

ALTER TABLE delivery_point
    OWNER TO postgres;

CREATE TABLE product
(
    id bigserial NOT NULL
        CONSTRAINT product_name_pk PRIMARY KEY,
    name character varying(255) NOT NULL
        CONSTRAINT product_name_unique UNIQUE,
    price integer
);

ALTER TABLE product
    OWNER to postgres;

CREATE TABLE product_order
(
    id bigserial NOT NULL
        CONSTRAINT product_order_pk PRIMARY KEY,
    user_id bigint
        CONSTRAINT user_fk REFERENCES app_user (id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT,
    status character varying(30) NOT NULL,
    delivery_point_id bigint
        CONSTRAINT delivery_point_fk REFERENCES delivery_point (id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT
);

ALTER TABLE product_order
    OWNER to postgres;

CREATE TABLE product_order_history
(
    id bigserial NOT NULL
        CONSTRAINT product_order_history_pk PRIMARY KEY,
    product_order_id bigint
        CONSTRAINT order_fk REFERENCES product_order (id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT,
    status character varying(30),
    datetime timestamp,
    CONSTRAINT order_unique UNIQUE (product_order_id, status)
);

ALTER TABLE product_order_history
    OWNER to postgres;

CREATE TABLE product_order_item
(
    id bigserial NOT NULL
        CONSTRAINT product_order_id_pk PRIMARY KEY,
    order_id bigint NOT NULL
        CONSTRAINT order_fk REFERENCES product_order (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    product_id bigint NOT NULL
        CONSTRAINT product_fk REFERENCES product (id)
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    quantity int,
    CONSTRAINT order_product_unique UNIQUE (order_id, product_id)
);

ALTER TABLE product_order_item
    OWNER to postgres;


INSERT INTO delivery_point (name, address) VALUES
    ('Point 1', 'Город 1, улица 1, дом 1'),
    ('Point 2', 'Город 1, улица 2, дом 2'),
    ('Point 3', 'Город 1, улица 3, дом 3')
ON CONFLICT DO NOTHING;

INSERT INTO product (name, price) VALUES
    ('Товар 1', '1'),
    ('Товар 2', '10'),
    ('Товар 3', '100')
ON CONFLICT DO NOTHING;


INSERT INTO role (name) VALUES
    ('ROLE_USER'),
    ('ROLE_EMPLOYEE')
ON CONFLICT DO NOTHING;

INSERT INTO app_user (login, password, full_name) VALUES
    ('emp1', '$2a$10$JxF7VoYN.D.pSCZX3eLWEeefdSkXuN.ZS9Rx3zv7btEjBWIuZe6QG' , 'Иванов Иван Иванович'),
    ('emp2', '$2a$10$giGyammgsCSz.eZ0oSkhhuxdWP3fknNt3eRwkNULtRqsmnJwtrZau', 'Петров Иван Иванович'),
    ('client1', '$2a$10$JxF7VoYN.D.pSCZX3eLWEeefdSkXuN.ZS9Rx3zv7btEjBWIuZe6QG' , 'Петров Петр Петрович'),
    ('client2', '$2a$10$giGyammgsCSz.eZ0oSkhhuxdWP3fknNt3eRwkNULtRqsmnJwtrZau', 'Иванов Петр Петрович')
ON CONFLICT DO NOTHING;

INSERT INTO user_role (user_id, role_id) VALUES
    (1, 2),
    (2, 2),
    (3, 1),
    (4, 1)
ON CONFLICT DO NOTHING;