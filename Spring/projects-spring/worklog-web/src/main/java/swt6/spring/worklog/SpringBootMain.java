package swt6.spring.worklog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/* Version 1, 2
@Configuration
@ComponentScan
public class SpringBootMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }
}
*/
//Adds enable auto configuration
//Components are searched automatically
@SpringBootApplication
public class SpringBootMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class, args);
    }
}
