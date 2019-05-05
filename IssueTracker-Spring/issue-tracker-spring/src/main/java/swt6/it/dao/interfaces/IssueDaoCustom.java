package swt6.it.dao.interfaces;

import com.sun.istack.Nullable;
import swt6.it.domain.Employee;
import swt6.it.domain.Issue;
import swt6.it.domain.IssueState;
import swt6.it.domain.Project;

import java.util.List;

public interface IssueDaoCustom {
    List<Issue> findByStateAndProject(IssueState issueState, Project project);
    List<Issue> findByProject(Project project);
    List<Issue> findByEmployee(Employee employee);
}
