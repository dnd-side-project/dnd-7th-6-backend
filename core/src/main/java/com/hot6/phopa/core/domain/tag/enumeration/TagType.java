package com.hot6.phopa.core.domain.tag.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TagType {
    BRAND, BOOTH_CONDITION, PHOTO_CONDITION, HEAD_COUNT, RELATION, CONCEPT, SITUATION, FRAME;

    public static final List<TagType> POST_FILTER_TAG_LIST = new ArrayList<>(Arrays.asList(BRAND, HEAD_COUNT, CONCEPT, FRAME));
    public static final List<TagType> REVIEW_FORM_TAG_LIST = new ArrayList<>(Arrays.asList(BOOTH_CONDITION, PHOTO_CONDITION));
    public static final List<TagType> PERSONAL_TAG_LIST = new ArrayList<>(Arrays.asList(HEAD_COUNT, RELATION));
    public static final List<TagType> CONCEPT_TAG_LIST = new ArrayList<>(Arrays.asList(CONCEPT, SITUATION));
}
