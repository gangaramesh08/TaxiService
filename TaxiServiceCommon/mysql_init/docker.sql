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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE if not exists `driver_profile` (
  `profile_id` bigint NOT NULL AUTO_INCREMENT,
  `driver_id` bigint NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `age` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  `vehicle_id` bigint DEFAULT NULL,
  PRIMARY KEY (`profile_id`),
  UNIQUE KEY `driver_id_UNIQUE` (`driver_id`),
  CONSTRAINT `driver_id_driver_profile` FOREIGN KEY (`driver_id`) REFERENCES `driver_credentials` (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE if not exists `driver_documents` (
  `document_id` bigint NOT NULL AUTO_INCREMENT,
  `driver_id` bigint NOT NULL,
  `doc_name` varchar(255) NOT NULL,
  `doc_type` varchar(255) NOT NULL,
  `storage_link` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  PRIMARY KEY (`document_id`),
  KEY `driver_id_driver_doc_idx` (`driver_id`),
  KEY `driver_id_driver_docType` (`driver_id`,`doc_type`),
  CONSTRAINT `driver_id_driver_doc` FOREIGN KEY (`driver_id`) REFERENCES `driver_credentials` (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `driver_status` (
  `driver_id` bigint NOT NULL,
  `status` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  PRIMARY KEY (`driver_id`),
  CONSTRAINT `driver_id_driver_status` FOREIGN KEY (`driver_id`) REFERENCES `driver_credentials` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `driver_address` (
  `address_id` bigint NOT NULL AUTO_INCREMENT,
  `driver_id` bigint NOT NULL,
  `house_number` varchar(255) DEFAULT NULL,
  `street_name` varchar(255) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `zip_code` varchar(10) NOT NULL,
  `type` varchar(25) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `driver_id_ddriver_address` (`driver_id`),
  CONSTRAINT `driver_id_ddriver_address` FOREIGN KEY (`driver_id`) REFERENCES `driver_credentials` (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `driver_background_verification` (
  `verification_id` bigint NOT NULL AUTO_INCREMENT,
  `driver_id` bigint NOT NULL,
  `status` varchar(16) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  PRIMARY KEY (`verification_id`),
  KEY `driver_id_drvier_bgverify_idx` (`driver_id`),
  CONSTRAINT `driver_id_drvier_bgverify` FOREIGN KEY (`driver_id`) REFERENCES `driver_credentials` (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `device_shipment_status` (
  `shipment_id` bigint NOT NULL,
  `device_id` bigint NOT NULL,
  `driver_id` bigint NOT NULL,
  `status` varchar(10) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL,
  PRIMARY KEY (`shipment_id`),
  KEY `driver_id_device_status` (`driver_id`),
  CONSTRAINT `driver_id_device_status` FOREIGN KEY (`driver_id`) REFERENCES `driver_credentials` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `device_details` (
  `device_id` bigint NOT NULL,
  `driver_id` bigint NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL,
  PRIMARY KEY (`driver_id`),
  CONSTRAINT `driver_id_device_details` FOREIGN KEY (`driver_id`) REFERENCES `driver_credentials` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
