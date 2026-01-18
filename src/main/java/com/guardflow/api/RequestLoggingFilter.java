package com.guardflow.api;

import com.guardflow.api.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    private final RedisTemplate<String,Object> redisTemplate;
    public RequestLoggingFilter(RateLimitService rateLimitService, RedisTemplate<String, Object> redisTemplate){
        this.rateLimitService = rateLimitService;
        this.redisTemplate = redisTemplate;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        String path = request.getRequestURI();
        if (!rateLimitService.isAllowed("ip:" + ip)) {
            Long ttl = redisTemplate.getExpire("rate_limit:ip:" + ip);
            response.setHeader("Retry-After", String.valueOf(ttl));
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("""
{
  "error": "RATE_LIMIT_EXCEEDED",
  "message": "Too many requests. Please try again later."
}
""");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
