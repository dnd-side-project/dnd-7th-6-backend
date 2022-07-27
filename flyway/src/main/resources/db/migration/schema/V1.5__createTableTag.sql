--
-- Table structure for table `tag`
--
DROP TABLE IF EXISTS `tag`;
 SET character_set_client = utf8mb4;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'tag id',
  `tag` varchar(50) NOT NULL UNIQUE COMMENT 'tag name',
  `review_count` int NOT NULL default 0  COMMENT 'tag 리뷰수',
  `post_count` int NOT NULL default 0 COMMENT 'tag 포스트수',
  `photo_booth_count` int NOT NULL default 0 COMMENT 'tag photoBooth 수',
  `is_photo_booth` boolean NOT NULL default 0 COMMENT '포토부스 여부',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='태그 테이블';
