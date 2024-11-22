CREATE DATABASE IF NOT EXISTS `rest_wtih_springboot`;

USE `rest_wtih_springboot`;

CREATE TABLE IF NOT EXISTS `example` (
                                         `id` bigint NOT NULL AUTO_INCREMENT,
                                         `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;