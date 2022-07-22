--
-- Table structure for table `post`
--
DROP TABLE IF EXISTS `post`;
 SET character_set_client = utf8mb4;
CREATE TABLE `post` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'post id',
  `title` varchar(50) NOT NULL COMMENT 'post title',
  `content` varchar(50) NOT NULL COMMENT 'post content',
  `like_count` int NOT NULL default 0 COMMENT 'post like',
  `status` varchar(100) NOT NULL COMMENT '상태값',
  `user_id` bigint NOT NULL COMMENT 'user_id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='커뮤니티 포스트 테이블';