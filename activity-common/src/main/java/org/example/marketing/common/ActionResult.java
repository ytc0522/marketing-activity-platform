package org.example.marketing.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @param <T></T>
 * @author : CM
 * @date : 2019/3/4
 */
@Data
public class ActionResult<T> implements Serializable {
    private static final long serialVersionUID = -7561168468033650904L;
    private int errCode;
    private String errMsg;
    private T value;

    public ActionResult() {
        this(ErrorCode.SUCCESS);
    }

    public ActionResult(IErrorCode errorCode) {
        this.errCode = errorCode.getCode();
        this.errMsg = errorCode.getMsg();
        this.value = null;
    }


    public ActionResult(IErrorCode errorCode, T value) {
        this.errCode = errorCode.getCode();
        this.errMsg = errorCode.getMsg();
        this.value = value;
    }


    public ActionResult(T value) {
        this.errCode = ErrorCode.SUCCESS.getCode();
        this.errMsg = ErrorCode.SUCCESS.getMsg();
        this.value = value;
    }

    public static ActionResult success() {
        return new ActionResult(ErrorCode.SUCCESS);
    }

    public static ActionResult success(Object value) {
        return new ActionResult(value);
    }


    public static ActionResult failure() {
        return new ActionResult(ErrorCode.FAILURE);
    }

    public static ActionResult error() {
        return new ActionResult(ErrorCode.UN_KNOWN_EXCEPTION);
    }


    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setErrorCode(IErrorCode errorCode) {
        this.errCode = errorCode.getCode();
        this.errMsg = errorCode.getMsg();
    }
}
