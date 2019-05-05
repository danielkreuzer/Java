package swt6.spring.basics.hello;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GreetingClient {

    public static void main(String[] args) {
        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext("swt6/spring/basics/hello/greetingsConfig.xml")) {
            var greetingService = factory.getBean("greetingService", GreetingService.class);
            greetingService.sayHello();
        } // factory.close
    }
}
