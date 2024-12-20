package cn.lycodeing.cert.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author lycodeing
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private Integer id;

    /**
     * 申请证书的域名
     */
    private String domain;

    /**
     * 联系人邮箱地址
     */
    private String email;

    /**
     * 证书提供商类型
     */
    private String certProvider;

    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 要申请证书的域名列表
     */
    private List<String> domainsList;

}
