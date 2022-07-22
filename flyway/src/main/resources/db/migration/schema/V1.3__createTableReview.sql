--
-- Table structure for table `review`
--
DROP TABLE IF EXISTS `review`;
 SET character_set_client = utf8mb4;
CREATE TABLE `review` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'review id',
  `photo_booth_id` bigint NOT NULL COMMENT 'photo booth id',
  `title` varchar(50) NOT NULL COMMENT 'review title',
  `content` varchar(50) NOT NULL COMMENT 'review content',
  `like_count` int NOT NULL default 0 COMMENT '리뷰 좋아요 수',
  `status` varchar(100) NOT NULL COMMENT '상태값',
  `user_id` bigint NOT NULL COMMENT 'user_id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  FOREIGN KEY (photo_booth_id) REFERENCES photo_booth(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포토부스 리뷰 테이블';