package cn.lycodeing.cert.web.common;

import lombok.Data;

@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, 200, "success");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, 200, "success");
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, 200, msg);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, 500, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, 500, "failed");
    }

    private static <T> R<T> restResult(T data, Integer code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
