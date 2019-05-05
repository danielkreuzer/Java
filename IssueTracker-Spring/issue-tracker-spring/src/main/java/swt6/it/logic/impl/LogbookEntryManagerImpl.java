package swt6.it.logic.impl;

import com.sun.istack.Nullable;
import org.springframework.transaction.annotation.Transactional;
import swt6.it.dao.interfaces.IssueDao;
import swt6.it.dao.interfaces.LogbookEntryDao;
import swt6.it.domain.Issue;
import swt6.it.domain.LogbookEntry;
import swt6.it.domain.Project;
import swt6.it.logic.interfaces.LogbookEntryManager;

@Transactional
public class LogbookEntryManagerImpl implements LogbookEntryManager {
    private LogbookEntryDao logbookEntryDao;
    private IssueDao issueDao;

    public void setLogbookEntryDao(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }

    public void setIssueDao(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    @Override
    public LogbookEntry addLogbookEntry(LogbookEntry logbookEntry, @Nullable Issue issue, @Nullable Project project) {
        // check if issue is valid
        if(issue != null) {
            if(issue.getProject() == null && project == null) {
                return null;
            }
        }

        if(project != null) {
            issue.addProject(project);
            issue = issueDao.save(issue);
        }

        if(issue != null) {
            logbookEntry.addIssue(issue);
        }
        return logbookEntryDao.save(logbookEntry);
    }
}
