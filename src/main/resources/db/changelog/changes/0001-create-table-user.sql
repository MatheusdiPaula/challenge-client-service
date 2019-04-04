CREATE TABLE tb_user (
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(200) NOT NULL,
   age INT NOT NULL,
   max_temp NUMERIC,
   min_temp NUMERIC,
   version INT NOT NULL,
   CONSTRAINT client_pk PRIMARY KEY(id),
);