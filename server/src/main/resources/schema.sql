DROP TABLE IF EXISTS public.users CASCADE;
DROP TABLE IF EXISTS public.authorities CASCADE;
DROP TABLE IF EXISTS public.users_info CASCADE;
DROP TABLE IF EXISTS public.tokens CASCADE;
DROP TABLE IF EXISTS public.analyses_info CASCADE;
DROP TABLE IF EXISTS public.patients CASCADE;
DROP TABLE IF EXISTS public.analyses_geometric CASCADE;
DROP TABLE IF EXISTS public.vessels CASCADE;
DROP TABLE IF EXISTS public.analyses_bloodflow CASCADE;
DROP TABLE IF EXISTS public.makulas CASCADE;
DROP TABLE IF EXISTS public.ischemias CASCADE;
DROP TABLE IF EXISTS public.densities CASCADE;

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
      id         SERIAL NOT NULL PRIMARY KEY,
      username   VARCHAR(30) NOT NULL,
      enabled    BOOLEAN NOT NULL,
      os         VARCHAR(255) NOT NULL,
      browser    VARCHAR(255) NOT NULL,
      device     VARCHAR(255) NOT NULL,
      expiration TIMESTAMP,
      CONSTRAINT fk_tokens_users FOREIGN KEY (username) REFERENCES users (username)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.patients (
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

CREATE TABLE public.analyses_info (
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
      finished              BOOLEAN NOT NULL,
      conclusion            VARCHAR(1000),
      CONSTRAINT fk_analyses_info_users FOREIGN KEY (username) REFERENCES users (username)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
      CONSTRAINT fk_analyses_info_patients FOREIGN KEY (patient_id) REFERENCES patients (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.analyses_geometric (
      id                    SERIAL NOT NULL PRIMARY KEY,
      analyse_info_id       INT NOT NULL,
      binarized_image       VARCHAR(400) NOT NULL,
      skel_image            VARCHAR(400) NOT NULL,
      CONSTRAINT fk_analyses_geometric_analyses_info FOREIGN KEY (analyse_info_id) REFERENCES analyses_info (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.vessels (
      id                    SERIAL NOT NULL PRIMARY KEY,
      analyse_geometric_id  INT NOT NULL,
      vessel_image          VARCHAR(400) NOT NULL,
      main_vessel_image     VARCHAR(400) NOT NULL,
      tortuosity_degree     REAL NOT NULL,
      count_of_branches     INT NOT NULL,
      branching_degree      REAL NOT NULL,
      area                  REAL NOT NULL,
      area_percent          REAL NOT NULL,
      CONSTRAINT fk_vessels_analyses_geometric FOREIGN KEY (analyse_geometric_id) REFERENCES analyses_geometric (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.analyses_bloodflow (
      id              SERIAL NOT NULL PRIMARY KEY,
      analyse_info_id INT NOT NULL,
      ishemia_image   VARCHAR(400) NOT NULL,
      density_image   VARCHAR(400) NOT NULL,
      CONSTRAINT fk_analyses_bloodflow_analyses_info FOREIGN KEY (analyse_info_id) REFERENCES analyses_info (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.makulas (
      id                   SERIAL NOT NULL PRIMARY KEY,
      analyse_bloodflow_id INT NOT NULL,
      area                 REAL NOT NULL,
      radius                 REAL NOT NULL,
      x                    REAL NOT NULL,
      y                    REAL NOT NULL,
      CONSTRAINT fk_ishemia_areas_analyses_bloodflow FOREIGN KEY (analyse_bloodflow_id) REFERENCES analyses_bloodflow (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.ischemias (
      id                   SERIAL NOT NULL PRIMARY KEY,
      analyse_bloodflow_id INT NOT NULL,
      area                 REAL NOT NULL,
      zone_number               INT NOT NULL,
      x                    REAL NOT NULL,
      y                    REAL NOT NULL,
      CONSTRAINT fk_ishemia_centers_analyses_bloodflow FOREIGN KEY (analyse_bloodflow_id) REFERENCES analyses_bloodflow (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE TABLE public.densities (
      id                   SERIAL NOT NULL PRIMARY KEY,
      analyse_bloodflow_id INT NOT NULL,
      sector_number        INT NOT NULL,
      density              REAL NOT NULL,
      CONSTRAINT fk_densities_analyses_bloodflow FOREIGN KEY (analyse_bloodflow_id) REFERENCES analyses_bloodflow (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

