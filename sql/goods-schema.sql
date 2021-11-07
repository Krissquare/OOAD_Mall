-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: 192.168.31.241    Database: oomall
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `advance_sale`
--

DROP TABLE IF EXISTS `advance_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advance_sale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `advance_pay_price` bigint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `commission_ratio` bigint DEFAULT NULL,
  `pid` bigint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `orderitem_id` bigint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `state` tinyint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon_activity`
--

DROP TABLE IF EXISTS `coupon_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `coupon_time` datetime DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `quantity_type` tinyint DEFAULT NULL,
  `valid_term` tinyint DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon_onsale`
--

DROP TABLE IF EXISTS `coupon_onsale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_onsale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activity_id` bigint DEFAULT NULL,
  `onsale_id` bigint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `freight_model`
--

DROP TABLE IF EXISTS `freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `freight_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `default_model` tinyint DEFAULT NULL,
  `tpye` tinyint DEFAULT NULL,
  `unit` int DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `groupon_activity`
--

DROP TABLE IF EXISTS `groupon_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupon_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `onsale`
--

DROP TABLE IF EXISTS `onsale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `onsale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `activity_id` bigint DEFAULT NULL,
  `share_act_id` bigint DEFAULT NULL,
  `invalid_by` varchar(64) DEFAULT NULL,
  `invalid_time` datetime DEFAULT NULL,
  `valid` tinyint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `piece_freight_model`
--

DROP TABLE IF EXISTS `piece_freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `piece_freight_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `freight_model_id` bigint DEFAULT NULL,
  `first_items` int DEFAULT NULL,
  `first_item_freight` bigint DEFAULT NULL,
  `additional_items` int DEFAULT NULL,
  `additional_items_price` bigint DEFAULT NULL,
  `region_id` bigint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `pre_id` bigint DEFAULT NULL,
  `goods_id` bigint DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `freight_id` bigint DEFAULT NULL,
  `sku_sn` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `original_price` bigint DEFAULT NULL,
  `weight` bigint DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_draft`
--

DROP TABLE IF EXISTS `product_draft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_draft` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `goods_id` bigint DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `weight` bigint DEFAULT NULL,
  `freight_id` bigint DEFAULT NULL,
  `sku_sn` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `original_price` bigint DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `detail` varchar(500) DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `state` tinyint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4190 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `share_activity`
--

DROP TABLE IF EXISTS `share_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `share_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `deposit` bigint DEFAULT NULL,
  `deposit_threshold` bigint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shop_account`
--

DROP TABLE IF EXISTS `shop_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `account` bigint DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `weight_freight_model`
--

DROP TABLE IF EXISTS `weight_freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weight_freight_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `freight_model_id` bigint DEFAULT NULL,
  `first_weight` int DEFAULT NULL,
  `first_weight_freight` bigint DEFAULT NULL,
  `ten_price` bigint DEFAULT NULL,
  `fifty_price` bigint DEFAULT NULL,
  `hundred__price` bigint DEFAULT NULL,
  `trihun_price` bigint DEFAULT NULL,
  `above_price` bigint DEFAULT NULL,
  `region_id` bigint DEFAULT NULL,
  `creator_id`   bigint            DEFAULT NULL COMMENT '创建用户id',
  `gmt_create`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier_id`  bigint            DEFAULT NULL COMMENT '修改用户id',
  `gmt_modified` datetime          DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-05 20:13:23
