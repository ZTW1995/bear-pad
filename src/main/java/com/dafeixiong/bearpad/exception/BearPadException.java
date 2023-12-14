package com.dafeixiong.bearpad.exception;

/**
 * <p>项目名称：bear-pad</p>
 * <p>类名：com.dafeixiong.bearpad.exception.BearPadException</p>
 * <p>创建时间: 2023-08-17 17:15 </p>
 * <p>修改时间: 2023-08-17 17:15 </p>
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public final class BearPadException extends RuntimeException {

    public BearPadException(String message) {
        super(message);
    }

    public BearPadException(Throwable throwable) {
        super(throwable);
    }

    public BearPadException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
