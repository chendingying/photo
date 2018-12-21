package com.photo.warehouse.exception.auth;


import com.photo.warehouse.constants.CommonConstants;
import com.photo.warehouse.util.BaseResponse;

/**
 * Created by cdy on 2017/8/25.
 */
public class TokenForbiddenResponse extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(CommonConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
