CREATE TABLE address(
    id uuid DEFAULT uuid_generate_v4(),
    street VARCHAR(255) NOT NULL,
    street_number VARCHAR(10) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    zip_code VARCHAR(25) NOT NULL,
    person_id uuid NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    FOREIGN KEY(person_id) REFERENCES person(id)
);

ALTER TABLE address ADD CONSTRAINT address_id_pk PRIMARY KEY(id);