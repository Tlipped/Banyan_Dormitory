CREATE TABLE `visitor` (
                           `id` int NOT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `visitor_id` int DEFAULT NULL,
                           `phone_number` int DEFAULT NULL,
                           `date` datetime DEFAULT NULL,
                           `time` datetime DEFAULT NULL,
                           `reason` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
