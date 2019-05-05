package swt6.it.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import swt6.it.domain.Employee;
import swt6.it.domain.Project;
import swt6.it.logic.interfaces.EmployeeManager;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"applicationContextTest.xml"})
public class EmployeeManagerImplTest {

    @Autowired
    private EmployeeManager employeeManager;

    @Test
    public void syncEmployee() {
        Employee employee =
                new Employee("Daniel", "Tester",
                        LocalDate.of(1996, 1, 1));

        employee = employeeManager.syncEmployee(employee);
        assertNotNull(employee.getId());
        assertNotNull(employeeManager.getEmployeeById(employee.getId()));
    }

    @Test
    public void removeEmployee() {
        Employee employee =
                new Employee("Daniel", "Test2",
                        LocalDate.of(1996, 1, 1));

        employee = employeeManager.syncEmployee(employee);
        assertNotNull(employeeManager.getEmployeeById(employee.getId()));
        Long id = employee.getId();
        employeeManager.removeEmployee(employee);
        assertNull(employeeManager.getEmployeeById(id));
    }

    @Test
    public void getAllEmployees() {
        Employee employee =
                new Employee("Daniel", "Test3",
                        LocalDate.of(1996, 1, 1));
        Employee employee1 =
                new Employee("Daniel", "Test4",
                        LocalDate.of(1996, 1, 1));
        employee = employeeManager.syncEmployee(employee);
        employee1 = employeeManager.syncEmployee(employee1);

        assertTrue(employeeManager.getAllEmployees().size() > 1);
    }

    @Test
    public void getEmployeeById() {
        Employee employee =
                new Employee("Daniel", "Test8",
                        LocalDate.of(1996, 1, 1));
        employee = employeeManager.syncEmployee(employee);

        assertEquals(employeeManager.getEmployeeById(employee.getId()).getLastName(), employee.getLastName());
    }

    @Test
    public void getAllEmployeesWithProject() {
        Project project = new Project("TestProject");
        Employee employee =
                new Employee("Daniel", "Test9",
                        LocalDate.of(1996, 1, 1));
        employee.addProject(project);
        employee = employeeManager.syncEmployee(employee);
        assertTrue(employeeManager.getAllEmployeesWithProject().size() > 0);
    }

    @Test
    public void findByProject() {
        Project project = new Project("TestProject1");
        Employee employee =
                new Employee("Daniel", "Test10",
                        LocalDate.of(1996, 1, 1));
        employee.addProject(project);
        employee = employeeManager.syncEmployee(employee);
        assertEquals(employeeManager.findByProject(project).get(0).getLastName(), employee.getLastName());
    }
}