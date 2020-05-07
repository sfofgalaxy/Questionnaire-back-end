package com.ziffer.questionnaire.intercepter;

import com.ziffer.questionnaire.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Service
public class TokenIntercepter implements HandlerInterceptor {
    @Resource
    private RedisUtils redisUtils;
    private final String httpHeaderName = "token";
    //鉴权失败后返回的错误信息，默认为401 unauthorized
    private String unauthorizedErrorMessage = "401 unauthorized";

    //鉴权失败后返回的HTTP错误码，默认为401
    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

    //存放登录用户模型Key的Request Key
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 如果打上了AuthToken注解则需要验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            String token = request.getHeader(httpHeaderName);
            String username = "";
            if (token != null && token.length() != 0) {

                username = redisUtils.get(token);
            }
            if (username != null && !username.trim().equals("")) {
                return true;
            } else {

            }
        }

        request.setAttribute(REQUEST_CURRENT_KEY, null);

        return true;
    }
}
