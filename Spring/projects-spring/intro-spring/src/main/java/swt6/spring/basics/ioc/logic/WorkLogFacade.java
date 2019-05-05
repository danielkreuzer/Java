package swt6.spring.basics.ioc.logic;

import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.util.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public interface WorkLogFacade {
    Employee findEmployeeById(Long id);

    List<Employee> findAllEmployees();
}
