package cn.lycodeing.cert.task.context;

import cn.lycodeing.cert.task.enums.PostProcessorTypeEnum;
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


    public PostProcessorTypeEnum type;
}
