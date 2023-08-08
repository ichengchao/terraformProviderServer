package terraformDemo.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import terraformDemo.utils.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Aspect
@Component
public class DemoControllerAspect {

    public Logger logger = LoggerFactory.getLogger(getClass());

    @Before(value = "execution(* terraformDemo.controller.*Controller.*(..)) and args(request, response) )")
    public void beforeAdvice(JoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response) {
        logger.info("===method: " + joinPoint.getSignature().getName());
        logger.info("===params: " + JsonUtils.toJsonString(request.getParameterMap()));
    }

}
