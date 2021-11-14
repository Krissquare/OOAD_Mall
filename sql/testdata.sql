-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: oomall
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `oomall_share_activity`
--

DROP TABLE IF EXISTS `oomall_share_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `oomall_share_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_id` bigint(20) DEFAULT NULL,
  `shop_name` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `state` tinyint(4) DEFAULT '0',
  `created_by` bigint(20) DEFAULT NULL,
  `create_name` varchar(128) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `modi_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分享活动';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oomall_share_activity`
--

INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (1,2,'甜蜜之旅','分享活动1',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',1,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (2,3,'向往时刻','分享活动2',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',2,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (3,5,'坚持就是胜利','分享活动3',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',2,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (4,4,'努力向前','分享活动4',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',0,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (5,3,'向往时刻','分享活动5',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',1,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (6,4,'努力向前','分享活动6',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',1,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (7,2,'甜蜜之旅','分享活动7',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',1,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (8,5,'坚持就是胜利','分享活动8',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',1,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (9,3,'向往时刻','分享活动9',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',1,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
INSERT INTO `oomall_share_activity` (`id`, `shop_id`, `shop_name`, `name`, `strategy`, `begin_time`, `end_time`, `state`, `created_by`, `create_name`, `modified_by`, `modi_name`, `gmt_create`, `gmt_modified`) VALUES (10,2,'甜蜜之旅','分享活动10',NULL,'2021-11-11 15:01:23','2022-02-19 15:01:23',1,1,'admin',NULL,NULL,'2021-11-11 15:01:23',NULL);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-14 22:19:15
