-- 약관 동의 여부 추가
alter table `user`
    add `is_agreement_term` tinyint(1) default 0 not null comment '약관 동의 여부' after provider_id;