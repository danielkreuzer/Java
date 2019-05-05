package swt6.it.logic.impl;

import org.springframework.transaction.annotation.Transactional;
import swt6.it.dao.interfaces.EmployeeDao;
import swt6.it.domain.Employee;
import swt6.it.domain.Project;
import swt6.it.logic.interfaces.EmployeeManager;

import java.util.List;
import java.util.Optional;

@Transactional
public class EmployeeManagerImpl implements EmployeeManager {
    private EmployeeDao employeeDao;

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    @Override
    public void removeEmployee(Employee employee) {
        employeeDao.delete(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee =  employeeDao.findById(id);
        return employee.isEmpty() ? null : employee.get();
    }

    @Override
    public List<Employee> getAllEmployeesWithProject() {
        List<Employee> employees = employeeDao.findAll();
        employees.removeIf(employee -> employee.getProjects() == null);
        return employees;
    }

    @Override
    public List<Employee> findByProject(Project project) {
        return employeeDao.findByProjects(project);
    }
}
