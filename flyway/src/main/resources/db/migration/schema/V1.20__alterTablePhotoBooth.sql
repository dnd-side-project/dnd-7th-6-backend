alter table photo_booth
    add tag_id bigint null;

alter table photo_booth
    add constraint photo_booth_photo_booth_tag_id_fk
        foreign key (tag_id) references tag (id);