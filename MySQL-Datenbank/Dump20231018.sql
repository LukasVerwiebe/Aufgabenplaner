-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: javafxdb
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notes` (
  `note_id` int NOT NULL AUTO_INCREMENT,
  `note_details` longtext,
  `note_owner` int NOT NULL,
  PRIMARY KEY (`note_id`),
  KEY `noteowner_idx` (`note_owner`),
  CONSTRAINT `noteowner` FOREIGN KEY (`note_owner`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (1,'Credit-Points: 53\nNotenschnitt: 2,31\n\nM: 2,7\n\n\nOffene Prüfungen: \nBSY: 6\nGMI: 7\nMIN: 6\n\n\nSemester 4:\nINP:\nBKV:\nIDB:\nKIN:\n---------\nSP:\nPM:\nWI:\n',1),(2,'Test Nutzer',24),(3,NULL,25);
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `task_header` varchar(45) DEFAULT NULL,
  `task_details` longtext,
  `task_deadline` date DEFAULT NULL,
  `task_categorie` varchar(45) DEFAULT NULL,
  `task_owner` int NOT NULL,
  PRIMARY KEY (`task_id`),
  KEY `user_id_idx` (`task_owner`),
  CONSTRAINT `userid` FOREIGN KEY (`task_owner`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (59,'Test mit Datum','gjhmv ','2022-04-24','Important',1),(61,'','','2022-04-18','In Progress',24),(82,'GitHub Projekte','Die Projekte der Vorlseungen: MCI, OPR und INS auf GitHub stellen.','2022-05-31','Someday',1),(83,'GUI  Programmierung','Als Übung die EPR und evtl. die OPR Projekte neu Programmieren und mit einer GUI versehen.','2022-05-31','Someday',1),(84,'Projekte mit Datenbanken','Noch mehr Projekte mit einer Datenbank umsetzen.\n- INS Praktikum mit XMAPP eine Datenbank erstellen\n- ....\n','2022-05-31','Someday',1),(85,'INP: Vorlesungsvideos ansehen','Die neuen INP Videos ansehen und Notizen erstellen','2022-04-19','Approved',1),(86,'IDB: Notizen zu Vorlseungen erstellen','Die Notizen in HelpNdoc um IDB erweitern','2022-04-19','Approved',1),(87,'PM: Notizen zur Vorlseung machen','HelpNdoc datei erweitern um Themenbereich','2022-04-19','Approved',1),(88,'KI: Aufgabe bis zum 21.04.2022 erledigen','Die Aufgabe bearbeiten','2022-04-19','In Progress',1),(89,'KI: Notizen zur Vorlseung anfertigen','','2022-04-19','Approved',1),(90,'KI: Übungsblatt 1 Aufgabe 1 bearbeiten','','2022-04-19','Approved',1),(91,'Python Video','Video zu Python Programmierung ansehen','2022-04-19','Someday',1),(92,'INP: Praktikum 1','Praktikum 1 bearbeiten und Lösung hochladen.','2022-04-20','Approved',1),(93,'WI: Aufgaben bearbeiten','Aufgaben aus der Vorlseung bearbeiten. Buch lesen und Notizen machen.\nTest','2022-04-20','Approved',1),(94,'BKV: Praktikum 2','Das BKV Praktikum 2 erledigen.','2022-04-21','Approved',1),(95,'BKV: Vorlseungs Notizen erstellen','','2022-04-21','Approved',1),(96,'IDB: Praktikum 1 Vorbereitung','Praktikumsaufgaben von Blatt 1 bearbeiten.','2022-04-22','Approved',1),(97,'INP: Vorlesungsnotizen Transportebene','Notizen zu der INP Vorlseung ergänzen','2022-04-22','In Progress',1),(98,'WI: Notizen zu Vorlsung','','2022-04-22','Approved',1),(99,'PM: Projektvereinbarung','','2022-04-22','In Progress',1),(100,'INP: Videos zum Thema DNS','','2022-04-24','In Progress',1),(101,'INP: Notizen zum Thema DNS','','2022-04-24','In Progress',1),(102,'Hyper-V: Einrichten','','2022-04-24','Someday',1),(103,'JavaFX: Library Programm','https://www.youtube.com/watch?v=SsV29noHQRU&list=PLhs1urmduZ29jTcE1ca8Z6bZNvH_39ayL&index=2','2022-04-24','Someday',1),(104,'Login System','https://www.youtube.com/watch?v=DH3dWzmkT5Y','2022-04-27','Waiting',1),(105,'Login System 2','https://www.youtube.com/watch?v=HqjeeTMjMTU','2022-04-27','Waiting',1),(106,'Dashboard','https://www.youtube.com/watch?v=cPF3qGTjYgk','2022-04-27','Waiting',1),(107,'Task Management System','https://www.youtube.com/watch?v=5T_9K3ZkCf8','2022-04-27','Waiting',1),(108,'Graphs Dashboard','https://www.youtube.com/watch?v=QVU60_VWho0','2022-04-27','Waiting',1),(109,'Keyhub','https://www.youtube.com/watch?v=C0YdOxljm1E','2022-04-27','Waiting',1),(110,'Customer Service Dashboard','https://www.youtube.com/watch?v=CWGYyVeDXlI','2022-04-27','Waiting',1),(111,'C# Modern UI','https://www.youtube.com/watch?v=5AsJJl7Bhvc','2022-04-27','Waiting',1);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `emailadress` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'lukas','test','test@test.de','Admin'),(24,'peter','test',NULL,'Client'),(25,'admin','admin',NULL,'Client');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-18 21:07:37
