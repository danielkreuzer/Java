package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TraceAdvice {

    @Pointcut("execution(public * swt6.spring.basics.aop.*.*(..))")
    private static void allMethods() {
    }

    @Before("swt6.spring.basics.aop.advice.TraceAdvice.allMethods()")
    public void traceBefore(JoinPoint jp) {
        String className = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        System.out.printf("--> %s.%s%n", className, methodName);
    }

    @After("swt6.spring.basics.aop.advice.TraceAdvice.allMethods()")
    public void traceAfter(JoinPoint jp) {
        String className = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        System.out.printf("<-- %s.%s%n", className, methodName);
    }

    @Around("execution(public * swt6.spring.basics.aop.*.find*ById(..))")
    public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();

        try {
            // before
            System.out.printf("==> %s.%s%n", className, methodName);
            Object result = pjp.proceed(pjp.getArgs());

            return result;
        } finally {
            // after
            System.out.printf("<== %s.%s%n", className, methodName);

        }
    }

    @AfterThrowing(pointcut = "swt6.spring.basics.aop.advice.TraceAdvice.allMethods()", throwing = "exception")
    public void traceException(JoinPoint jp, Throwable exception) {
        String className = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();

        System.out.printf("##> %s.%s threw exception [%s]%n", className, methodName, exception);
    }
}
