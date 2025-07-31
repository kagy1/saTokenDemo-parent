package com.kagy.utils;

import com.kagy.status.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    // ========== 成功响应 ==========

    /**
     * 成功响应（无数据）
     */
    public static <T> Result<T> success() {
        return new Result<>(StatusCode.SUCCESS, StatusCode.getDescription(StatusCode.SUCCESS), null);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(StatusCode.SUCCESS, StatusCode.getDescription(StatusCode.SUCCESS), data);
    }

    /**
     * 成功响应（自定义消息）
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(StatusCode.SUCCESS, message, data);
    }

    /**
     * 创建成功响应
     */
    public static <T> Result<T> created(T data) {
        return new Result<>(StatusCode.CREATED, StatusCode.getDescription(StatusCode.CREATED), data);
    }

    // ========== 失败响应 ==========

    /**
     * 失败响应（使用状态码）
     */
    public static <T> Result<T> error(int code) {
        return new Result<>(code, StatusCode.getDescription(code), null);
    }

    /**
     * 失败响应（自定义消息）
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败响应（带数据）
     */
    public static <T> Result<T> error(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    // ========== 常用失败响应快捷方法 ==========

    /**
     * 参数错误
     */
    public static <T> Result<T> badRequest() {
        return error(StatusCode.BAD_REQUEST);
    }

    public static <T> Result<T> badRequest(String message) {
        return error(StatusCode.BAD_REQUEST, message);
    }

    /**
     * 未登录
     */
    public static <T> Result<T> unauthorized() {
        return error(StatusCode.UNAUTHORIZED);
    }

    /**
     * 权限不足
     */
    public static <T> Result<T> forbidden() {
        return error(StatusCode.FORBIDDEN);
    }

    /**
     * 资源不存在
     */
    public static <T> Result<T> notFound() {
        return error(StatusCode.NOT_FOUND);
    }

    /**
     * 服务器错误
     */
    public static <T> Result<T> serverError() {
        return error(StatusCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> serverError(String message) {
        return error(StatusCode.INTERNAL_SERVER_ERROR, message);
    }

    // ========== 判断方法 ==========

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this.code == StatusCode.SUCCESS;
    }

    /**
     * 判断是否失败
     */
    public boolean isError() {
        return !isSuccess();
    }
}