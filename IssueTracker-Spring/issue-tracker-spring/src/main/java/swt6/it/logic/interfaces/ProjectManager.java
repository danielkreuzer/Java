package swt6.it.logic.interfaces;

import org.springframework.data.util.Pair;
import swt6.it.domain.Employee;
import swt6.it.domain.Issue;
import swt6.it.domain.Project;

import java.util.List;
import java.util.Set;

public interface ProjectManager {
    Project syncProject(Project project);
    Project addEmployeeToProject(Project project, Employee employee);
    Project removeEmployeeFormProject(Project project, Employee employee);
    List<Project> findAllProjects();
    Project findById(Long id);

    Long TimeInvested(Issue issue);

    Long TimeInvested(Issue issue, Employee employee);

    Long employeeNeedsToInvest(Project project, Employee employee);
    Long employeeInvested(Project project, Employee employee);
    Long timeNeededToInvest(Project project);
    Long timeInvested(Project project);
    Pair<Long, Set<Employee>> getCriticalPath(Project project);
}
