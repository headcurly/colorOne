package com.colorone.common.frame.exception;

import com.colorone.common.constant.ExceptionMsg;
import com.colorone.common.domain.core.RequestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author： lee
 * @email：miracleren@gmail.com
 * @date：2023/7/11
 * @备注：全局异常处理
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public RequestResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return RequestResult.error(e.toString());
    }


    //region Spring Security异常的处理
    @ExceptionHandler(UsernameNotFoundException.class)
    public RequestResult handleUsernameNotFoundException(UsernameNotFoundException e) {
        //log.error(e.getMessage(), e);
        return RequestResult.error(e.toString());
    }

    /**
     * 用户名错误\密码错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public RequestResult handleBadCredentialsException(BadCredentialsException e) {
        //log.error(e.getMessage(), e);
        return RequestResult.error(ExceptionMsg.LOGIN_BAD_CREDENTIALS);
    }

    /**
     * 账号被禁用
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DisabledException.class)
    public RequestResult handleDisabledException(DisabledException e) {
        //log.error(e.getMessage(), e);
        return RequestResult.error(ExceptionMsg.LOGIN_USER_DISABLED);
    }

    /**
     * 无权限
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public RequestResult handleAccessDeniedException(AccessDeniedException e) {
        //log.error(e.getMessage(), e);
        return RequestResult.error(ExceptionMsg.ACCESS_DENIED);
    }

    /**
     * 账号已过期
     * @param e
     * @return
     */
    @ExceptionHandler(AccountExpiredException.class)
    public RequestResult handleAccountExpiredException(AccountExpiredException e) {
        //log.error(e.getMessage(), e);
        return RequestResult.error(ExceptionMsg.ACCOUNT_EXPIRED);
    }

    //endregion
}
