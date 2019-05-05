package swt6.spring.basics.ioc.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.logic.factorybased.WorkLogImpl;
import swt6.spring.basics.ioc.logic.javaconfig.WorkLogConfig;
import swt6.util.PrintUtil;

@SuppressWarnings("Duplicates")
public class IocTest {

    private static void testSimple() {
        WorkLogImpl workLog = new WorkLogImpl();
        workLog.findAllEmployees();
        workLog.findEmployeeById(1L);
        workLog.findEmployeeById(99L);
    }

    private static void testXmlConfig() {
        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-xml-config.xml")) {
            WorkLogFacade workLog1 = factory.getBean("workLog-setter-injected", WorkLogFacade.class);
            workLog1.findAllEmployees();
            workLog1.findEmployeeById(1L);
            workLog1.findEmployeeById(99L);

            PrintUtil.printSeparator(60, '-');
            WorkLogFacade workLog2 = factory.getBean("workLog-constructor-injected", WorkLogFacade.class);
            workLog2.findAllEmployees();
            workLog2.findEmployeeById(1L);
            workLog2.findEmployeeById(99L);
        }
    }

    private static void testAnnotationConfig() {
        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-annotation-config.xml")) {
            WorkLogFacade workLog2 = factory.getBean("workLog", WorkLogFacade.class);
            workLog2.findAllEmployees();
            workLog2.findEmployeeById(1L);
            workLog2.findEmployeeById(99L);
        }
    }

    private static void testJavaConfig() {
        try (AbstractApplicationContext factory =
                     new AnnotationConfigApplicationContext(WorkLogConfig.class)) {
            WorkLogFacade workLog2 = factory.getBean("workLog", WorkLogFacade.class);
            workLog2.findAllEmployees();
            workLog2.findEmployeeById(1L);
            workLog2.findEmployeeById(99L);
        }
    }

    public static void main(String[] args) {
        PrintUtil.printTitle("testSimple", 60);
        testSimple();
        PrintUtil.printSeparator(60);
        PrintUtil.printTitle("testXmlConfig", 60);
        testXmlConfig();
        PrintUtil.printSeparator(60);
        PrintUtil.printTitle("testAnnotationConfig", 60);
        testAnnotationConfig();
        PrintUtil.printSeparator(60);
        PrintUtil.printTitle("testJavaConfig", 60);
        testJavaConfig();
        PrintUtil.printSeparator(60);
    }
}
