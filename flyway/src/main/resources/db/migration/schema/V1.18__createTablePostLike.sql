--
-- Table structure for table `post_like`
--
DROP TABLE IF EXISTS `post_like`;
 SET character_set_client = utf8mb4;
CREATE TABLE `post_like` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'post_like',
  `post_id` bigint NOT NULL COMMENT 'post_id',
  `user_id` bigint NOT NULL COMMENT 'user_id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`),
  FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포스트 좋아요 테이블';
create unique index post_like_post_id_user_id_uindex
    on post_like (post_id, user_id);