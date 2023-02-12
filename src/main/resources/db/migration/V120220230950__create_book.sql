CREATE TABLE book(
   id uuid DEFAULT uuid_generate_v4(),
   title varchar(255) NOT NULL,
   summary varchar(255) NOT NULL,
   author_id uuid NOT NULL,
   created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
   updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
   FOREIGN KEY(author_id) REFERENCES author(id)
);

ALTER TABLE book ADD CONSTRAINT book_id_pk PRIMARY KEY(id);
