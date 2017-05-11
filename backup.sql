-- MySQL dump 10.16  Distrib 10.1.22-MariaDB, for osx10.12 (x86_64)
--
-- Host: localhost    Database: Cinema
-- ------------------------------------------------------
-- Server version	10.1.22-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Current Database: `Cinema`
--

CREATE DATABASE /*!32312 IF NOT EXISTS */ `Cinema` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `Cinema`;

--
-- Table structure for table `Cinema`
--

DROP TABLE IF EXISTS `Cinema`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cinema` (
  `number_cinema` INT(11)      NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(50)  NOT NULL,
  `address`       VARCHAR(256) NOT NULL,
  PRIMARY KEY (`number_cinema`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cinema`
--

LOCK TABLES `Cinema` WRITE;
/*!40000 ALTER TABLE `Cinema`
  DISABLE KEYS */;
INSERT INTO `Cinema` VALUES (1, 'Красный кирпич', 'ул. Кирпичная дом 3'), (2, 'лол', 'ул.шипиловская');
/*!40000 ALTER TABLE `Cinema`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onInsertCinema
AFTER INSERT
  ON Cinema
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("Added a new value Number cinema «", NEW.number_cinema));
    SET message = (SELECT CONCAT(message, "», Name: «"));
    SET message = (SELECT CONCAT(message, NEW.name));
    SET message = (SELECT CONCAT(message, "», Address: «"));
    SET message = (SELECT CONCAT(message, NEW.Address));
    SET message = (SELECT CONCAT(message, "» in the table Cinema"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);
  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onUpdateCinema
AFTER UPDATE
  ON Cinema
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("The following values were updated Number cinema «", NEW.number_cinema));
    SET message = (SELECT CONCAT(message, "», Name: «"));
    SET message = (SELECT CONCAT(message, NEW.name));
    SET message = (SELECT CONCAT(message, "», Address: «"));
    SET message = (SELECT CONCAT(message, NEW.Address));
    SET message = (SELECT CONCAT(message, "» in the Cinema table"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onDeleteCinema
AFTER DELETE
  ON Cinema
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("A value «", OLD.name));
    SET message = (SELECT CONCAT(message, "»"));
    SET message = (SELECT CONCAT(message, " of the Cinema has been removed"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;

--
-- Table structure for table `Country`
--

DROP TABLE IF EXISTS `Country`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Country` (
  `name`           VARCHAR(50) NOT NULL,
  `number_country` INT(11)     NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`number_country`),
  UNIQUE KEY `name` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Country`
--

LOCK TABLES `Country` WRITE;
/*!40000 ALTER TABLE `Country`
  DISABLE KEYS */;
INSERT INTO `Country` VALUES ('Россия', 1);
/*!40000 ALTER TABLE `Country`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onInsertCountry
AFTER INSERT
  ON Country
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("Added a new value Name: «", NEW.name));
    SET message = (SELECT CONCAT(message, "», Number country: «"));
    SET message = (SELECT CONCAT(message, NEW.number_country));
    SET message = (SELECT CONCAT(message, "» to the table Country"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);
  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onUpdateCountry
AFTER UPDATE
  ON Country
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("The following values were updated Name: «", NEW.name));
    SET message = (SELECT CONCAT(message, "», Number country: «"));
    SET message = (SELECT CONCAT(message, NEW.number_country));
    SET message = (SELECT CONCAT(message, "» to the Country table"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onDeleteCountry
AFTER DELETE
  ON Country
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("A value «", OLD.name));
    SET message = (SELECT CONCAT(message, "»"));
    SET message = (SELECT CONCAT(message, " of the Country has been removed"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;

--
-- Table structure for table `Film`
--

DROP TABLE IF EXISTS `Film`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Film` (
  `number_film`    INT(11)     NOT NULL AUTO_INCREMENT,
  `name`           VARCHAR(50) NOT NULL,
  `duration`       INT(11)     NOT NULL,
  `number_country` INT(11)              DEFAULT NULL,
  PRIMARY KEY (`number_film`),
  KEY `R_1` (`number_country`),
  CONSTRAINT `R_1` FOREIGN KEY (`number_country`) REFERENCES `Country` (`number_country`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 13
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Film`
--

LOCK TABLES `Film` WRITE;
/*!40000 ALTER TABLE `Film`
  DISABLE KEYS */;
INSERT INTO `Film` VALUES (12, '2', 23, 1);
/*!40000 ALTER TABLE `Film`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onInsertFilm
AFTER INSERT
  ON Film
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("Added a new value Number film: «", NEW.number_film));
    SET message = (SELECT CONCAT(message, "», Name: «"));
    SET message = (SELECT CONCAT(message, NEW.name));
    SET message = (SELECT CONCAT(message, "», Duration: «"));
    SET message = (SELECT CONCAT(message, NEW.duration));
    SET message = (SELECT CONCAT(message, "», Number country: «"));
    SET message = (SELECT CONCAT(message, NEW.number_country));
    SET message = (SELECT CONCAT(message, "», to the table Film"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);
  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onUpdateFilm
AFTER UPDATE
  ON Film
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("The following values were updated Number film: «", NEW.number_film));
    SET message = (SELECT CONCAT(message, "», Name: «"));
    SET message = (SELECT CONCAT(message, NEW.name));
    SET message = (SELECT CONCAT(message, "», Duration: «"));
    SET message = (SELECT CONCAT(message, NEW.duration));
    SET message = (SELECT CONCAT(message, "», Number country: «"));
    SET message = (SELECT CONCAT(message, NEW.number_country));
    SET message = (SELECT CONCAT(message, "», in the Film table"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onDeleteFilm
AFTER DELETE
  ON Film
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("A value «", OLD.name));
    SET message = (SELECT CONCAT(message, "»"));
    SET message = (SELECT CONCAT(message, " of the Film has been removed"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;

--
-- Table structure for table `Session`
--

DROP TABLE IF EXISTS `Session`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Session` (
  `number_session` INT(11)  NOT NULL AUTO_INCREMENT,
  `number_room`    INT(11)  NOT NULL,
  `number_film`    INT(11)  NOT NULL,
  `number_cinema`  INT(11)  NOT NULL,
  `number_type`    INT(11)  NOT NULL,
  `Date`           DATETIME NOT NULL,
  PRIMARY KEY (`number_session`),
  KEY `R_2` (`number_film`),
  KEY `R_3` (`number_type`),
  KEY `R_4` (`number_cinema`),
  CONSTRAINT `R_2` FOREIGN KEY (`number_film`) REFERENCES `Film` (`number_film`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `R_3` FOREIGN KEY (`number_type`) REFERENCES `Type_Session` (`number_type`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `R_4` FOREIGN KEY (`number_cinema`) REFERENCES `Cinema` (`number_cinema`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Session`
--

LOCK TABLES `Session` WRITE;
/*!40000 ALTER TABLE `Session`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `Session`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onInsertSession
AFTER INSERT
  ON Session
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("Added a new value Number session: «", NEW.number_session));
    SET message = (SELECT CONCAT(message, "», Number room: «"));
    SET message = (SELECT CONCAT(message, NEW.number_room));
    SET message = (SELECT CONCAT(message, "», Number film: «"));
    SET message = (SELECT CONCAT(message, NEW.number_film));
    SET message = (SELECT CONCAT(message, "», Number cinema: «"));
    SET message = (SELECT CONCAT(message, NEW.number_cinema));
    SET message = (SELECT CONCAT(message, "», Date: «"));
    SET message = (SELECT CONCAT(message, NEW.date));
    SET message = (SELECT CONCAT(message, "», Number type: «"));
    SET message = (SELECT CONCAT(message, NEW.number_type));
    SET message = (SELECT CONCAT(message, "» to the table Session"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);
  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onUpdateSession
AFTER UPDATE
  ON Session
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("The following values were updated Number session: «", NEW.number_session));
    SET message = (SELECT CONCAT(message, "», Number room: «"));
    SET message = (SELECT CONCAT(message, NEW.number_room));
    SET message = (SELECT CONCAT(message, "», Number film: «"));
    SET message = (SELECT CONCAT(message, NEW.number_film));
    SET message = (SELECT CONCAT(message, "», Number cinema: «"));
    SET message = (SELECT CONCAT(message, NEW.number_cinema));
    SET message = (SELECT CONCAT(message, "», Date: «"));
    SET message = (SELECT CONCAT(message, NEW.date));
    SET message = (SELECT CONCAT(message, "», Number type: «"));
    SET message = (SELECT CONCAT(message, NEW.number_type));
    SET message = (SELECT CONCAT(message, "» in the Session table"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onDeleteSession
AFTER DELETE
  ON Session
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("A value «", OLD.number_session));
    SET message = (SELECT CONCAT(message, "»"));
    SET message = (SELECT CONCAT(message, " of the Session has been removed"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;

--
-- Table structure for table `Type_Session`
--

DROP TABLE IF EXISTS `Type_Session`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Type_Session` (
  `number_type` INT(11)     NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50) NOT NULL,
  PRIMARY KEY (`number_type`),
  UNIQUE KEY `name` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Type_Session`
--

LOCK TABLES `Type_Session` WRITE;
/*!40000 ALTER TABLE `Type_Session`
  DISABLE KEYS */;
INSERT INTO `Type_Session` VALUES (2, '2D'), (3, '3D'), (1, 'IMAX');
/*!40000 ALTER TABLE `Type_Session`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onInsertTypeSession
AFTER INSERT
  ON Type_Session
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("Added a new value Number type", NEW.number_type));
    SET message = (SELECT CONCAT(message, "», Name: «"));
    SET message = (SELECT CONCAT(message, NEW.name));
    SET message = (SELECT CONCAT(message, "» to the table Type_Session"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);
  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onUpdateTypeSession
AFTER UPDATE
  ON Type_Session
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("The following values were updated Number type", NEW.number_type));
    SET message = (SELECT CONCAT(message, "», Name: «"));
    SET message = (SELECT CONCAT(message, NEW.name));
    SET message = (SELECT CONCAT(message, "» in the Type_Session table"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
/*!50003 CREATE */ /*!50017 DEFINER =`root`@`localhost` */ /*!50003 TRIGGER onDeleteTypeSession
AFTER DELETE
  ON Type_Session
FOR EACH ROW
  BEGIN
    DECLARE message VARCHAR(300);
    SET message = (SELECT CONCAT("A value «", OLD.name));
    SET message = (SELECT CONCAT(message, "»"));
    SET message = (SELECT CONCAT(message, " of the Type_Session has been removed"));
    INSERT INTO logs (date, сontractor, message) VALUES (NOW(), SESSION_USER(), message);

  END */;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logs` (
  `id`         INT(11) NOT NULL AUTO_INCREMENT,
  `date`       DATETIME         DEFAULT NULL,
  `сontractor` VARCHAR(150)     DEFAULT NULL,
  `message`    VARCHAR(1024)    DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 45
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logs`
--

LOCK TABLES `logs` WRITE;
/*!40000 ALTER TABLE `logs`
  DISABLE KEYS */;
INSERT INTO `logs` VALUES (1, '2017-04-24 11:34:03', 'root@localhost', ': delete country «США»'),
  (2, '2017-04-24 11:35:13', 'root@localhost', 'Insert country «США»'),
  (3, '2017-04-24 11:59:04', 'root@localhost', 'The value in Country updated from «США» to «США2»'),
  (4, '2017-04-24 12:00:00', 'root@localhost', 'The value in Country updated from «США2» to «США»'),
  (5, '2017-04-24 12:04:54', 'root@localhost', 'The value in Session updated from «2» to «7»'),
  (6, '2017-04-24 13:04:32', 'root@localhost', 'A value «США» of the Country has been removed'),
  (7, '2017-04-24 13:11:52', 'root@localhost', 'Added a new value «Alex» to the table Cinema'),
  (8, '2017-04-24 13:11:58', 'root@localhost', 'Added a new value «3» to the table Film'),
  (9, '2017-04-24 13:12:01', 'root@localhost', 'A value «3» of the Film has been removed'),
  (10, '2017-04-24 13:12:04', 'root@localhost', 'A value «7» of the Session has been removed'),
  (11, '2017-04-24 13:12:06', 'root@localhost', 'A value «1» of the Session has been removed'),
  (12, '2017-04-24 13:12:10', 'root@localhost', 'A value «5D» of the Type_Session has been removed'),
  (13, '2017-04-24 13:18:47', 'root@localhost', 'The value in Type_Session updated from «-\\(-_-)/-» to «-\\(-_-)/-»'),
  (14, '2017-04-24 15:59:43', NULL, '0'),
  (15, '2017-04-24 16:05:14', 'root@localhost', 'Added a new value «7», «213», «213», «7», to the table Film'),
  (16, '2017-04-24 16:08:55', 'root@localhost', 'Added a new value Number film: «8», Name: «231», Duration: «123», Number country«1», to the table Film'),
  (17, '2017-04-24 16:11:24', 'root@localhost', 'Added a new value Number film: «9», Name: «2», Duration: «3», Number country«1», to the table Film'),
  (18, '2017-04-24 16:19:51', 'root@localhost', 'Added a new value «23» to the table Country'),
  (19, '2017-04-24 16:19:53', 'root@localhost', 'A value «23» of the Country has been removed'),
  (20, '2017-04-24 16:20:08', 'root@localhost', 'Added a new value «123» to the table Country'),
  (21, '2017-04-24 16:20:11', 'Alex@localhost', 'A value «123» of the Country has been removed'),
  (22, '2017-04-24 16:28:51', 'root@localhost', 'Added a new value Number film: «10», Name: «123», Duration: «123», Number country«1», to the table Film'),
  (23, '2017-04-24 16:34:38', 'root@localhost', 'Added a new value Number film: «11», Name: «23», Duration: «23», Number country: «1», to the table Film'),
  (24, '2017-04-24 16:34:50', 'root@localhost', 'Added a new value Number session: «4», Number room: «23», Number film: «3», Number cinema: «1», Date: «2017-04-25 09:09:00», Number type: «2» to the table Session'),
  (25, '2017-05-02 12:54:05', 'root@localhost', 'The following values were updated Name: «РоссияФ», Number country: «1» to the Country table'),
  (26, '2017-05-04 14:21:06', 'root@localhost', 'A value «Alex» of the Cinema has been removed'),
  (27, '2017-05-04 14:21:27', 'root@localhost', 'The following values were updated Name: «Россия», Number country: «1» to the Country table'),
  (28, '2017-05-04 14:21:54', 'root@localhost', 'Added a new value Number type7», Name: «» to the table Type_Session'),
  (29, '2017-05-04 14:22:05', 'root@localhost', 'A value «» of the Type_Session has been removed'),
  (30, '2017-05-04 14:22:12', 'root@localhost', 'Added a new value Number type10», Name: «» to the table Type_Session'),
  (31, '2017-05-04 14:22:22', 'root@localhost', 'A value «» of the Type_Session has been removed'),
  (32, '2017-05-04 14:35:18', 'root@localhost', 'The following values were updated Number film: «3», Name: «=-)», Duration: «123», Number country: «1», in the Film table'),
  (33, '2017-05-04 14:37:58', 'root@localhost', 'The following values were updated Number film: «3», Name: «=-)», Duration: «123», Number country: «1», in the Film table'),
  (34, '2017-05-04 14:43:03', 'root@localhost', 'A value «-\\(-_-)/-» of the Type_Session has been removed'),
  (35, '2017-05-04 14:43:08', 'root@localhost', 'A value «(----___----)» of the Cinema has been removed'),
  (36, '2017-05-04 14:43:24', 'root@localhost', 'A value «=-)» of the Film has been removed'),
  (37, '2017-05-04 14:43:27', 'root@localhost', 'A value «2» of the Film has been removed'),
  (38, '2017-05-04 14:43:29', 'root@localhost', 'A value «123» of the Film has been removed'),
  (39, '2017-05-04 14:43:31', 'root@localhost', 'A value «231» of the Film has been removed'),
  (40, '2017-05-04 14:43:33', 'root@localhost', 'A value «213» of the Film has been removed'),
  (41, '2017-05-04 14:43:35', 'root@localhost', 'A value «12» of the Film has been removed'),
  (42, '2017-05-04 14:43:37', 'root@localhost', 'A value «23» of the Film has been removed'),
  (43, '2017-05-04 14:43:38', 'root@localhost', 'A value «Пупок 3» of the Film has been removed'),
  (44, '2017-05-04 14:46:32', 'root@localhost',
   'Added a new value Number film: «12», Name: «2», Duration: «23», Number country: «1», to the table Film');
/*!40000 ALTER TABLE `logs`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'Cinema'
--
/*!50003 DROP PROCEDURE IF EXISTS `addCinema` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `addCinema`(nC VARCHAR(20), Address VARCHAR(100))
  SQL SECURITY INVOKER
  COMMENT 'Insert Cinema'
  BEGIN
    INSERT Cinema (name, address) VALUES (Nc, Address);
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `addCountry` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `addCountry`(nC VARCHAR(20))
  SQL SECURITY INVOKER
  COMMENT 'Insert Country'
  BEGIN
    INSERT Country (name) VALUES (Nc);
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `addFilm` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `addFilm`(nF VARCHAR(20), Dur INT, nC INT)
  SQL SECURITY INVOKER
  COMMENT 'Insert Film'
  BEGIN
    INSERT INTO Film (name, duration, number_country) VALUES (nF, Dur, nC);
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `addSession` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `addSession`(nT INT, nC INT, nF INT, d DATETIME, nR INT)
  SQL SECURITY INVOKER
  COMMENT 'Insert Session'
  BEGIN
    INSERT INTO Session (number_type, Number_cinema, number_film, date, number_room) VALUES (nT, nC, nF, d, nR);
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `addTypeSession` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `addTypeSession`(nTS VARCHAR(20))
  SQL SECURITY INVOKER
  COMMENT 'Insert Type_Session'
  BEGIN
    INSERT INTO Type_Session (name) VALUES (nTS);
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `getCinema` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `getCinema`()
  SQL SECURITY INVOKER
  COMMENT 'Select Cinema'
  BEGIN
    SELECT *
    FROM Cinema;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `getCountry` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `getCountry`()
  SQL SECURITY INVOKER
  COMMENT 'Select Country'
  BEGIN
    SELECT *
    FROM Country;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `getFilm` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `getFilm`()
  SQL SECURITY INVOKER
  COMMENT 'Select Film'
  BEGIN
    SELECT *
    FROM Film;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `getSession` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `getSession`()
  SQL SECURITY INVOKER
  COMMENT 'Select Session'
  BEGIN
    SELECT *
    FROM Session;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `getTypeSession` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `getTypeSession`()
  SQL SECURITY INVOKER
  COMMENT 'Select Type_Session'
  BEGIN
    SELECT *
    FROM Type_Session;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `getUsers` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `getUsers`()
  SQL SECURITY INVOKER
  COMMENT 'Select Users'
  BEGIN
    SELECT *
    FROM Users;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `removeCinema` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `removeCinema`(nU INT)
  SQL SECURITY INVOKER
  COMMENT 'Delete Cinema'
  BEGIN
    DELETE FROM Cinema
    WHERE Number_Cinema = nU;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `removeCountry` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `removeCountry`(nU INT)
  SQL SECURITY INVOKER
  COMMENT 'Delete Country'
  BEGIN
    DELETE FROM Country
    WHERE number_country = nU;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `removeFilm` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `removeFilm`(nU INT)
  SQL SECURITY INVOKER
  COMMENT 'Delete Film'
  BEGIN
    DELETE FROM Film
    WHERE Number_Film = nU;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `removeSession` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `removeSession`(nU INT)
  SQL SECURITY INVOKER
  COMMENT 'Delete Session'
  BEGIN
    DELETE FROM Session
    WHERE Number_Session = nU;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `removeTypeSession` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `removeTypeSession`(nU INT)
  SQL SECURITY INVOKER
  COMMENT 'Delete Type_Session'
  BEGIN
    DELETE FROM Type_Session
    WHERE Number_Type = nU;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `updateCinema` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `updateCinema`(numC INT, nC VARCHAR(20), Address VARCHAR(100), oldNumC INT)
  SQL SECURITY INVOKER
  COMMENT 'Update Cinema'
  BEGIN
    UPDATE Cinema
    SET number_cinema = numC, name = nC, address = address
    WHERE number_cinema = oldNumC;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `updateCountry` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `updateCountry`(numC INT, nC VARCHAR(20), oldNumC INT)
  SQL SECURITY INVOKER
  COMMENT 'Update Country'
  BEGIN
    UPDATE Country
    SET number_country = numC, name = nC
    WHERE number_country = oldNumC;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `updateFilm` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `updateFilm`(numF INT, nC INT, nF VARCHAR(20), Dur INT, oldNumF INT)
  SQL SECURITY INVOKER
  COMMENT 'Update Film'
  BEGIN
    UPDATE Film
    SET number_film = numF, number_country = nC, name = nF, duration = Dur
    WHERE number_film = oldNumF;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `updateSession` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `updateSession`(numC INT, numR INT, numT INT, numF INT, d DATETIME,
                                                             numS INT, oldNumS INT)
  SQL SECURITY INVOKER
  COMMENT 'Update Session'
  BEGIN
    UPDATE Session
    SET number_cinema = numC, number_room = numR, number_type = numT,
      number_film     = numF, date = d, number_session = numS
    WHERE number_session = oldNumS;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!50003 DROP PROCEDURE IF EXISTS `updateTypeSession` */;
/*!50003 SET @saved_cs_client = @@character_set_client */;
/*!50003 SET @saved_cs_results = @@character_set_results */;
/*!50003 SET @saved_col_connection = @@collation_connection */;
/*!50003 SET character_set_client = utf8 */;
/*!50003 SET character_set_results = utf8 */;
/*!50003 SET collation_connection = utf8_general_ci */;
/*!50003 SET @saved_sql_mode = @@sql_mode */;
/*!50003 SET sql_mode = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
DELIMITER ;;
CREATE DEFINER =`root`@`localhost` PROCEDURE `updateTypeSession`(numTS INT, nTS VARCHAR(20), oldNumTS INT)
  SQL SECURITY INVOKER
  COMMENT 'Update Type_Session'
  BEGIN
    UPDATE Type_Session
    SET number_type = numTS, name = nTS
    WHERE number_type = oldNumTS;
  END ;;
DELIMITER ;
/*!50003 SET sql_mode = @saved_sql_mode */;
/*!50003 SET character_set_client = @saved_cs_client */;
/*!50003 SET character_set_results = @saved_cs_results */;
/*!50003 SET collation_connection = @saved_col_connection */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2017-05-09 14:56:03
