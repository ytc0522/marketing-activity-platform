package org.example.marketing.common;

/**
 * @author : CM
 * @date : 2019/3/4
 */
public interface IErrorCode {

    int BASE_CODE = 0;
    int ADMIN_BASE_CODE = 1000;
    int BANK_BASE_CODE = 2000;
    int GATEWAY_BASE_CODE = 3000;
    int BANK_HOST_BASE_CODE = 9900;
    int CBD_HOST_BASE_CODE = 20000;
    int IMPORT_BASE_CODE = 30000;

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    String getMsg();
}
