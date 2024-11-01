
CREATE TABLE IF NOT EXISTS public.accounts (
    id bigint NOT NULL,
    balance real,
    user_id bigint
);

CREATE TABLE IF NOT EXISTS public.email_data (
    id bigint NOT NULL,
    email character varying(255),
    user_id bigint
);


CREATE TABLE IF NOT EXISTS public.phone_data (
    id bigint NOT NULL,
    phone character varying(255),
    user_id bigint
);



CREATE TABLE IF NOT EXISTS public.user_roles (
    user_id bigint NOT NULL,
    roles character varying(255)
);



CREATE TABLE IF NOT EXISTS public.users (
    id bigint NOT NULL,
    date_of_birth timestamp without time zone,
    name character varying(255),
    password character varying(255)
);



ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.email_data
    ADD CONSTRAINT email_data_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.phone_data
    ADD CONSTRAINT phone_data_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.email_data
    ADD CONSTRAINT fk62aitajoe5fk8s6a7b0830qjd FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.phone_data
    ADD CONSTRAINT fkddj6vjnjncixxkhhjee7dyc2h FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT fknjuop33mo69pd79ctplkck40n FOREIGN KEY (user_id) REFERENCES public.users(id);
