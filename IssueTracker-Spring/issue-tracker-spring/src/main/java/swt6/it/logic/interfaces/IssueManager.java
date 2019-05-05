package swt6.it.logic.interfaces;

import com.sun.istack.Nullable;
import swt6.it.domain.Employee;
import swt6.it.domain.Issue;
import swt6.it.domain.IssueState;
import swt6.it.domain.Project;

import java.util.List;

public interface IssueManager {
    Issue addIssue(Issue issue, Project project);
    Issue syncIssue(Issue issue);
    Issue changeProject(Issue issue, Project project);
    Issue changeIssueState(Issue issue, @Nullable Employee employee, @Nullable Long estimatedTime, IssueState issueState);
    List<Issue> findByProject(Project project);
    List<Issue> findByStateAndProject(IssueState issueState, Project project);
    List<Issue> findByEmployee(Employee employee);
    List<Issue> findAll();
}
