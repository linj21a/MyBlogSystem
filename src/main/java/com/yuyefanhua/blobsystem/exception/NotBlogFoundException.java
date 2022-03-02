/**
 * @author 60417
 * @date 2022/2/24
 * @time 15:29
 * @todo
 */
package com.yuyefanhua.blobsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 没有具体的博客
 */
//重定向状态码，默认找不到返回500，我们这里返回404
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotBlogFoundException extends RuntimeException {
    public NotBlogFoundException() {
    }

    public NotBlogFoundException(String message) {
        super(message);
    }

    protected NotBlogFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
