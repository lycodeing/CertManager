package cn.lycodeing.cert.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lycodeing
 */

@Getter
@AllArgsConstructor
public enum InstanceStatusEnum {

    WAITING(1, "等待处理"),

    RUNNER(2, "运行中"),

    SUCCESS(3, "成功"),

    FAIL(4, "失败");

    private final int code;

    private final String message;
}
