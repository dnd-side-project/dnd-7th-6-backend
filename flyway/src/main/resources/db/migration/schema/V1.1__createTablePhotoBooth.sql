--
-- Table structure for table `photo_booth`
--
DROP TABLE IF EXISTS `photo_booth`;
 SET character_set_client = utf8mb4;
CREATE TABLE `photo_booth` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'photo booth id',
  `name` varchar(50) NOT NULL COMMENT 'photo booth name',
  `point` POINT NOT NULL COMMENT 'geometry',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포토부스 테이블';