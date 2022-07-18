--
-- Table structure for table `post`
--
DROP TABLE IF EXISTS `post`;
 SET character_set_client = utf8mb4;
CREATE TABLE `post` (
  `id` bigint PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'review id',
  `title` varchar(50) NOT NULL COMMENT 'review title',
  `content` varchar(50) NOT NULL COMMENT 'review content',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='커뮤니티 포스트 테이블';