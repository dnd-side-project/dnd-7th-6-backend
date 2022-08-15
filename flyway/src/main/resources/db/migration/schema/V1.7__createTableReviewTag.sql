--
-- Table structure for table `review_tag`
--
DROP TABLE IF EXISTS `review_tag`;
 SET character_set_client = utf8mb4;
CREATE TABLE `review_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'review tag id',
  `review_id` bigint NOT NULL COMMENT 'review id',
  `tag_id` bigint NOT NULL COMMENT 'tag id',
  `photo_booth_id` bigint NOT NULL COMMENT 'photo_booth id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`),
  FOREIGN KEY (review_id) REFERENCES review(id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포토부스 리뷰 연관 테이블';