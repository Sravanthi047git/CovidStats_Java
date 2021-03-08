-- MySQL dump 10.16  Distrib 10.1.26-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: db
-- ------------------------------------------------------
-- Server version	10.1.26-MariaDB-0+deb9u1

--
-- Table structure for table `Global_data`
--
DROP TABLE IF EXISTS `Global_data`;
CREATE TABLE `Global_data` (
  `Region` varchar(50) DEFAULT 'Global',  
  `g_confirmed` int(4) DEFAULT NULL,  
  `g_recovered` int(4) DEFAULT NULL,
  `g_deaths` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `Source_info`
--
DROP TABLE IF EXISTS `Source_info`;
CREATE TABLE `Source_info` (
  `url` varchar(200) DEFAULT NULL,
  `publishedBy` varchar(200) DEFAULT NULL,
  `ref` varchar(200) DEFAULT NULL,
  `lastUpdated` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `Country_data`
--
DROP TABLE IF EXISTS `Country_data`;
CREATE TABLE `Country_data` (
  `c_country` varchar(50)  DEFAULT NULL,
  `c_state` varchar(50)  DEFAULT NULL,  
  `c_confirmed` int(4) DEFAULT NULL,  
  `c_recovered` int(4) DEFAULT NULL,
  `c_deaths` int(4) DEFAULT NULL,
  `c_active` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
--

--
-- Table structure for table `Country_data`
--
DROP TABLE IF EXISTS `Country_total`;
CREATE TABLE `Country_total` (
  `ct_country` varchar(50)  DEFAULT NULL, 
  `ct_confirmed` int(4) DEFAULT NULL,  
  `ct_recovered` int(4) DEFAULT NULL,
  `ct_deaths` int(4) DEFAULT NULL,
  `ct_active` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
--


