--
-- Table structure for table `tag`
--
DROP TABLE IF EXISTS `tag`;
 SET character_set_client = utf8mb4;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'tag id',
  `title` varchar(50) NOT NULL COMMENT 'tag title',
  `keyword` varchar(50) NOT NULL COMMENT 'tag keyword',
  `review_count` int NOT NULL default 0  COMMENT 'tag 리뷰수',
  `tag_icon_image_url` varchar(200) NULL  COMMENT 'tag icon image url',
  `post_count` int NOT NULL default 0 COMMENT 'tag 포스트수',
  `photo_booth_count` int NOT NULL default 0 COMMENT 'tag photoBooth 수',
  `tag_type` varchar(50) NOT NULL COMMENT 'tag type',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='태그 테이블';
create unique index tag_keyword_tag_type_uindex
    on tag (tag_type, keyword);
