package com.hot6.phopa.core.common.utils;

import com.hot6.phopa.core.common.service.S3UploadService;
import com.hot6.phopa.core.domain.review.model.entity.ReviewImageEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class S3UrlUtil {
    private static String urlPrefix;
    private static S3UploadService s3StaticUploadService;
    private final S3UploadService s3UploadService;

    @PostConstruct
    public void init() {
        S3UrlUtil.s3StaticUploadService = s3UploadService;
    }

    @Value("${cloud.aws.s3.cdn.url}")
    public void setUrlPrefix(String prefix) {
        urlPrefix = prefix;
    }

    public static String convertToS3Url(String path) {
        if (StringUtils.isEmpty(path) || path.startsWith("http")) {
            return path;
        }

        if (path.startsWith("public")) {
            return getFullUrl(path);
        }

        return s3StaticUploadService.generatePreSignedURL(path);
    }

    public static String convertToS3Url(ReviewImageEntity reviewImageEntity) {
        if (reviewImageEntity == null){
            return null;
        }
        return convertToS3Url(reviewImageEntity.getImageUrl());
    }

    public static String convertToS3Url(String path, int expirationHour) {
        if (StringUtils.isEmpty(path) || path.startsWith("http")) {
            return path;
        }

        if (path.startsWith("public")) {
            return getFullUrl(path);
        }

        return s3StaticUploadService.generatePreSignedURL(path, expirationHour);
    }
    private static String getFullUrl(String path) {
        return urlPrefix + "/" + path;
    }
}


