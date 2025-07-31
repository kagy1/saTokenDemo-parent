package com.kagy.status;

/**
 * 系统状态码常量类
 * 统一定义系统中使用的状态码
 */
public class StatusCode {

    // ========== 成功状态码 ==========
    /**
     * 请求成功
     */
    public static final int SUCCESS = 200;

    /**
     * 创建成功
     */
    public static final int CREATED = 201;

    // ========== 客户端错误状态码 ==========
    /**
     * 请求参数错误
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 未授权访问
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 禁止访问
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源不存在
     */
    public static final int NOT_FOUND = 404;

    /**
     * 请求频率过高
     */
    public static final int TOO_MANY_REQUESTS = 429;

    // ========== 服务器错误状态码 ==========
    /**
     * 服务器内部错误
     */
    public static final int INTERNAL_SERVER_ERROR = 500;

    // ========== 自定义业务状态码 ==========
    /**
     * 用户名或密码错误
     */
    public static final int LOGIN_FAILED = 1001;

    /**
     * 用户未登录
     */
    public static final int NOT_LOGIN = 1002;

    /**
     * Token过期
     */
    public static final int TOKEN_EXPIRED = 1003;

    /**
     * 权限不足
     */
    public static final int PERMISSION_DENIED = 1004;

    /**
     * 用户已存在
     */
    public static final int USER_EXIST = 1005;

    /**
     * 数据验证失败
     */
    public static final int VALIDATION_FAILED = 1006;

    /**
     * 数据库操作失败
     */
    public static final int DATABASE_ERROR = 1007;

    /**
     * 文件上传失败
     */
    public static final int FILE_UPLOAD_FAILED = 1008;

    /**
     * 验证码错误
     */
    public static final int VERIFICATION_CODE_ERROR = 1009;

    /**
     * 操作过于频繁
     */
    public static final int OPERATION_TOO_FREQUENT = 1010;

    // ========== 状态码描述映射 ==========

    /**
     * 获取状态码对应的描述信息
     */
    public static String getDescription(int code) {
        switch (code) {
            case SUCCESS:
                return "请求成功";
            case CREATED:
                return "创建成功";
            case BAD_REQUEST:
                return "请求参数错误";
            case UNAUTHORIZED:
                return "未授权访问";
            case FORBIDDEN:
                return "禁止访问";
            case NOT_FOUND:
                return "资源不存在";
            case TOO_MANY_REQUESTS:
                return "请求频率过高";
            case INTERNAL_SERVER_ERROR:
                return "服务器内部错误";
            case LOGIN_FAILED:
                return "用户名或密码错误";
            case NOT_LOGIN:
                return "用户未登录";
            case TOKEN_EXPIRED:
                return "Token过期";
            case PERMISSION_DENIED:
                return "权限不足";
            case USER_EXIST:
                return "用户已存在";
            case VALIDATION_FAILED:
                return "数据验证失败";
            case DATABASE_ERROR:
                return "数据库操作失败";
            case FILE_UPLOAD_FAILED:
                return "文件上传失败";
            case VERIFICATION_CODE_ERROR:
                return "验证码错误";
            case OPERATION_TOO_FREQUENT:
                return "操作过于频繁";
            default:
                return "未知错误";
        }
    }
}