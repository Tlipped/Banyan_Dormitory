CREATE TABLE `user` (
                        `id` varchar(255) NOT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `school` varchar(255) DEFAULT NULL,
                        `score` varchar(255) DEFAULT NULL,
                        `room` varchar(255) DEFAULT NULL,
                        `bed` varchar(255) DEFAULT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
