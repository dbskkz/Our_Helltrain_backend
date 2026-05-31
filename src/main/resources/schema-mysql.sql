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

 CREATE TABLE IF NOT EXISTS `report` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NULL DEFAULT 0,
  `complainant_id` int NOT NULL DEFAULT 0,
  `accused_id` int NULL DEFAULT 0,
  `description` varchar(200) NOT NULL DEFAULT '0',
  `file_path` varchar(500) DEFAULT NULL,
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
  `img_path` varchar(500) DEFAULT 'default_avatar.png',
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

---- announcement_table 新增 published
------先查表，看看有沒有要新增的欄位
--SET @exist2 := (
--    SELECT COUNT(*)
--    FROM information_schema.columns
--    WHERE table_schema = DATABASE()
--      AND table_name = 'user'
--      AND column_name = 'create_date'
--);
----如果上述結果為0(沒查到)就新增(ALTER那行)，結果為1就印出下面那行(欄位已存在)
--SET @sql2 = IF(
--    @exist2 = 0,
--    'ALTER TABLE user ADD COLUMN create_date DATETIME NOT NULL,
--    'SELECT "create_date already exists"'
--);
----執行
--PREPARE stmt2 FROM @sql2;
--EXECUTE stmt2;
--DEALLOCATE PREPARE stmt2;
SET @exist2 := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'user'
      AND column_name = 'create_date'
);

SET @sql2 := IF(
    @exist2 = 0,
    'ALTER TABLE `user` ADD COLUMN `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP',
    'SELECT "create_date already exists"'
);

PREPARE stmt2 FROM @sql2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;

-- 其他資料表的欄位繼續往下加...

SET @exist3 := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'user'
      AND column_name = 'img_path'
      AND column_default = 'default_avatar.png'
);

SET @sql3 := IF(
    @exist3 = 0,
    'ALTER TABLE `user` MODIFY COLUMN `img_path` VARCHAR(500) DEFAULT ''default_avatar.png''',
    'SELECT "img_path default already set"'
);

PREPARE stmt3 FROM @sql3;
EXECUTE stmt3;
DEALLOCATE PREPARE stmt3;

SET @exist4 := (
    SELECT COUNT(*)
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'report'
      AND column_name = 'product_id'
);

SET @sql4 := IF(
    @exist4 = 0,
    'ALTER TABLE `report` CHANGE COLUMN `order_id` `product_id` INT NULL DEFAULT 0',
    'SELECT "product_id already exists"'
);

PREPARE stmt4 FROM @sql4;
EXECUTE stmt4;
DEALLOCATE PREPARE stmt4;

-- 1. user.verified 改成 DATE
SET @exist_verified := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'user'
    AND column_name = 'verified'
    AND data_type = 'date'
);
SET @sql_verified := IF(@exist_verified = 0,
    'ALTER TABLE `user` MODIFY COLUMN `verified` DATE DEFAULT NULL',
    'SELECT "verified already DATE"');
PREPARE stmt_verified FROM @sql_verified;
EXECUTE stmt_verified;
DEALLOCATE PREPARE stmt_verified;

-- 2. announcement 刪除 status
SET @exist_ann_status := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'announcement'
    AND column_name = 'status'
);
SET @sql_ann_status := IF(@exist_ann_status = 1,
    'ALTER TABLE `announcement` DROP COLUMN `status`',
    'SELECT "status already removed"');
PREPARE stmt_ann_status FROM @sql_ann_status;
EXECUTE stmt_ann_status;
DEALLOCATE PREPARE stmt_ann_status;

-- 3. announcement.content 移除 NOT NULL
SET @exist_content := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'announcement'
    AND column_name = 'content'
    AND is_nullable = 'NO'
);
SET @sql_content := IF(@exist_content = 1,
    'ALTER TABLE `announcement` MODIFY COLUMN `content` VARCHAR(300) DEFAULT NULL',
    'SELECT "content already nullable"');
PREPARE stmt_content FROM @sql_content;
EXECUTE stmt_content;
DEALLOCATE PREPARE stmt_content;

-- announcement.shelf_date 改成 DATE
SET @exist_shelf := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'announcement'
    AND column_name = 'shelf_date'
    AND data_type = 'date'
);
SET @sql_shelf := IF(@exist_shelf = 0,
    'ALTER TABLE `announcement` MODIFY COLUMN `shelf_date` DATE NOT NULL',
    'SELECT "shelf_date already DATE"');
PREPARE stmt_shelf FROM @sql_shelf;
EXECUTE stmt_shelf;
DEALLOCATE PREPARE stmt_shelf;

-- announcement.removal_date 改成 DATE
SET @exist_removal := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'announcement'
    AND column_name = 'removal_date'
    AND data_type = 'date'
);
SET @sql_removal := IF(@exist_removal = 0,
    'ALTER TABLE `announcement` MODIFY COLUMN `removal_date` DATE NOT NULL',
    'SELECT "removal_date already DATE"');
PREPARE stmt_removal FROM @sql_removal;
EXECUTE stmt_removal;
DEALLOCATE PREPARE stmt_removal;

-- order.create_date 改成 DATE
SET @exist_order_date := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'order'
    AND column_name = 'create_date'
    AND data_type = 'date'
);
SET @sql_order_date := IF(@exist_order_date = 0,
    'ALTER TABLE `order` MODIFY COLUMN `create_date` DATE NOT NULL',
    'SELECT "order.create_date already DATE"');
PREPARE stmt_order_date FROM @sql_order_date;
EXECUTE stmt_order_date;
DEALLOCATE PREPARE stmt_order_date;

-- product.shelf_date 改成 DATE
SET @exist_prod_shelf := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'product'
    AND column_name = 'shelf_date'
    AND data_type = 'date'
);
SET @sql_prod_shelf := IF(@exist_prod_shelf = 0,
    'ALTER TABLE `product` MODIFY COLUMN `shelf_date` DATE DEFAULT NULL',
    'SELECT "product.shelf_date already DATE"');
PREPARE stmt_prod_shelf FROM @sql_prod_shelf;
EXECUTE stmt_prod_shelf;
DEALLOCATE PREPARE stmt_prod_shelf;

-- report.report_date 改成 DATE
SET @exist_report_date := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'report'
    AND column_name = 'report_date'
    AND data_type = 'date'
);
SET @sql_report_date := IF(@exist_report_date = 0,
    'ALTER TABLE `report` MODIFY COLUMN `report_date` DATE NOT NULL',
    'SELECT "report_date already DATE"');
PREPARE stmt_report_date FROM @sql_report_date;
EXECUTE stmt_report_date;
DEALLOCATE PREPARE stmt_report_date;

-- user.verified 改成 DATE
SET @exist_verified := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'user'
    AND column_name = 'verified'
    AND data_type = 'date'
);
SET @sql_verified := IF(@exist_verified = 0,
    'ALTER TABLE `user` MODIFY COLUMN `verified` DATE DEFAULT NULL',
    'SELECT "verified already DATE"');
PREPARE stmt_verified FROM @sql_verified;
EXECUTE stmt_verified;
DEALLOCATE PREPARE stmt_verified;

-- violation.violation_at 改成 DATE
SET @exist_violation_at := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'violation'
    AND column_name = 'violation_at'
    AND data_type = 'date'
);
SET @sql_violation_at := IF(@exist_violation_at = 0,
    'ALTER TABLE `violation` MODIFY COLUMN `violation_at` DATE NOT NULL',
    'SELECT "violation_at already DATE"');
PREPARE stmt_violation_at FROM @sql_violation_at;
EXECUTE stmt_violation_at;
DEALLOCATE PREPARE stmt_violation_at;

-- manager.founding_date 改成 DATE
SET @exist_founding := (
    SELECT COUNT(*) FROM information_schema.columns
    WHERE table_schema = DATABASE()
    AND table_name = 'manager'
    AND column_name = 'founding_date'
    AND data_type = 'date'
);
SET @sql_founding := IF(@exist_founding = 0,
    'ALTER TABLE `manager` MODIFY COLUMN `founding_date` DATE DEFAULT NULL',
    'SELECT "founding_date already DATE"');
PREPARE stmt_founding FROM @sql_founding;
EXECUTE stmt_founding;
DEALLOCATE PREPARE stmt_founding;