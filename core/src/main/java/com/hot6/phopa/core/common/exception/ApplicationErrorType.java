package com.hot6.phopa.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 주의! error code 값을 변경할 때는 클라이언트에서 혹시 사용하고 있지 않은지 꼭 확인해야 합니다!
 */
@AllArgsConstructor
public enum ApplicationErrorType {
    /**
     * common (-10000 ~ -19999)
     */
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, -10000, "try.again"),
    INVALID_DATA_ARGUMENT(HttpStatus.BAD_REQUEST, -10001, "try.again"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10002, "try.again"),
    INTERNAL_CONFIGURATION_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10003, "try.again"),
    INVALID_VALIDATION_CODE(HttpStatus.BAD_REQUEST, -15000, "try.again"),
    INVALID_REQUEST_TO_ROOT_VIEW(HttpStatus.BAD_REQUEST, -10004, "try.again"/*클라이언트에서 Root View 로 이동*/),
    CLIENT_ABORT(HttpStatus.BAD_REQUEST, -10005, "try.again"),
    ALREADY_ACCOUNT(HttpStatus.BAD_REQUEST, -10006, "이미 가입된 이메일입니다. 일반 로그인해주세요"),

    JSON_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10007, "json 파싱에 실패했습니다."),

    /**
     * Maintenance Mode (-99999)
     */
    MAINTENANCE_MODE_IS_ON(HttpStatus.SERVICE_UNAVAILABLE, -99999, "maintenance.mode"),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10008, "s3 파일업로드 실패"),
    CANNOT_BE_DELETED(HttpStatus.INTERNAL_SERVER_ERROR, -10008, "s3 파일제거 실패"),
    FILE_DOWNLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -10008, "s3 파일 다운로드 실패"),
    COULDNT_FIND_ANY_DATA(HttpStatus.INTERNAL_SERVER_ERROR, -10009, "cannot found any data"),
    INACTIVE_USER(HttpStatus.FORBIDDEN, -100010, "INACTIVE USER"),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, -100010, "UNAUTHORIZED USER"),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, -100010, "expired token"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.FORBIDDEN, -100011, "expired refresh token"),
    ALREADY_NAME(HttpStatus.BAD_REQUEST, -100012, "동일한 닉네임이 있어요"),
    DIFF_USER(HttpStatus.BAD_REQUEST, -100013, "작성자가 아닙니다."),
    CANNOT_BE_CREATED_USER(HttpStatus.BAD_REQUEST, -100014, "자신의 글에는 좋아요를 누를 수 없습니다."),
    DEAGREEMENT_USER(HttpStatus.FORBIDDEN, -100015, "약관 동의를 하지 않은 유저입니다. 약관 동의를 해주세요."),
    ALREADY_REPORT_POST(HttpStatus.BAD_REQUEST, -100016, "이미 신고한 게시글입니다."),
    ;


    @Getter
    private HttpStatus httpStatus;

    @Getter
    private Integer code;

    @Getter
    private String message;

    public int getStatusCode() {
        return httpStatus.value();
    }
}
