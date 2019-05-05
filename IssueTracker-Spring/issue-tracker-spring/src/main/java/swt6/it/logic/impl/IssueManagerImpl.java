package swt6.it.logic.impl;

import com.sun.istack.Nullable;
import org.springframework.transaction.annotation.Transactional;
import swt6.it.dao.interfaces.IssueDao;
import swt6.it.dao.interfaces.LogbookEntryDao;
import swt6.it.domain.*;
import swt6.it.logic.interfaces.IssueManager;

import java.util.List;

@Transactional
public class IssueManagerImpl implements IssueManager {
    private IssueDao issueDao;
    private LogbookEntryDao logbookEntryDao;

    public void setIssueDao(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    public void setLogbookEntryDao(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }

    @Override
    public Issue addIssue(Issue issue, Project project) {
        if(project == null) {
            return null;
        }

        issue.addProject(project);
        return issueDao.save(issue);
    }

    @Override
    public Issue syncIssue(Issue issue) {
        return issueDao.save(issue);
    }

    @Override
    public Issue changeProject(Issue issue, Project project) {
        return addIssue(issue, project);
    }

    /*
    Änderung des Issue-Status.
    In diesem Zuge kann dem Issue ein Verantwortlicher
    zugewiesen werden. Diese Zuweisung kann nicht mehr
    rückgängig gemacht werden, sobald der Verantwortliche
    bereits einen Tagebucheintrag erstellt hat, der diesem
    Issue zugeordnet wurde. Spätestens bei der Zuweisung eines
    Verantwortlichen muss auch eine Zeitschätzung für die Bearbeitung
    des Issues abgegeben werden.
     */

    @Override
    public Issue changeIssueState(Issue issue, @Nullable Employee employee, @Nullable Long estimatedTime, IssueState issueState) {
        // check if Employee is valid
        if(employee != null) {
            if (issue.getEmployee() != null && logbookEntryDao.findByEmployee(issue.getEmployee()) != null) {
                return null;
            }
            if(issue.getEstimatedTime() == null) {
                if (estimatedTime == null) return null;
            }
        }

        if(estimatedTime != null) {
            issue.setEstimatedTime(estimatedTime);
        }

        issue.setState(issueState);
        return issueDao.save(issue);
    }

    @Override
    public List<Issue> findByProject(Project project) {
        return issueDao.findByProject(project);
    }

    @Override
    public List<Issue> findByStateAndProject(IssueState issueState, Project project) {
        return issueDao.findByStateAndProject(issueState, project);
    }

    @Override
    public List<Issue> findByEmployee(Employee employee) {
        return issueDao.findByEmployee(employee);
    }

    @Override
    public List<Issue> findAll() {
        return issueDao.findAll();
    }
}
