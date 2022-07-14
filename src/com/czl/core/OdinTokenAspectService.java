package com.czl.core;


import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;

import com.czl.annotation.OdinToken;
import com.czl.constants.OdinConfig;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 该切面类用于对标记了注解 {@link OdinToken} 的方法进行token强校验
 * OdinToken注解需标记 {@link com.czl.service} 包下的方法上
 *
 * @author: caizelin
 * @date: 2021/10/14 11:05
 */
@Component
@Aspect
public class OdinTokenAspectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.kanfangjilu.api.aspectj.OdinTokenAspectService.class);
    @Autowired
    private TokenService tokenService;

    //  位于包 com.czl.service下的类的方法 该方法若带有OdinToken注解会被切入
    @Pointcut("execution(* com.czl.service.impl.*.*(..)) && @annotation(com.czl.annotation.OdinToken)")
    private void odinToke() {
    }

    @Before("odinToke()")
    public void handleOdinToken(JoinPoint joinPoint) {
        LOGGER.info("handleOdinToken method {} start", joinPoint.getSignature().getName());
        // 1. 获取到方法签名
        Signature signature = joinPoint.getSignature();
        // 2. 强转方法签名
        MethodSignature methodSignature = (MethodSignature) signature;
        // 3. 获取当前目标方法
        Method targetMethod = methodSignature.getMethod();
        // 4. 获取当前目标方法上标记的注解@OdinToken
        OdinToken odinToken = targetMethod.getAnnotation(OdinToken.class);
        // 5. 判断注解是否存在
        // 注解不存在，退出
        if (null == odinToken) {
            LOGGER.error("handleOdinToken method {} odinToken is null", targetMethod.getName());
            return;
        }
        // 注解存在 获取目标方法运行时使用的参数
        Object[] args = joinPoint.getArgs();
        // 遍历参数，获取入参类型为HttpServletRequest的参数
        for (int index = 0; index < args.length; index++) {
            if (args[index] instanceof HttpServletRequest) {
                HttpServletRequest req = (HttpServletRequest) args[index];
                // Token校验
                handleTokeAuth(odinToken.value(), req);
                break;
            }
            // 遍历到最后一个入参还未找到HttpServletRequest参数
            if (index == args.length - 1) {
                LOGGER.error("handleOdinToken method {} without param httpServletRequest", targetMethod.getName());
                throw new Exception();
            }
        }
    }

    /**
     * Token校验
     *
     * @param config
     * @param request
     */
    private void handleTokeAuth(OdinConfig config, HttpServletRequest request) throws Exception {
        LOGGER.info("handleTokeAuth start token authentication");
        boolean match = tokenMatch(config, request);
        if (!match) {
           throw new Exception();
        }
    }

    public boolean tokenMatch(OdinConfig config, String userToken) {
        // 获取端传递的token
        String userToken = request.getHeader(Header.X_TOKEN);
        return config.getDefaultValue().equals(config);
    }

}
