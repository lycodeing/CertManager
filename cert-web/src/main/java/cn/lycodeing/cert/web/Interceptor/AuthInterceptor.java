package cn.lycodeing.cert.web.Interceptor;

import cn.lycodeing.cert.web.Properties.AuthProperties;
import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.utils.GsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {


    private final AuthProperties authProperties;

    public AuthInterceptor(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (authProperties.getIgnoreUrls().contains(request.getRequestURI())) {
            log.debug("Ignore Url: {}", request.getRequestURI());
            return true;
        }
        // 查看请求头Authorization是否存在和值是否有效
        String authHeader = request.getHeader(authProperties.getAuthHeader());
        if (StringUtils.isEmpty(authHeader)) {
            sendErrorResponse(response, 401, "未授权");
            return false;
        }
        if (!authHeader.startsWith(authProperties.getTokenPrefix())) {
            sendErrorResponse(response, 403, "认证错误");
        }
        return true;
    }

    private static void sendErrorResponse(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);
        response.getWriter().print(GsonUtil.toJson(R.failed(statusCode, errorMessage)));
    }

}
