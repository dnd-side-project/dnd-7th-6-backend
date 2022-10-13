--
-- Table structure for table `post_report_count`
--
DROP TABLE IF EXISTS `post_report_count`;
 SET character_set_client = utf8mb4;
CREATE TABLE `post_report_count` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `post_id` bigint NOT NULL COMMENT 'post_id',
    `report_count` bigint  default 0  not null,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`),
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='포스트 신고 카운트 테이블';