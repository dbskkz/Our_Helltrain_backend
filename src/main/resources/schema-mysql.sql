CREATE TABLE `manager` IF NOT EXIST(
  `email` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `founding_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `announcement` IF NOT EXIST(
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `img_path` varchar(500) NOT NULL,
  `shelf_date` varchar(45) NOT NULL,
  `removal_date` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `department` IF NOT EXIST(
  `department_id` int NOT NULL AUTO_INCREMENT,
  `school` varchar(45) NOT NULL,
  `department` varchar(45) NOT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order` IF NOT EXIST(
  `order_id` int NOT NULL,
  `buyer_id` int NOT NULL,
  `product_id` int NOT NULL,
  `create_date` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `buyer_check` tinyint NOT NULL,
  `seller_check` tinyint NOT NULL,
  `buyer_rank` int DEFAULT '0',
  `salesman_rank` int DEFAULT '0',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `product` IF NOT EXIST(
  `product_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_name` varchar(45) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `price` int NOT NULL,
  `img_path` varchar(500) NOT NULL,
  `type` varchar(60) DEFAULT NULL,
  `shelf_date` varchar(45) NOT NULL,
  `product_condition` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `grade` varchar(45) DEFAULT NULL,
  `location` varchar(45) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `report` IF NOT EXIST(
  `report_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL DEFAULT '0',
  `complainant_id` int NOT NULL DEFAULT '0',
  `accused_id` int NOT NULL DEFAULT '0',
  `description` varchar(200) NOT NULL DEFAULT '0',
  ` file_path` varchar(500) DEFAULT NULL,
  `report_date` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `violation_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `score` IF NOT EXIST(
  `product_id` int NOT NULL,
  `base_score` varchar(45) NOT NULL,
  `click` varchar(45) NOT NULL,
  `score` int DEFAULT '0',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` IF NOT EXIST(
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(60) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `phone` varchar(20) DEFAULT '0',
  `location` varchar(45) NOT NULL,
  `department_id` int NOT NULL DEFAULT '0',
  `status` varchar(45) NOT NULL,
  `student_verified_at` varchar(45) NOT NULL,
  `good_level` int DEFAULT NULL,
  `msg` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `violation` IF NOT EXIST(
  `user_email` varchar(60) NOT NULL,
  `violation_id` int NOT NULL AUTO_INCREMENT,
  `violation_type` varchar(45) DEFAULT NULL,
  `violation_at` varchar(45) NOT NULL,
  PRIMARY KEY (`violation_id`,`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
