package swt6.it.logic.interfaces;

import swt6.it.domain.Employee;
import swt6.it.domain.Project;

import java.util.List;

public interface EmployeeManager {
    public Employee syncEmployee(Employee employee);
    public void removeEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployeesWithProject();
    List<Employee> findByProject(Project project);
}
