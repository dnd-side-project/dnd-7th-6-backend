--
-- Table structure for table `post_image`
--
DROP TABLE IF EXISTS `post_image`;
 SET character_set_client = utf8mb4;
CREATE TABLE `post_image` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'post image id',
  `post_id` bigint NOT NULL COMMENT 'post id',
  `image_url` varchar(200) NOT NULL COMMENT 'image url',
  `image_order` int NOT NULL default 1 COMMENT 'image 순서',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`),
  FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포스트이미지 테이블';