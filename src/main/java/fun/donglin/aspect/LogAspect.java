package fun.donglin.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class LogAspect {

    private final Logger LOGGER = LoggerFactory.getLogger( this.getClass() );

    @Pointcut("execution(* fun.donglin.web.admin.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        // 获取 url, ip
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull( attributes ).getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();

        // 获取 classMethod, args
        Signature signature = joinPoint.getSignature();
        String classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        Object[] args = joinPoint.getArgs();

        // 构造 RequestLog 对象
        RequestLog requestLog = new RequestLog( url, ip, classMethod, args );

        LOGGER.info( "Request : {}", requestLog );
    }

    @After("log()")
    public void doAfter() {
//        LOGGER.info( "------------- after ---------------" );
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Object result) {
        LOGGER.info( "Result : {}", result );
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString( args ) +
                    '}';
        }
    }
}
