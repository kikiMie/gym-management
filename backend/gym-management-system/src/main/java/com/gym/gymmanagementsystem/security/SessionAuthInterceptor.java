package com.gym.gymmanagementsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
/**
 * 会话认证拦截器
 * 用于验证用户和管理员的登录状态，保护受保护的 API 接口
 */
@Component
public class SessionAuthInterceptor implements HandlerInterceptor {
//传递数据的实际对象
    private final ObjectMapper objectMapper;

    public SessionAuthInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    /**
     * 判断当前请求 URI 是否属于需要会员（User）权限的接口
     *
     * @param uri 请求的 URI
     * @return 如果是会员接口返回 true，否则返回 false
     */
    private boolean isUserApi (String uri){
        return uri != null &&(
                uri.equals("/api/toUserMain")
                        || uri.startsWith("/api/user/")
                        || uri.startsWith("/api/chat/")
                        || uri.equals("/api/userMain")

        );
    }
    /**
     * 判断当前请求 URI 是否属于公开接口（无需登录即可访问）
     *
     * @param uri 请求的 URI
     * @return 如果是公开接口返回 true，否则返回 false
     */
    private boolean isPublicApi(String uri) {
        return uri != null && (
                uri.equals("/api/adminLogin")
                        || uri.equals("/api/userLogin")
                        || uri.equals("/api/logout")
        );
    }
    /**
     * 在请求处理之前进行身份验证
     *
     * @param request 当前 HTTP 请求
     * @param response 当前 HTTP 响应
     * @param handler 选择的处理器
     * @return 如果验证通过返回 true 继续处理，否则返回 false 中断请求
     * @throws Exception 处理过程中的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }

        String uri = request.getRequestURI();
        // 只拦截 /api 开头的请求
        if (uri == null || !uri.startsWith("/api")){
            return true;
        }
        // 放行公开的登录/登出接口
        if (isPublicApi(uri)){
            return true;
        }
        // 根据接口类型确定需要的 Session Key（user 或 admin）
        boolean requireUser = isUserApi(uri);
        String requiredSessionKey = requireUser ? "user" : "admin";

        HttpSession session = request.getSession(false);
        // 检查 Session 中是否存在对应的身份标识
        if (session != null && session.getAttribute(requiredSessionKey) != null){
            return true;
        }

        // 未通过验证，返回 401 未授权响应
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> resp = new HashMap<>();
        resp.put("success", false);
        resp.put("message", "未登录");
        response.getWriter().write(objectMapper.writeValueAsString(resp));
        return false;

    }
}
