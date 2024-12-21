CREATE TABLE `message` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `from` varchar(255) DEFAULT NULL,
                           `to` varchar(255) DEFAULT NULL,
                           `content` varchar(255) DEFAULT NULL,
                           `status` tinyint(1) DEFAULT NULL,
                            `type` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
