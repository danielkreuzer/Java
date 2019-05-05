package swt6.spring.basics.ioc.logic.javaconfig;

import org.springframework.stereotype.Service;
import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("workLog")
@SuppressWarnings("Duplicates")
public class WorkLogImpl implements WorkLogFacade {

    private void init() {
        employees.put(1L, new Employee(1L, "Bill", "Gates"));
        employees.put(2L, new Employee(2L, "James", "Goslin"));
        employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
    }

    @Inject @Log(Log.Type.STANDARD)
    private Logger logger;
    private Map<Long, Employee> employees = new HashMap<Long, Employee>();

    public WorkLogImpl() {
        init();
    }

    public WorkLogImpl(Logger logger) {
        this.logger = logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Employee findEmployeeById(Long id) {
        Employee empl = employees.get(id);
        logger.log("findEmployeeById(" + id + "): " + empl);
        return employees.get(id);
    }

    @Override
    public List<Employee> findAllEmployees() {
        logger.log("findAllEmployees");
        return new ArrayList<Employee>(employees.values());
    }
}
