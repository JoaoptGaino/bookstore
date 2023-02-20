CREATE TABLE person(
    id uuid DEFAULT uuid_generate_v4(),
    name varchar(255) NOT NULL,
    type VARCHAR(255) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

ALTER TABLE person ADD CONSTRAINT person_id_pk PRIMARY KEY(id);