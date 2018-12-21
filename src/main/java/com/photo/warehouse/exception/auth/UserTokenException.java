package com.photo.warehouse.exception.auth;

import com.photo.warehouse.constants.CommonConstants;
import com.photo.warehouse.exception.BaseException;

/**
 * Created by cdy on 2017/9/8.
 */
public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
