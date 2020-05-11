package com.ziffer.questionnaire.intercepter;

import com.ziffer.questionnaire.utils.RedisUtils;
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
    private final String httpParamUsername = "username";
//    //鉴权失败后返回的错误信息，默认为401 unauthorized
//    private String unauthorizedErrorMessage = "401 unauthorized";
//    //鉴权失败后返回的HTTP错误码，默认为401
//    private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;
//    //存放登录用户模型Key的Request Key
//    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    /**
     * @Description 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理；
     * @Data 2020-05-11
     * @Version  1.0
     */
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
            String transUsername = request.getParameter(httpParamUsername);
            String username;
            PrintWriter writer;
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            if (token != null && token.length() != 0) {
                username = redisUtils.get(token);
                if(username==null){
                    String error = "token信息有误";
                    writer = response.getWriter();
                    writer.print(error);
                    return false;
                }else if(!username.equals(transUsername)){
                    String error = "token信息有误";
                    writer = response.getWriter();
                    writer.print(error);
                    return false;
                }
            }else{
                String error = "token信息有误";
                writer = response.getWriter();
                writer.print(error);
                return false;
            }
            return true;
        }
        return true;
    }
}
