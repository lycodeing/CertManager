package cn.lycodeing.cert.web.controller;

import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.domain.DnsProvider;
import cn.lycodeing.cert.web.dto.request.DnsProvidersDTO;
import cn.lycodeing.cert.web.dto.response.DnsOptionDTO;
import cn.lycodeing.cert.web.security.SecurityContext;
import cn.lycodeing.cert.web.service.DnsProvidersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * dns接口
 * @author lycodeing
 */
@Slf4j
@RestController
@RequestMapping("/api/dns")
public class DnsProvidersController {

    private final DnsProvidersService dnsProvidersService;

    public DnsProvidersController(DnsProvidersService dnsProvidersService) {
        this.dnsProvidersService = dnsProvidersService;
    }


    @PostMapping
    public R<Void> add(@RequestBody @Validated DnsProvidersDTO request) {
        log.info("添加DNS提供商: {}", request);
        dnsProvidersService.save(DnsProvider.builder()
                .userId(SecurityContext.getUserId())
                .providerName(request.getProviderName())
                .providerType(request.getProviderType())
                .description(request.getDescription())
                .accessKey(request.getAccessKey())
                .secretKey(request.getSecretKey())
                .createTime(LocalDateTime.now())
                .build());
        return R.ok();
    }



    @GetMapping("/options")
    public R<List<DnsOptionDTO>> list(){
        return R.ok(dnsProvidersService.list().stream()
                .map(dns -> DnsOptionDTO.builder()
                        .dnsId(dns.getId())
                        .providerName(dns.getProviderName())
                        .build()).toList());
    }



    @DeleteMapping
    public R<Void> delete() {

        return R.ok();
    }
}
