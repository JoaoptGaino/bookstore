CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE author(
   id uuid DEFAULT uuid_generate_v4(),
   name varchar(255) NOT NULL,
   created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
   updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
ALTER TABLE author ADD CONSTRAINT author_id_pk PRIMARY KEY(id);