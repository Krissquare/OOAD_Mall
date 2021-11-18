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
-- Table structure for table `oomall_advance_sale`
--

DROP TABLE IF EXISTS `oomall_advance_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_advance_sale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `shop_name` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `advance_pay_price` bigint DEFAULT NULL,
  `state` tinyint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='预售';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_category`
--

DROP TABLE IF EXISTS `oomall_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `commission_ratio` int DEFAULT '15' COMMENT '平台抽佣比例（千分位）',
  `pid` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=314 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_comment`
--

DROP TABLE IF EXISTS `oomall_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `orderitem_id` bigint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `state` tinyint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `post_by` bigint DEFAULT NULL,
  `post_name` varchar(128) DEFAULT NULL,
  `post_time` datetime DEFAULT NULL,
  `audit_by` bigint DEFAULT NULL,
  `audit_name` varchar(128) DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_coupon_activity`
--

DROP TABLE IF EXISTS `oomall_coupon_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_coupon_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `shop_name` varchar(128) DEFAULT NULL,
  `coupon_time` datetime DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `quantity_type` tinyint DEFAULT NULL,
  `valid_term` tinyint DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `state` tinyint DEFAULT '0',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠活动';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_coupon_onsale`
--

DROP TABLE IF EXISTS `oomall_coupon_onsale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_coupon_onsale` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activity_id` bigint DEFAULT NULL,
  `onsale_id` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3308 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠活动商品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_freight_model`
--

DROP TABLE IF EXISTS `oomall_freight_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_freight_model` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `default_model` tinyint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `unit` int DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='运费模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_goods`
--

DROP TABLE IF EXISTS `oomall_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_goods` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=501 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品集合';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_groupon_activity`
--

DROP TABLE IF EXISTS `oomall_groupon_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_groupon_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `shop_id` bigint DEFAULT NULL,
  `shop_name` varchar(128) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `state` tinyint DEFAULT '0',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='团购活动';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_onsale`
--

DROP TABLE IF EXISTS `oomall_onsale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_onsale` (
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
  `state` tinyint DEFAULT '0',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3913 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='价格与数量';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_piece_freight`
--

DROP TABLE IF EXISTS `oomall_piece_freight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_piece_freight` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `freight_model_id` bigint DEFAULT NULL,
  `first_items` int DEFAULT NULL,
  `first_item_freight` bigint DEFAULT NULL,
  `additional_items` int DEFAULT NULL,
  `additional_items_price` bigint DEFAULT NULL,
  `region_id` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='计件运费';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_product`
--

DROP TABLE IF EXISTS `oomall_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `shop_name` varchar(128) DEFAULT NULL,
  `goods_id` bigint DEFAULT '0',
  `category_id` bigint DEFAULT '0',
  `freight_id` bigint DEFAULT '0',
  `sku_sn` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `original_price` bigint DEFAULT NULL,
  `weight` bigint DEFAULT '1',
  `image_url` varchar(255) DEFAULT NULL,
  `barcode` varchar(128) DEFAULT NULL,
  `unit` varchar(64) DEFAULT '个',
  `origin_place` varchar(128) DEFAULT NULL COMMENT '产地',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  `state` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5462 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_product_draft`
--

DROP TABLE IF EXISTS `oomall_product_draft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_product_draft` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `goods_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `weight` bigint DEFAULT NULL,
  `freight_id` bigint DEFAULT NULL,
  `sku_sn` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `original_price` bigint DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `barcode` varchar(128) DEFAULT NULL,
  `unit` varchar(64) DEFAULT '个',
  `origin_place` varchar(128) DEFAULT NULL COMMENT '产地',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品草稿';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_region`
--

DROP TABLE IF EXISTS `oomall_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_region` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `state` tinyint DEFAULT '0',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4192 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='行政区划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_share_activity`
--

DROP TABLE IF EXISTS `oomall_share_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_share_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `shop_name` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `strategy` varchar(500) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `state` tinyint DEFAULT '0',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分享活动';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_shop`
--

DROP TABLE IF EXISTS `oomall_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_shop` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `deposit` bigint DEFAULT NULL,
  `deposit_threshold` bigint DEFAULT NULL,
  `state` tinyint DEFAULT '0',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商铺';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_shop_account`
--

DROP TABLE IF EXISTS `oomall_shop_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_shop_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `account` varchar(256) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `priority` tinyint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商铺账号';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oomall_weight_freight`
--

DROP TABLE IF EXISTS `oomall_weight_freight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oomall_weight_freight` (
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
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(128) DEFAULT NULL,
  `modifier_id` bigint DEFAULT NULL,
  `modifier_name` varchar(128) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='计重运费';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-18 20:13:33
