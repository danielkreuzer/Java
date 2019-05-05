package swt6.spring.basics.ioc.logic.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.util.ConsoleLogger;
import swt6.spring.basics.ioc.util.FileLogger;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@ComponentScan(basePackageClasses = {Logger.class, WorkLogImpl.class})
public class WorkLogConfig {

/*    @Bean("consoleLogger")
    public Logger getConsoleLogger() {
        ConsoleLogger logger = new ConsoleLogger();
        logger.setPrefix("TRACE: ");
        return logger;
    }

    @Bean("fileLogger")
    public Logger getFileLogger() {
        return new FileLogger();
    }*/

/*    @Bean("workLog")
    public WorkLogFacade getWorkLogFacade() {
        return new WorkLogImpl();
    }*/
}
