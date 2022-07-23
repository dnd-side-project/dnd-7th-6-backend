--
-- Table structure for table `review_like`
--
DROP TABLE IF EXISTS `review_like`;
 SET character_set_client = utf8mb4;
CREATE TABLE `review_like` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'review_like',
  `review_id` bigint NOT NULL COMMENT 'review_id',
  `user_id` bigint NOT NULL COMMENT 'user_id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`),
  FOREIGN KEY (review_id) REFERENCES review(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='리뷰 좋아요 테이블';
create unique index review_like_review_id_user_id_uindex
    on review_like (review_id, user_id);