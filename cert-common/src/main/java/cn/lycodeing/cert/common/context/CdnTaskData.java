package cn.lycodeing.cert.common.context;

import cn.lycodeing.cert.common.enums.PostProcessorTypeEnum;
import lombok.Data;

@Data
public class CdnTaskData {
    /**
     * accessKey
     */
    private String accessKey;

    /**
     * accessSecret
     */
    private String accessSecret;

    /**
     * 域名
     */
    private String domain;


    /**
     * 需要更新的cdn域名
     */
    private String cdnDomain;

    /**
     * 别名
     */
    private String alisaName;


    /**
     * 处理器类型
     */
    public PostProcessorTypeEnum type;
}
