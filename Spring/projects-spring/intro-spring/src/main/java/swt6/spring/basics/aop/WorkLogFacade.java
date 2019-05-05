package swt6.spring.basics.aop;

import java.util.List;

public interface WorkLogFacade {
  public Employee findEmployeeById(Long id) throws EmployeeIdNotFoundException;
  public List<Employee> findAllEmployees();
}