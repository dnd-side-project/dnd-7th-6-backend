--
-- Table structure for table `review_image`
--
DROP TABLE IF EXISTS `review_image`;
 SET character_set_client = utf8mb4;
CREATE TABLE `review_image` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'review image id',
  `review_id` bigint NOT NULL COMMENT 'review id',
  `image_url` varchar(200) NOT NULL COMMENT 'image url',
  `image_order` int NOT NULL default 1 COMMENT 'image 순서',
  `like_count` int NOT NULL default 0 COMMENT '좋아요 수',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`),
  FOREIGN KEY (review_id) REFERENCES review(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포토부스 리뷰 이미지 테이블';