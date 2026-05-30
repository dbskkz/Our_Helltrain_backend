CREATE TABLE IF NOT EXISTS `manager`(
  `email` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `founding_date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `announcement` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `img_path` varchar(500) NOT NULL,
  `shelf_date` date NOT NULL,
  `removal_date` date NOT NULL,
  `publish` tinyint NOT NULL DEFAULT '0',
  `content` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE IF NOT EXISTS `order`(
  `order_id` int NOT NULL,
  `buyer_id` int NOT NULL,
  `product_id` int NOT NULL,
  `create_date` date NOT NULL,
  `status` varchar(45) NOT NULL,
  `buyer_check` tinyint NOT NULL,
  `seller_check` tinyint NOT NULL,
  `buyer_rank` float DEFAULT '0',
  `salesman_rank` float DEFAULT '0',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `product_name` varchar(45) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `price` int NOT NULL,
  `img_path` varchar(500) NOT NULL,
  `type` varchar(60) NOT NULL,
  `shelf_date` date DEFAULT NULL,
  `product_condition` varchar(500) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `grade` varchar(45) NOT NULL,
  `location` varchar(45) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `report`(
  `report_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int INT NULL DEFAULT '0' 
  `complainant_id` int NOT NULL DEFAULT '0',
  `accused_id` int  INT NULL DEFAULT '0',
  `description` varchar(200) NOT NULL DEFAULT '0',
  ` file_path` varchar(500) DEFAULT NULL,
  `report_date` date NOT NULL,
  `status` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `violation_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `score`(
  `product_id` int NOT NULL,
  `base_score` varchar(45) NOT NULL,
  `click` varchar(45) NOT NULL,
  `score` int DEFAULT '0',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS  `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(60) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `location` varchar(45) NOT NULL,
  `school` varchar(60) NOT NULL,
  `department` varchar(45) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `verified` date DEFAULT NULL,
  `good_level` float DEFAULT '0',
  `msg` varchar(200) DEFAULT NULL,
  `img_path` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `violation`(
  `user_email` varchar(60) NOT NULL,
  `violation_id` int NOT NULL AUTO_INCREMENT,
  `violation_type` varchar(45) DEFAULT NULL,
  `violation_at` date NOT NULL,
  PRIMARY KEY (`violation_id`,`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 新增欄位
SET @exist := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'product'
      AND column_name = 'dept_group'
);

SET @sql = IF(
    @exist = 0,
    'ALTER TABLE product ADD COLUMN dept_group VARCHAR(255)',
    'SELECT "Column already exists"'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- announcement_table 新增 published
----先查表，看看有沒有要新增的欄位
SET @exist2 := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'user'
      AND column_name = 'create_date'
);
--如果上述結果為0(沒查到)就新增(ALTER那行)，結果為1就印出下面那行(欄位已存在)
SET @sql2 = IF(
    @exist2 = 0,
    'ALTER TABLE user ADD COLUMN create_date DATETIME NOT NULL,
    'SELECT "create_date already exists"'
);
--執行
PREPARE stmt2 FROM @sql2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;


-- 其他資料表的欄位繼續往下加...