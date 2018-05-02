DROP TABLE IF EXISTS public.users CASCADE;
DROP TABLE IF EXISTS public.authorities CASCADE;
DROP TABLE IF EXISTS public.users_info CASCADE;
DROP TABLE IF EXISTS public.tokens CASCADE;
DROP TABLE IF EXISTS public.analyse_info CASCADE;
DROP TABLE IF EXISTS public.patient CASCADE;
DROP TABLE IF EXISTS public.analyse_geometric CASCADE;
DROP TABLE IF EXISTS public.vessel CASCADE;

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
      firstName      VARCHAR(30) NOT NULL,
      lastName       VARCHAR(30) NOT NULL,
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

CREATE TABLE public.patient (
      id                    SERIAL NOT NULL PRIMARY KEY,
      firstname             VARCHAR(30) NOT NULL,
      lastname              VARCHAR(30) NOT NULL,
      patronymic            VARCHAR(30),
      email                 VARCHAR(100) NOT NULL,
      phone                 VARCHAR(11) NOT NULL,
      bday                  TIMESTAMP NOT NULL,
      location_address      VARCHAR(100) NOT NULL,
      work_address          VARCHAR(100) NOT NULL,
      policy                VARCHAR(16) NOT NULL,
      comment               VARCHAR(1000)
);

CREATE TABLE public.analyse_info (
      id                    SERIAL NOT NULL PRIMARY KEY,
      username              VARCHAR(30) NOT NULL,
      patient_id            INT NOT NULL,
      name                  VARCHAR(200) NOT NULL,
      short_description     VARCHAR(500) NOT NULL,
      full_description      VARCHAR(1000),
      analyse_type          VARCHAR(200) NOT NULL,
      comment               VARCHAR(1000),
      img                   VARCHAR(400) NOT NULL,
      analyse_date          TIMESTAMP NOT NULL,
      finished           BOOLEAN NOT NULL,
      conclusion            VARCHAR(1000),
      CONSTRAINT fk_analyse_info_users FOREIGN KEY (username) REFERENCES users (username)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
      CONSTRAINT fk_analyse_info_patient FOREIGN KEY (patient_id) REFERENCES patient (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.analyse_geometric (
      id                    SERIAL NOT NULL PRIMARY KEY,
      analyse_info_id       INT NOT NULL,
      original_image        VARCHAR(400) NOT NULL,
      binarized_image       VARCHAR(400) NOT NULL,
      skel_image            VARCHAR(400) NOT NULL,
      CONSTRAINT fk_analyse_geometric_analyse_info FOREIGN KEY (analyse_info_id) REFERENCES patient (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.vessel (
      id                    SERIAL NOT NULL PRIMARY KEY,
      analyse_geometric_id  INT NOT NULL,
      vessel_image          VARCHAR(400) NOT NULL,
      main_vessel_image     VARCHAR(400) NOT NULL,
      tortuosity_degree     REAL NOT NULL,
      count_of_branches     INT NOT NULL,
      branching_degree      REAL NOT NULL,
      area                  REAL NOT NULL,
      area_percent          REAL NOT NULL,
      CONSTRAINT fk_vessel_analyse_geometric FOREIGN KEY (analyse_geometric_id) REFERENCES analyse_geometric (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

