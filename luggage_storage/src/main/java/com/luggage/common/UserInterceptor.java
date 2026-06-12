package com.luggage.common;

import com.luggage.util.JwtUtil;
import com.luggage.util.UserContext;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            response.setStatus(401);
            response.getWriter().write("未提供认证令牌");
            return false;
        }
        
        String token = extractToken(authHeader);
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            response.getWriter().write("认证令牌格式错误");
            return false;
        }
        
        Claims claims = JwtUtil.parseToken(token);
        if (claims == null) {
            response.setStatus(401);
            response.getWriter().write("无效的认证令牌");
            return false;
        }
        
        Object role = claims.get("用户角色");
        if (role == null || !"user".equals(role.toString())) {
            response.setStatus(403);
            response.getWriter().write("权限不足");
            return false;
        }
        
        Long userId = JwtUtil.getIdFromToken(token);
        if (userId == null) {
            response.setStatus(401);
            response.getWriter().write("无效的用户ID");
            return false;
        }
        
        UserContext.setUserId(userId);
        return true;
    }

    private String extractToken(String authHeader) {
        if (authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContext.clear();
    }
}
