
-- CREATE SCHEMA IF NOT EXISTS social_network;
--
-- SET SCHEMA social_network;

DROP TABLE IF EXISTS persons;
DROP TABLE IF EXISTS friends;

CREATE TABLE `ORGANIZATION_USER_MAPPING` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FK_ORGANIZATION_ID` int(11) NOT NULL,
  `FK_USER_DETAILS_ID` int(11) NOT NULL,
   PRIMARY KEY (`ID`)
  ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS roles (
  id          BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name        VARCHAR(50) UNIQUE NOT NULL,
  description VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS persons (
  id         BIGINT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(50)        NOT NULL,
  last_name  VARCHAR(50)        NOT NULL,
  short_name VARCHAR(50),
  email      VARCHAR(50) UNIQUE NOT NULL,
  password   VARCHAR(60)        NOT NULL,
  phone      VARCHAR(15),
  birth_date DATE,
  gender     INT,
  created    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP 
);

CREATE TABLE  user_roles (
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  person_id BIGINT NOT NULL,
  role_id  BIGINT NOT NULL,
  FOREIGN KEY (person_id) REFERENCES persons (id), FOREIGN KEY (role_id) REFERENCES roles (id));

CREATE TABLE  friends (
  friend_id BIGINT PRIMARY KEY,
  person_id BIGINT NOT NULL,
  FOREIGN KEY (person_id) REFERENCES persons (id)
);


CREATE TABLE IF NOT EXISTS messages (
  id           BIGINT  PRIMARY KEY,
  posted       DATETIME NOT NULL,
  sender_id    BIGINT   NOT NULL,
  recipient_id BIGINT   NOT NULL,
  body         VARCHAR(1000)
);

select * from person;

