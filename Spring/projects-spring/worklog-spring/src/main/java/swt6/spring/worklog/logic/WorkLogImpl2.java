package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

@Service("workLog")
@Transactional
public class WorkLogImpl2 implements WorkLogFacade {

    @Autowired
    private EmployeeRepository employeeDao;

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long id) {
        Optional<Employee> employee = employeeDao.findById(id);
        return employee.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeDao.findAll();
    }
}
