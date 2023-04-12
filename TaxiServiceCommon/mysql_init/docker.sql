CREATE DATABASE if not exists TaxiService;

use TaxiService;

CREATE TABLE if not exists `driver_credentials` (
  `driver_id` bigint NOT NULL AUTO_INCREMENT,
  `mobile_number` varchar(10) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  PRIMARY KEY (`driver_id`),
  KEY `idx_driver_credentials_mobile_number` (`mobile_number`),
  KEY `idx_driver_credentials_mobile_number_password` (`mobile_number`,`password`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE if not exists `driver_profile` (
  `profile_id` bigint NOT NULL AUTO_INCREMENT,
  `driver_id` bigint NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `age` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  PRIMARY KEY (`profile_id`),
  UNIQUE KEY `driver_id_UNIQUE` (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;