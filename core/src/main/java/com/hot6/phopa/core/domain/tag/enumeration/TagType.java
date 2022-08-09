package com.hot6.phopa.core.domain.tag.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TagType {

    BRAND("브랜드"), BOOTH_CONDITION("부스 필터"), PHOTO_CONDITION("사진 필터"), HEAD_COUNT("인원수"), RELATION("관계"), CONCEPT("포즈컨셉"), SITUATION("상황"), FRAME("프레임");

    String desc;
    public static final List<TagType> POST_TAG_LIST = new ArrayList<>(Arrays.asList(BRAND, HEAD_COUNT, RELATION, CONCEPT, SITUATION, FRAME));
    public static final List<TagType> PHOTO_BOOTH_FILTER_TAG_LIST = new ArrayList<>(Arrays.asList(BRAND, BOOTH_CONDITION, PHOTO_CONDITION));
    public static final List<TagType> REVIEW_FORM_TAG_LIST = new ArrayList<>(Arrays.asList(BOOTH_CONDITION, PHOTO_CONDITION));
    public static final List<TagType> PERSONAL_TAG_LIST = new ArrayList<>(Arrays.asList(HEAD_COUNT, RELATION));
    public static final List<TagType> CONCEPT_TAG_LIST = new ArrayList<>(Arrays.asList(CONCEPT, SITUATION));

    TagType(String desc) {
        this.desc = desc;
    }

    public String getDesc(){
        if(PERSONAL_TAG_LIST.contains(this)){
            return "인원";
        } else if(CONCEPT_TAG_LIST.contains(this)){
            return "포즈컨셉";
        } else {
            return this.desc;
        }
    }
}
