
--
-- Table structure for table `post_tag`
--
DROP TABLE IF EXISTS `post_tag`;
 SET character_set_client = utf8mb4;
CREATE TABLE `post_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'post tag id',
  `post_id` bigint NOT NULL COMMENT 'post id',
  `tag_id` bigint NOT NULL COMMENT 'tag id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`),
  FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포스트 태그 연관 테이블';