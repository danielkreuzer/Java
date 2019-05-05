package swt6.spring.basics.aop.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.aop.EmployeeIdNotFoundException;
import swt6.spring.basics.aop.WorkLogFacade;
import swt6.util.PrintUtil;

public class AopTest {

    private static void testAOP(String configFileName) {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(configFileName)) {
            WorkLogFacade workLog = factory.getBean(WorkLogFacade.class);
            System.out.println("worklog.getClass=" + workLog.getClass());
            System.out.println("worklog.superclass=" + workLog.getClass().getSuperclass());
            try {
                workLog.findAllEmployees();
                workLog.findEmployeeById(1L);
                workLog.findEmployeeById(99L);
            } catch (EmployeeIdNotFoundException e) {
            }
        }
    }

    public static void main(String[] args) {
        /*PrintUtil.printTitle("testAOP (config based)", 60);
        testAOP("swt6/spring/basics/aop/test/applicationContext-xml-config.xml");
        PrintUtil.printSeparator(60);*/

         PrintUtil.printTitle("testAOP (annotation based)", 60);
         testAOP("swt6/spring/basics/aop/test/applicationContext-annotation-config.xml");
         PrintUtil.printSeparator(60);
    }

}
