--
-- Table structure for table `photo_booth_like`
--
DROP TABLE IF EXISTS `photo_booth_like`;
 SET character_set_client = utf8mb4;
CREATE TABLE `photo_booth_like` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'photo_booth_like id',
  `photo_booth_id` bigint NOT NULL COMMENT 'photo_booth_id',
  `user_id` bigint NOT NULL COMMENT 'user_id',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`),
  FOREIGN KEY (photo_booth_id) REFERENCES photo_booth(id) ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포토부스 좋아요 테이블';
create unique index photo_booth_like_photo_booth_id_user_id_uindex
    on photo_booth_like (photo_booth_id, user_id);