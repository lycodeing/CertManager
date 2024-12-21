package cn.lycodeing.cert.web.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author lycodeing
 */
@Data
@Builder
public class DnsOptionDTO {

    private Integer dnsId;

    private String providerName;
}
