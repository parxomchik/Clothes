-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: clothes
-- ------------------------------------------------------
-- Server version	5.7.7-rc-log

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
-- Table structure for table `Attribute`
--

DROP TABLE IF EXISTS `Attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Attribute` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `idCategory` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Attribute_1_idx` (`idCategory`),
  CONSTRAINT `idCategory` FOREIGN KEY (`idCategory`) REFERENCES `Category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `News`
--

DROP TABLE IF EXISTS `News`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `News` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `description` text,
  `image` longblob,
  `imageName` varchar(255) DEFAULT NULL,
  `lastModified` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PageComponent`
--

DROP TABLE IF EXISTS `PageComponent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PageComponent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(45) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `metaDesc` varchar(100) DEFAULT NULL,
  `metaKeywords` varchar(100) DEFAULT NULL,
  `componentType` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PlainComponent`
--

DROP TABLE IF EXISTS `PlainComponent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PlainComponent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `identifier` varchar(45) DEFAULT NULL,
  `fieldDesc` varchar(45) DEFAULT NULL,
  `idPageComponent` int(11) DEFAULT NULL,
  `componentType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idPageComponent_idx` (`idPageComponent`),
  CONSTRAINT `idPageComponent456` FOREIGN KEY (`idPageComponent`) REFERENCES `pageComponent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `blobPropertyForPlainComponent`
--

DROP TABLE IF EXISTS `blobPropertyForPlainComponent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blobPropertyForPlainComponent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyR` varchar(255) NOT NULL,
  `valueR` longblob,
  `idPlainComponent` int(11) NOT NULL,
  PRIMARY KEY (`id`,`idPlainComponent`,`keyR`),
  KEY `FK_1a2gkrysob5v6pl45co6ic9yl_idx` (`idPlainComponent`),
  CONSTRAINT `FK_1a2gkrysob5v6pl45co6ic9y0` FOREIGN KEY (`idPlainComponent`) REFERENCES `plainComponent` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL DEFAULT '-1',
  `active` tinyint(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` longblob,
  `imageName` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `idCategory` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idCategoryParent_idx` (`idCategory`),
  CONSTRAINT `idParentCategory78` FOREIGN KEY (`idCategory`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `globalSetting`
--

DROP TABLE IF EXISTS `globalSetting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `globalSetting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyR` varchar(45) DEFAULT NULL,
  `valueR` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `key_UNIQUE` (`keyR`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  `dateAdd` datetime DEFAULT NULL,
  `description` varchar(10000) DEFAULT NULL,
  `price` double(7,2) NOT NULL DEFAULT '0.00',
  `packPrice` double(7,2) NOT NULL DEFAULT '0.00',
  `title` varchar(255) DEFAULT NULL,
  `firm` varchar(45) DEFAULT NULL,
  `productCode` varchar(20) DEFAULT NULL,
  `packSize` varchar(45) DEFAULT NULL,
  `hit` tinyint(1) DEFAULT '0',
  `idCategory` int(11) DEFAULT NULL,
  `dateModified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `productCode_UNIQUE` (`productCode`),
  KEY `FK_j1hvqkikk1b2h6aiask6j2i50` (`idCategory`),
  CONSTRAINT `FK_j1hvqkikk1b2h6aiask6j2i54` FOREIGN KEY (`idCategory`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `itemAttributeProperty`
--

DROP TABLE IF EXISTS `itemAttributeProperty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemAttributeProperty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idItem` int(11) NOT NULL,
  `idAttribute` int(11) NOT NULL,
  `idProperty` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ItemAttributeProperty_1_idx` (`idItem`),
  KEY `fk_ItemAttributeProperty_2_idx` (`idAttribute`),
  KEY `fk_ItemAttributeProperty_3_idx` (`idProperty`),
  CONSTRAINT `fk_ItemAttributeProperty_122` FOREIGN KEY (`idItem`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ItemAttributeProperty_222` FOREIGN KEY (`idAttribute`) REFERENCES `attribute` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ItemAttributeProperty_322` FOREIGN KEY (`idProperty`) REFERENCES `property` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `itemImage`
--

DROP TABLE IF EXISTS `itemImage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemImage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data` longblob NOT NULL,
  `smallData` longblob,
  `title` varchar(255) NOT NULL,
  `idItem` int(11) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `colorId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_ItemImage_1_idx` (`idItem`),
  CONSTRAINT `idItem2` FOREIGN KEY (`idItem`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orderItem`
--

DROP TABLE IF EXISTS `orderItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderItem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateDone` datetime DEFAULT NULL,
  `idItem` int(11) DEFAULT NULL,
  `idOrder` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `price` double(7,2) DEFAULT '0.00',
  `count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_sj9weblk0nri5im1v0d9nh8v0` (`idItem`),
  KEY `FK_2bxoktrnw2mj46f4xvl7evija` (`idOrder`),
  CONSTRAINT `FK_sj9weblk0nri5im1v0d9nh8v2` FOREIGN KEY (`idItem`) REFERENCES `item` (`id`),
  CONSTRAINT `idOrderX` FOREIGN KEY (`idOrder`) REFERENCES `orderX` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `orderX`
--

DROP TABLE IF EXISTS `orderX`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orderX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateAdd` datetime DEFAULT NULL,
  `dateDelivered` datetime DEFAULT NULL,
  `dateDone` datetime DEFAULT NULL,
  `idUser` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `total` double(7,2) DEFAULT '0.00',
  `deliveryType` varchar(45) DEFAULT NULL,
  `serviceTitle` varchar(45) DEFAULT NULL,
  `addressOrStorageNum` varchar(255) DEFAULT NULL,
  `paymentType` varchar(45) DEFAULT NULL,
  `paymentId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fhff1rpsdgxuuiv3t852v18bf` (`idUser`),
  CONSTRAINT `idUserX` FOREIGN KEY (`idUser`) REFERENCES `userX` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property`
--

DROP TABLE IF EXISTS `property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `property` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `idAttribute` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Property_1_idx` (`idAttribute`),
  CONSTRAINT `fk_Property_12` FOREIGN KEY (`idAttribute`) REFERENCES `Attribute` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `propertyForPlainComponent`
--

DROP TABLE IF EXISTS `propertyForPlainComponent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `propertyForPlainComponent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyR` varchar(45) DEFAULT NULL,
  `valueR` text,
  `idPlainComponent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idPlaintComponent_idx` (`idPlainComponent`),
  CONSTRAINT `idPlaintComponent212` FOREIGN KEY (`idPlainComponent`) REFERENCES `plainComponent` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `relativeItem`
--

DROP TABLE IF EXISTS `relativeItem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relativeItem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idItem` int(11) NOT NULL,
  `idRelative` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_RelativeItem_12_idx` (`idItem`),
  KEY `fk_RelativeItem_13222_idx` (`idRelative`),
  CONSTRAINT `fk_RelativeItem_12222` FOREIGN KEY (`idItem`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_RelativeItem_13222` FOREIGN KEY (`idRelative`) REFERENCES `item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userX`
--

DROP TABLE IF EXISTS `userX`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `house` varchar(45) DEFAULT NULL,
  `apartment` varchar(45) DEFAULT NULL,
  `postIndex` int(11) DEFAULT '0',
  `regDate` datetime DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-12 16:49:11
