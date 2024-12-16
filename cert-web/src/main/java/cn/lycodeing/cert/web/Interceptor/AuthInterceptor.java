package cn.lycodeing.cert.web.Interceptor;

import cn.lycodeing.cert.web.common.R;
import cn.lycodeing.cert.web.properties.AuthProperties;
import cn.lycodeing.cert.web.security.SecurityContext;
import cn.lycodeing.cert.web.utils.GsonUtil;
import cn.lycodeing.cert.web.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @author lycodeing
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {


    private final AuthProperties authProperties;

    private final JwtUtil jwtUtil;

    public AuthInterceptor(AuthProperties authProperties, JwtUtil jwtUtil) {
        this.authProperties = authProperties;
        this.jwtUtil = jwtUtil;
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
        boolean authentication = authentication(authHeader);
        if(! authentication){
            sendErrorResponse(response, 403, "认证错误");
        }
        return authentication;
    }

    private static void sendErrorResponse(HttpServletResponse response, int statusCode, String errorMessage) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);
        response.getWriter().print(GsonUtil.toJson(R.failed(statusCode, errorMessage)));
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SecurityContext.remove();
    }

    /**
     * 认证方法
     */
    private boolean authentication(String token) {
        try {
            Integer userId = jwtUtil.getUserId(token);
            SecurityContext.setUserId(userId);
        } catch (Exception e) {
            log.error("token认证失败");
            return false;
        }
        return true;

    }

}
