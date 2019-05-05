package swt6.it.logic.impl;

import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.Transactional;
import swt6.it.dao.interfaces.IssueDao;
import swt6.it.dao.interfaces.LogbookEntryDao;
import swt6.it.dao.interfaces.ProjectDao;
import swt6.it.domain.Employee;
import swt6.it.domain.Issue;
import swt6.it.domain.LogbookEntry;
import swt6.it.domain.Project;
import swt6.it.logic.interfaces.ProjectManager;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
public class ProjectManagerImpl implements ProjectManager {
    private ProjectDao projectDao;
    private LogbookEntryDao logbookEntryDao;

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public void setLogbookEntryDao(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }

    @Override
    public Project syncProject(Project project) {
        return projectDao.save(project);
    }

    @Override
    public Project addEmployeeToProject(Project project, Employee employee) {
        project.addEmployee(employee);
        return projectDao.save(project);
    }

    @Override
    public Project removeEmployeeFormProject(Project project, Employee employee) {
        project.removeEmployee(employee);
        return projectDao.save(project);
    }

    @Override
    public List<Project> findAllProjects() {
        return projectDao.findAll();
    }

    @Override
    public Project findById(Long id) {
        Optional<Project> project = projectDao.findById(id);
        return project.orElse(null);
    }

    @Override
    public Long TimeInvested(Issue issue) {
        Long time = 0L;
        for(LogbookEntry logbookEntry : issue.getEntries()) {
            Duration duration = Duration.between(logbookEntry.getStartTime(), logbookEntry.getEndTime());
            time = duration.toMinutes();
        }
        return time;
    }

    @Override
    public Long TimeInvested(Issue issue, Employee employee) {
        Long time = 0L;
        for(LogbookEntry logbookEntry : logbookEntryDao.findByEmployeeAndIssue(employee, issue)) {
            Duration duration = Duration.between(logbookEntry.getStartTime(), logbookEntry.getEndTime());
            time = duration.toMinutes();
        }
        return time;
    }

    @Override
    public Long employeeNeedsToInvest(Project project, Employee employee) {
        Set<Issue> issues = project.getIssues();
        Long time = 0L;

        if(issues != null) {
            for (Issue issue : issues) {
                Long timeSpent = TimeInvested(issue, employee);
                if (timeSpent < issue.getEstimatedTime()) {
                    time += issue.getEstimatedTime() - timeSpent;
                }
            }
        }
        return time;
    }

    @Override
    public Long employeeInvested(Project project, Employee employee) {
        Set<Issue> issues = project.getIssues();
        Long time = 0L;
        if(issues != null) {
            for (Issue issue : issues) {
                time += TimeInvested(issue, employee);
            }
        }
        return time;
    }

    @Override
    public Long timeNeededToInvest(Project project) {
        Set<Issue> issues = project.getIssues();
        Long time = 0L;
        for(Issue issue : issues) {
            Long timeSpent = TimeInvested(issue);
            if(timeSpent < issue.getEstimatedTime()) {
                time += issue.getEstimatedTime() - timeSpent;
            }
        }
        return time;
    }

    @Override
    public Long timeInvested(Project project) {
        Set<Issue> issues = project.getIssues();
        Long time = 0L;
        for(Issue issue : issues) {
            time += TimeInvested(issue);
        }
        return time;
    }

    @Override
    public Pair<Long, Set<Employee>> getCriticalPath(Project project) {
        Long longestTime = 0L;
        Set<Employee> employees = new HashSet<>();

        for(Employee employee : project.getEmployees()) {
            Long time = employeeNeedsToInvest(project, employee);
            if(time > longestTime) {
                employees.clear();
                employees.add(employee);
                longestTime = time;
            }

            if(longestTime != 0 && time.equals(longestTime)) {
                employees.add(employee);
            }
        }

        return Pair.of(longestTime, employees);
    }
}
