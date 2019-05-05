package swt6.spring.basics.ioc.util;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Qualifier
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    Type value() default Type.STANDARD;

    public enum Type {
        STANDARD,
        FILE
    }
}
