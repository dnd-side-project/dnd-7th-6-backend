--
-- Table structure for table `photo_booth`
--
DROP TABLE IF EXISTS `photo_booth`;
 SET character_set_client = utf8mb4;
CREATE TABLE `photo_booth` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'photo booth id',
  `name` varchar(50) NOT NULL COMMENT 'photo booth name',
  `jibun_address` varchar(150) NOT NULL COMMENT 'photo booth jibun address',
  `road_address` varchar(150) NOT NULL COMMENT 'photo booth road address',
  `latitude` DOUBLE NOT NULL COMMENT 'geometry',
  `longitude` DOUBLE NOT NULL COMMENT 'geometry',
  `like_count` int NOT NULL default 0 COMMENT '좋아요수',
  `tag_id` bigint NOT NULL COMMENT 'tag id',
  `status` varchar(100) default 'ACTIVE' NOT NULL COMMENT '상태값',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
  PRIMARY KEY (`id`),
  FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포토부스 테이블';
create unique index photo_booth_point_uindex
    on photo_booth (latitude, longitude);
