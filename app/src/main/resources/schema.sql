DROP TABLE IF EXISTS public.users CASCADE;
DROP TABLE IF EXISTS public.authorities CASCADE;
DROP TABLE IF EXISTS public.users_info CASCADE;
DROP TABLE IF EXISTS public.tokens CASCADE;

CREATE TABLE public.users
(
      username                 VARCHAR(30) NOT NULL PRIMARY KEY,
      password                 VARCHAR(120) NOT NULL,
      enabled                  BOOLEAN     NOT NULL,
      last_password_reset_date TIMESTAMP   NOT NULL
);

CREATE TABLE public.authorities (
      id        SERIAL NOT NULL PRIMARY KEY,
      username  VARCHAR(30) NOT NULL,
      authority VARCHAR(30) NOT NULL,
      CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
      CONSTRAINT unique_authorities UNIQUE (username, authority)
);

CREATE TABLE public.users_info (
      info_id        SERIAL NOT NULL PRIMARY KEY,
      username       VARCHAR(30) NOT NULL,
      firstname      VARCHAR(30) NOT NULL,
      lastname       VARCHAR(30) NOT NULL,
      modified_date  TIMESTAMP NOT NULL,
      CONSTRAINT fk_users_info_users FOREIGN KEY (username) REFERENCES users (username)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.tokens (
      id        SERIAL NOT NULL PRIMARY KEY,
      username  VARCHAR(30) NOT NULL,
      token     VARCHAR(1000) NOT NULL,
      enabled   BOOLEAN NOT NULL,
      CONSTRAINT fk_tokens_users FOREIGN KEY (username) REFERENCES users (username)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);