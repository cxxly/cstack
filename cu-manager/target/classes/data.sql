-- MySQL dump 10.13  Distrib 5.7.4-m14, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: cstack
-- ------------------------------------------------------
-- Server version	5.7.4-m14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Application`
--

DROP TABLE IF EXISTS `Application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Application` (
  `id` int(11) NOT NULL,
  `contextPath` varchar(255) DEFAULT NULL,
  `cuInstanceName` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `deploymentStatus` varchar(255) DEFAULT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnpeynyt1v85j1do416lgmbr0l` (`user_id`,`name`,`cuInstanceName`),
  CONSTRAINT `FK610rj0lic91qt1fr08f0iikmh` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Application`
--

LOCK TABLES `Application` WRITE;
/*!40000 ALTER TABLE `Application` DISABLE KEYS */;
INSERT INTO `Application` VALUES (27,'/examples','DEV','2017-03-27 17:12:40','ALREADY_DEPLOYED','app','app','START',1);
/*!40000 ALTER TABLE `Application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Application_aliases`
--

DROP TABLE IF EXISTS `Application_aliases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Application_aliases` (
  `Application_id` int(11) NOT NULL,
  `aliases` varchar(255) DEFAULT NULL,
  KEY `FKqj7a58n1j2a5nx9f6s20tcw9i` (`Application_id`),
  CONSTRAINT `FKqj7a58n1j2a5nx9f6s20tcw9i` FOREIGN KEY (`Application_id`) REFERENCES `Application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Application_aliases`
--

LOCK TABLES `Application_aliases` WRITE;
/*!40000 ALTER TABLE `Application_aliases` DISABLE KEYS */;
/*!40000 ALTER TABLE `Application_aliases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Deployment`
--

DROP TABLE IF EXISTS `Deployment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Deployment` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `application_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfqo12q3vwq5uit9tyy21jeqex` (`application_id`),
  CONSTRAINT `FKfqo12q3vwq5uit9tyy21jeqex` FOREIGN KEY (`application_id`) REFERENCES `Application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Deployment`
--

LOCK TABLES `Deployment` WRITE;
/*!40000 ALTER TABLE `Deployment` DISABLE KEYS */;
INSERT INTO `Deployment` VALUES (39,'2017-03-27 17:13:42','WAR',27);
/*!40000 ALTER TABLE `Deployment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EnvironmentVariable`
--

DROP TABLE IF EXISTS `EnvironmentVariable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EnvironmentVariable` (
  `id` int(11) NOT NULL,
  `containerName` varchar(255) DEFAULT NULL,
  `keyEnv` varchar(255) DEFAULT NULL,
  `valueEnv` varchar(255) DEFAULT NULL,
  `application_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsd06os8cmoxjo3gh7aak46i1g` (`application_id`),
  CONSTRAINT `FKsd06os8cmoxjo3gh7aak46i1g` FOREIGN KEY (`application_id`) REFERENCES `Application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EnvironmentVariable`
--

LOCK TABLES `EnvironmentVariable` WRITE;
/*!40000 ALTER TABLE `EnvironmentVariable` DISABLE KEYS */;
INSERT INTO `EnvironmentVariable` VALUES (29,'app-johndoe','CU_DATABASE_USER_MYSQL','adminn0aqf60c',27),(30,'app-johndoe','CU_DATABASE_NAME_MYSQL','app',27),(31,'app-johndoe','CU_DATABASE_PASSWORD_MYSQL','d13khsk8',27),(32,'app-johndoe','CU_DATABASE_DNS_MYSQL','app-mysql-johndoe',27),(34,'app-mysql-johndoe','MYSQL_USER','adminn0aqf60c',27),(35,'app-mysql-johndoe','MYSQL_DATABASE','app',27),(36,'app-mysql-johndoe','MYSQL_PASSWORD','d13khsk8',27);
/*!40000 ALTER TABLE `EnvironmentVariable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ErrorMessage`
--

DROP TABLE IF EXISTS `ErrorMessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ErrorMessage` (
  `id` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ErrorMessage`
--

LOCK TABLES `ErrorMessage` WRITE;
/*!40000 ALTER TABLE `ErrorMessage` DISABLE KEYS */;
/*!40000 ALTER TABLE `ErrorMessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Image`
--

DROP TABLE IF EXISTS `Image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Image` (
  `id` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `imageSubType` varchar(255) DEFAULT NULL,
  `imageType` varchar(255) DEFAULT NULL,
  `managerName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `prefixEnv` varchar(255) DEFAULT NULL,
  `prefixId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Image`
--

LOCK TABLES `Image` WRITE;
/*!40000 ALTER TABLE `Image` DISABLE KEYS */;
INSERT INTO `Image` VALUES (10,'Tomcat 6.0.45','APPSERVER','server','','tomcat-6','cloudunit/tomcat-6','tomcat',NULL,NULL),(11,'Tomcat 7.0.70','APPSERVER','server','','tomcat-7','cloudunit/tomcat-7','tomcat',NULL,NULL),(12,'Tomcat 8.0.39','APPSERVER','server','','tomcat-8','cloudunit/tomcat-8','tomcat',NULL,NULL),(13,'Tomcat 8.5.9','APPSERVER','server','','tomcat-85','cloudunit/tomcat-85','tomcat',NULL,NULL),(14,'Tomcat 9.0.0.M15','APPSERVER','server','','tomcat-9','cloudunit/tomcat-9','tomcat',NULL,NULL),(20,'MySQL 5.5.49','DATABASE','module','phpmyadmin','mysql-5-5','cloudunit/mysql-5-5','mysql',NULL,NULL),(21,'MySQL 5.6.30','DATABASE','module','phpmyadmin','mysql-5-6','cloudunit/mysql-5-6','mysql',NULL,NULL),(22,'MySQL 5.7.12','DATABASE','module','phpmyadmin','mysql-5-7','cloudunit/mysql-5-7','mysql',NULL,NULL),(30,'Mongo 2.6','DATABASE','module','mms','mongo-2-6','cloudunit/mongo-2-6','mongo',NULL,NULL),(40,'Redis 3.2','DATAGRID','module','redmin','redis-3-2','cloudunit/redis-3-2','redis',NULL,NULL),(50,'PostgreSQL 9.3','DATABASE','module','phppgadmin','postgresql-9-3','cloudunit/postgresql-9-3','postgresql',NULL,NULL),(51,'PostgreSQL 9.4','DATABASE','module','phppgadmin','postgresql-9-4','cloudunit/postgresql-9-4','postgresql',NULL,NULL),(52,'PostgreSQL 9.5','DATABASE','module','phppgadmin','postgresql-9-5','cloudunit/postgresql-9-5','postgresql',NULL,NULL),(53,'PostGIS 2.2.1 (9.4)','DATABASE','module','phppgadmin','postgis-2-2','cloudunit/postgis-2-2','postgresql',NULL,NULL),(60,'WildFly 8.2.1','APPSERVER','server','','wildfly-8','cloudunit/wildfly-8','wildfly',NULL,NULL),(61,'WildFly 9.0.2','APPSERVER','server','','wildfly-9','cloudunit/wildfly-9','wildfly',NULL,NULL),(62,'WildFly 10.0.0','APPSERVER','server','','wildfly-10','cloudunit/wildfly-10','wildfly',NULL,NULL),(70,'FatJar','STANDALONE','server','','fatjar','cloudunit/fatjar','fatjar',NULL,NULL),(80,'Apache 2.2.22','WEBSERVER','server','','apache-2-2','cloudunit/apache-2-2','apache',NULL,NULL),(90,'ActiveMQ 5.13.2','MESSAGE_BROKER','module','','activemq-5.13','cloudunit/activemq-5.13','activemq',NULL,NULL),(100,'RabbitMQ 3.6.5-1','MESSAGE_BROKER','module','','rabbitmq-3.6','cloudunit/rabbitmq-3.6','rabbitmq',NULL,NULL),(110,'Elasticsearch 2.4','SEARCH_ENGINE','module','','elasticsearch-2.4','cloudunit/elasticsearch-2.4','elasticsearch',NULL,NULL),(120,'Nginx 1.10','WEBSERVER','server','server','nginx-1.10','cloudunit/nginx-1.10','nginx',NULL,NULL);
/*!40000 ALTER TABLE `Image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Image_exposedPorts`
--

DROP TABLE IF EXISTS `Image_exposedPorts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Image_exposedPorts` (
  `Image_id` int(11) NOT NULL,
  `exposedPorts` varchar(255) DEFAULT NULL,
  `exposedPorts_KEY` varchar(255) NOT NULL,
  PRIMARY KEY (`Image_id`,`exposedPorts_KEY`),
  CONSTRAINT `FKms93jcptxqbkuhkwfljvjtdxx` FOREIGN KEY (`Image_id`) REFERENCES `Image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Image_exposedPorts`
--

LOCK TABLES `Image_exposedPorts` WRITE;
/*!40000 ALTER TABLE `Image_exposedPorts` DISABLE KEYS */;
INSERT INTO `Image_exposedPorts` VALUES (12,'8000','TCP'),(20,'3306','TCP'),(21,'3306','TCP'),(22,'3306','TCP'),(30,'27017','TCP'),(40,'6379','TCP'),(50,'5432','TCP'),(51,'5432','TCP'),(52,'5432','TCP'),(53,'5432','TCP'),(90,'5672','AMQP'),(90,'1883','MQTT'),(90,'61616','OPENWIRE'),(90,'61613','STOMP'),(90,'8161','WEBCONSOLE'),(90,'61614','WEBSERVICES'),(100,'5672','AMQP'),(100,'15672','WEBCONSOLE'),(110,'9300','HTTP'),(110,'9200','TCP');
/*!40000 ALTER TABLE `Image_exposedPorts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Image_moduleEnvironmentVariables`
--

DROP TABLE IF EXISTS `Image_moduleEnvironmentVariables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Image_moduleEnvironmentVariables` (
  `Image_id` int(11) NOT NULL,
  `moduleEnvironmentVariables` varchar(255) DEFAULT NULL,
  `moduleEnvironmentVariables_KEY` varchar(255) NOT NULL,
  PRIMARY KEY (`Image_id`,`moduleEnvironmentVariables_KEY`),
  CONSTRAINT `FKklpxn9wk7ogbw6g5sw1ner4vx` FOREIGN KEY (`Image_id`) REFERENCES `Image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Image_moduleEnvironmentVariables`
--

LOCK TABLES `Image_moduleEnvironmentVariables` WRITE;
/*!40000 ALTER TABLE `Image_moduleEnvironmentVariables` DISABLE KEYS */;
INSERT INTO `Image_moduleEnvironmentVariables` VALUES (20,'MYSQL_DATABASE','NAME'),(20,'MYSQL_PASSWORD','PASSWORD'),(20,'MYSQL_USER','USER'),(21,'MYSQL_DATABASE','NAME'),(21,'MYSQL_PASSWORD','PASSWORD'),(21,'MYSQL_USER','USER'),(22,'MYSQL_DATABASE','NAME'),(22,'MYSQL_PASSWORD','PASSWORD'),(22,'MYSQL_USER','USER'),(50,'POSTGRES_DB','NAME'),(50,'POSTGRES_PASSWORD','PASSWORD'),(50,'POSTGRES_USER','USER'),(51,'POSTGRES_DB','NAME'),(51,'POSTGRES_PASSWORD','PASSWORD'),(51,'POSTGRES_USER','USER'),(52,'POSTGRES_DB','NAME'),(52,'POSTGRES_PASSWORD','PASSWORD'),(52,'POSTGRES_USER','USER'),(53,'POSTGRES_DB','NAME'),(53,'POSTGRES_PASSWORD','PASSWORD'),(53,'POSTGRES_USER','USER'),(90,'ACTIVEMQ_NAME','NAME'),(90,'ACTIVEMQ_ADMIN_PASSWORD','PASSWORD'),(90,'ACTIVEMQ_ADMIN_LOGIN','USER'),(100,'RABBITMQ_DEFAULT_VHOST','NAME'),(100,'RABBITMQ_DEFAULT_PASS','PASSWORD'),(100,'RABBITMQ_DEFAULT_USER','USER');
/*!40000 ALTER TABLE `Image_moduleEnvironmentVariables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Message`
--

DROP TABLE IF EXISTS `Message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Message` (
  `id` int(11) NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `applicationName` varchar(255) DEFAULT NULL,
  `cuInstanceName` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `event` text,
  `type` varchar(255) DEFAULT NULL,
  `author_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKorjgff3apodyiybbjttr8iet0` (`author_id`),
  CONSTRAINT `FKorjgff3apodyiybbjttr8iet0` FOREIGN KEY (`author_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Message`
--

LOCK TABLES `Message` WRITE;
/*!40000 ALTER TABLE `Message` DISABLE KEYS */;
INSERT INTO `Message` VALUES (23,NULL,'3af22e5570f2','DEV','2017-03-25 12:05:50',NULL,'INFO',1),(24,NULL,'app','DEV','2017-03-25 12:05:51','John Doe attempts to deploy a new Application : app','INFO',1),(26,NULL,'app','DEV','2017-03-25 12:05:51','John Doe has deployed a new Application : app from WAR','INFO',1),(37,NULL,'aa8492c5d0df','DEV','2017-03-27 17:13:41',NULL,'INFO',1),(38,NULL,'app','DEV','2017-03-27 17:13:42','John Doe attempts to deploy a new Application : app','INFO',1),(40,NULL,'app','DEV','2017-03-27 17:13:42','John Doe has deployed a new Application : app from WAR','INFO',1);
/*!40000 ALTER TABLE `Message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Metric`
--

DROP TABLE IF EXISTS `Metric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Metric` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `serverName` varchar(255) DEFAULT NULL,
  `suffix` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Metric`
--

LOCK TABLES `Metric` WRITE;
/*!40000 ALTER TABLE `Metric` DISABLE KEYS */;
/*!40000 ALTER TABLE `Metric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Module`
--

DROP TABLE IF EXISTS `Module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Module` (
  `id` int(11) NOT NULL,
  `containerID` varchar(255) DEFAULT NULL,
  `containerIP` varchar(255) DEFAULT NULL,
  `internalDNSName` varchar(255) DEFAULT NULL,
  `memorySize` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sshPort` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `initialized` bit(1) NOT NULL,
  `image_id` int(11) DEFAULT NULL,
  `application_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKecnox526svth2s39k33hbmti6` (`image_id`),
  KEY `FKtblqn01v678osrtho8yqpa5aa` (`application_id`),
  CONSTRAINT `FKtblqn01v678osrtho8yqpa5aa` FOREIGN KEY (`application_id`) REFERENCES `Application` (`id`),
  CONSTRAINT `FKecnox526svth2s39k33hbmti6` FOREIGN KEY (`image_id`) REFERENCES `Image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Module`
--

LOCK TABLES `Module` WRITE;
/*!40000 ALTER TABLE `Module` DISABLE KEYS */;
INSERT INTO `Module` VALUES (33,'de6a73e7b3f6','','app-mysql-johndoe',NULL,'app-mysql-johndoe',NULL,NULL,'START','\0',20,27);
/*!40000 ALTER TABLE `Module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ModuleConfiguration`
--

DROP TABLE IF EXISTS `ModuleConfiguration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ModuleConfiguration` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ModuleConfiguration`
--

LOCK TABLES `ModuleConfiguration` WRITE;
/*!40000 ALTER TABLE `ModuleConfiguration` DISABLE KEYS */;
/*!40000 ALTER TABLE `ModuleConfiguration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ModuleConfiguration_properties`
--

DROP TABLE IF EXISTS `ModuleConfiguration_properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ModuleConfiguration_properties` (
  `ModuleConfiguration_id` int(11) NOT NULL,
  `properties` varchar(255) DEFAULT NULL,
  `properties_KEY` varchar(255) NOT NULL,
  PRIMARY KEY (`ModuleConfiguration_id`,`properties_KEY`),
  CONSTRAINT `FK9t3okgf4u52j283wf5uiugxcn` FOREIGN KEY (`ModuleConfiguration_id`) REFERENCES `ModuleConfiguration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ModuleConfiguration_properties`
--

LOCK TABLES `ModuleConfiguration_properties` WRITE;
/*!40000 ALTER TABLE `ModuleConfiguration_properties` DISABLE KEYS */;
/*!40000 ALTER TABLE `ModuleConfiguration_properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Port`
--

DROP TABLE IF EXISTS `Port`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Port` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `containerValue` varchar(255) DEFAULT NULL,
  `hostValue` varchar(255) DEFAULT NULL,
  `opened` bit(1) DEFAULT NULL,
  `portType` varchar(255) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm9y2wiromgvpurtuwuvrau9kk` (`module_id`),
  CONSTRAINT `FKm9y2wiromgvpurtuwuvrau9kk` FOREIGN KEY (`module_id`) REFERENCES `Module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Port`
--

LOCK TABLES `Port` WRITE;
/*!40000 ALTER TABLE `Port` DISABLE KEYS */;
INSERT INTO `Port` VALUES (3,'3306',NULL,'\0','TCP',33);
/*!40000 ALTER TABLE `Port` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PortToOpen`
--

DROP TABLE IF EXISTS `PortToOpen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PortToOpen` (
  `id` int(11) NOT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `nature` varchar(255) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `quickAccess` bit(1) DEFAULT NULL,
  `application_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqrp06tq238wp1rd4w9fw2x3lv` (`application_id`),
  CONSTRAINT `FKqrp06tq238wp1rd4w9fw2x3lv` FOREIGN KEY (`application_id`) REFERENCES `Application` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PortToOpen`
--

LOCK TABLES `PortToOpen` WRITE;
/*!40000 ALTER TABLE `PortToOpen` DISABLE KEYS */;
/*!40000 ALTER TABLE `PortToOpen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProxyCustomPort`
--

DROP TABLE IF EXISTS `ProxyCustomPort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProxyCustomPort` (
  `id` int(11) NOT NULL,
  `portNumber` varchar(255) DEFAULT NULL,
  `used` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProxyCustomPort`
--

LOCK TABLES `ProxyCustomPort` WRITE;
/*!40000 ALTER TABLE `ProxyCustomPort` DISABLE KEYS */;
/*!40000 ALTER TABLE `ProxyCustomPort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Role`
--

DROP TABLE IF EXISTS `Role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Role` (
  `id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Role`
--

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;
INSERT INTO `Role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Script`
--

DROP TABLE IF EXISTS `Script`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Script` (
  `id` int(11) NOT NULL,
  `content` text,
  `creationDate` datetime DEFAULT NULL,
  `creationUserId` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Script`
--

LOCK TABLES `Script` WRITE;
/*!40000 ALTER TABLE `Script` DISABLE KEYS */;
/*!40000 ALTER TABLE `Script` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Server`
--

DROP TABLE IF EXISTS `Server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Server` (
  `id` int(11) NOT NULL,
  `containerID` varchar(255) DEFAULT NULL,
  `containerIP` varchar(255) DEFAULT NULL,
  `internalDNSName` varchar(255) DEFAULT NULL,
  `memorySize` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sshPort` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `jvmMemory` bigint(20) DEFAULT NULL,
  `jvmOptions` text,
  `managerLocation` varchar(255) DEFAULT NULL,
  `image_id` int(11) DEFAULT NULL,
  `application_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlc2buwvma7g5i8njmrrnn48fk` (`image_id`),
  KEY `FK2s7wr3s8q1kug0odk78cogf9j` (`application_id`),
  CONSTRAINT `FK2s7wr3s8q1kug0odk78cogf9j` FOREIGN KEY (`application_id`) REFERENCES `Application` (`id`),
  CONSTRAINT `FKlc2buwvma7g5i8njmrrnn48fk` FOREIGN KEY (`image_id`) REFERENCES `Image` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Server`
--

LOCK TABLES `Server` WRITE;
/*!40000 ALTER TABLE `Server` DISABLE KEYS */;
INSERT INTO `Server` VALUES (28,'aa8492c5d0df','','app-tomcat-johndoe',NULL,'app-johndoe',NULL,'2017-03-27 17:12:55','START',512,'','http://app-johndoe.cloudunit.dev/manager/html',12,27);
/*!40000 ALTER TABLE `Server` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Snapshot`
--

DROP TABLE IF EXISTS `Snapshot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Snapshot` (
  `id` int(11) NOT NULL,
  `applicationDisplayName` varchar(255) DEFAULT NULL,
  `applicationName` varchar(255) DEFAULT NULL,
  `cuInstanceName` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `deploymentStatus` varchar(255) DEFAULT NULL,
  `description` text,
  `displayTag` varchar(255) DEFAULT NULL,
  `fullTag` varchar(255) DEFAULT NULL,
  `jvmMemory` bigint(20) DEFAULT NULL,
  `jvmOptions` varchar(255) DEFAULT NULL,
  `jvmRelease` varchar(255) DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqvq91kpqfh6ftyl3ranb0vmhl` (`user_id`),
  CONSTRAINT `FKqvq91kpqfh6ftyl3ranb0vmhl` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Snapshot`
--

LOCK TABLES `Snapshot` WRITE;
/*!40000 ALTER TABLE `Snapshot` DISABLE KEYS */;
/*!40000 ALTER TABLE `Snapshot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Snapshot_ModuleConfiguration`
--

DROP TABLE IF EXISTS `Snapshot_ModuleConfiguration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Snapshot_ModuleConfiguration` (
  `Snapshot_id` int(11) NOT NULL,
  `appConfig_id` int(11) NOT NULL,
  `appConfig_KEY` varchar(255) NOT NULL,
  PRIMARY KEY (`Snapshot_id`,`appConfig_KEY`),
  UNIQUE KEY `UK_vlgi2la78nqo6fex0urcmxh1` (`appConfig_id`),
  CONSTRAINT `FK7m7a6gwlqlo49r034c4tsudd5` FOREIGN KEY (`Snapshot_id`) REFERENCES `Snapshot` (`id`),
  CONSTRAINT `FKqf58tdbqujrx8tjppyrffrs0o` FOREIGN KEY (`appConfig_id`) REFERENCES `ModuleConfiguration` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Snapshot_ModuleConfiguration`
--

LOCK TABLES `Snapshot_ModuleConfiguration` WRITE;
/*!40000 ALTER TABLE `Snapshot_ModuleConfiguration` DISABLE KEYS */;
/*!40000 ALTER TABLE `Snapshot_ModuleConfiguration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Snapshot_digests`
--

DROP TABLE IF EXISTS `Snapshot_digests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Snapshot_digests` (
  `Snapshot_id` int(11) NOT NULL,
  `digests` varchar(255) DEFAULT NULL,
  `digests_KEY` varchar(255) NOT NULL,
  PRIMARY KEY (`Snapshot_id`,`digests_KEY`),
  CONSTRAINT `FK3kp5qb2a2tavd9u9atdxt4jb6` FOREIGN KEY (`Snapshot_id`) REFERENCES `Snapshot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Snapshot_digests`
--

LOCK TABLES `Snapshot_digests` WRITE;
/*!40000 ALTER TABLE `Snapshot_digests` DISABLE KEYS */;
/*!40000 ALTER TABLE `Snapshot_digests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Snapshot_images`
--

DROP TABLE IF EXISTS `Snapshot_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Snapshot_images` (
  `Snapshot_id` int(11) NOT NULL,
  `images` varchar(255) DEFAULT NULL,
  KEY `FKksvmckobw176mhglqnoruie3j` (`Snapshot_id`),
  CONSTRAINT `FKksvmckobw176mhglqnoruie3j` FOREIGN KEY (`Snapshot_id`) REFERENCES `Snapshot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Snapshot_images`
--

LOCK TABLES `Snapshot_images` WRITE;
/*!40000 ALTER TABLE `Snapshot_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `Snapshot_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Snapshot_savedPorts`
--

DROP TABLE IF EXISTS `Snapshot_savedPorts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Snapshot_savedPorts` (
  `Snapshot_id` int(11) NOT NULL,
  `savedPorts` varchar(255) DEFAULT NULL,
  KEY `FK2krbvdtftewynetonupiey40q` (`Snapshot_id`),
  CONSTRAINT `FK2krbvdtftewynetonupiey40q` FOREIGN KEY (`Snapshot_id`) REFERENCES `Snapshot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Snapshot_savedPorts`
--

LOCK TABLES `Snapshot_savedPorts` WRITE;
/*!40000 ALTER TABLE `Snapshot_savedPorts` DISABLE KEYS */;
/*!40000 ALTER TABLE `Snapshot_savedPorts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastConnection` datetime DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `login` varchar(255) NOT NULL,
  `organization` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `signin` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e6gkqunxajvyxl5uctpl2vl2p` (`email`),
  UNIQUE KEY `UK_587tdsv8u5cvheyo9i261xhry` (`login`),
  KEY `FK84qlpfci484r1luck11eno6ec` (`role_id`),
  CONSTRAINT `FK84qlpfci484r1luck11eno6ec` FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'johndoe.doe@gmail.com','John',NULL,'Doe','johndoe','admin','cVwsWoHVZ28Qf9fHE0W4Qg==','2013-08-22 09:22:06',1,1),(2,'scott.tiger@gmail.com','scott',NULL,'tiger','scott','user','cVwsWoHVZ28Qf9fHE0W4Qg==','2014-02-22 09:22:06',1,2),(3,'admin@otcaix.iscas.ac.cn','Admin',NULL,'Admin','admin','admin','IAbzJQzi7BJ52epoVp0CMw==','2017-03-22 09:22:06',1,1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Volume`
--

DROP TABLE IF EXISTS `Volume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Volume` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Volume`
--

LOCK TABLES `Volume` WRITE;
/*!40000 ALTER TABLE `Volume` DISABLE KEYS */;
/*!40000 ALTER TABLE `Volume` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VolumeAssociation`
--

DROP TABLE IF EXISTS `VolumeAssociation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VolumeAssociation` (
  `mode` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `volume_id` int(11) NOT NULL,
  `server_id` int(11) NOT NULL,
  PRIMARY KEY (`server_id`,`volume_id`),
  KEY `FKdwl0x9ajylx11sk7xa3p3t45d` (`volume_id`),
  CONSTRAINT `FKt3n7py7eoxi4yunbnqmnoeeey` FOREIGN KEY (`server_id`) REFERENCES `Server` (`id`),
  CONSTRAINT `FKdwl0x9ajylx11sk7xa3p3t45d` FOREIGN KEY (`volume_id`) REFERENCES `Volume` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VolumeAssociation`
--

LOCK TABLES `VolumeAssociation` WRITE;
/*!40000 ALTER TABLE `VolumeAssociation` DISABLE KEYS */;
/*!40000 ALTER TABLE `VolumeAssociation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (41),(41),(41),(41),(41),(41),(41),(41),(41),(41),(41),(41),(41),(41),(41),(41);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-27 12:18:42
