--
-- Table structure for table `tag`
--
DROP TABLE IF EXISTS `tag`;
 SET character_set_client = utf8mb4;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'tag id',
  `tag` varchar(50) NOT NULL COMMENT 'tag name',
  `review_count` int NOT NULL default 0  COMMENT 'tag 리뷰수',
  `post_count` int NOT NULL default 0 COMMENT 'tag 포스트수',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='태그 테이블';
create unique index tag_tag_uindex on tag (tag);