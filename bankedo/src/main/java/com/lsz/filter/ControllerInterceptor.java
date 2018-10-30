package com.lsz.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 拦截器：记录用户操作日志，检查用户是否登录……
 *
 * @author XuJijun
 */
@Aspect
@Component
public class ControllerInterceptor {
    protected static Logger log = LoggerFactory.getLogger(ControllerInterceptor.class);

    @Pointcut("execution(* com.lsz.web..*(..))")
// and @annotation(org.springframework.web.bind.annotation.RequestMapping)
    public void controllerMethodPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Before("controllerMethodPointcut()") // @Around(    拦截
    public void Interceptor(JoinPoint pjp) {
        Object[] objArr = pjp.getArgs();
        StringBuffer sb = new StringBuffer();
        if (objArr != null && objArr.length > 0) {
            for (Object obj : objArr) {
                if (sb.length() > 0) sb.append(" | ");
                if(obj != null){
                    sb.append(obj.toString());
                }
            }

        }

        log.warn("#### Begin  #### {} Param[{}]", pjp.toString(), sb);

    }

    @AfterReturning(returning = "rvt", value = "controllerMethodPointcut()")
    public Object InterceptorAfter(JoinPoint pjp, Object rvt) {
        log.warn("#### Finish #### {}", pjp.toString());
//        log.warn("#### Finish #### {} return[{}]", pjp.toShortString(), rvt);
        return rvt;
    }
//    @Around("controllerMethodPointcut()")
//    public void around(ProceedingJoinPoint pjp) throws Throwable {
//        Object[] objArr = pjp.getArgs();
//        StringBuffer sb = new StringBuffer();
//        if (objArr != null && objArr.length > 0) {
//            for (Object obj : objArr) {
//                if (sb.length() > 0) sb.append(" | ");
//                sb.append(obj.toString());
//            }
//
//        }
//        log.info("#### Begin #### {} Param[{}]", pjp.toString(), sb);
//        Object obj = pjp.proceed();
//        log.info("#### Finish #### {} Param[{}]",pjp.toShortString(),obj);
//    }

}  