package swt6.it.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import swt6.it.domain.*;
import swt6.it.logic.interfaces.IssueManager;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"applicationContextTest.xml"})
public class IssueManagerImplTest {

    @Autowired
    private IssueManager issueManager;

    @Test
    public void addIssue() {
        Project project = new Project("IssueTestProject1");
        Issue issue1 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 120L, 0.0);
        issue1 = issueManager.addIssue(issue1, project);
        assertNotNull(issue1.getId());
        assertNotNull(issue1.getProject());
    }

    @Test
    public void syncIssue() {
        Issue issue = new Issue(IssueState.CLOSED, IssuePriority.NORMAL, 119L, 0.0);
        issue = issueManager.syncIssue(issue);
        assertNotNull(issue.getId());
    }

    @Test
    public void changeProject() {
        Project project = new Project("IssueTestProject2");
        Issue issue1 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 110L, 0.0);
        issue1 = issueManager.addIssue(issue1, project);
        assertNotNull(issue1.getId());
        assertNotNull(issue1.getProject());
        Project project1 = new Project("IssueTestProject3");
        issue1 = issueManager.changeProject(issue1, project1);
        assertEquals(issue1.getProject().getTitle(), project1.getTitle());
    }

    @Test
    public void changeIssueState() {
        Issue issue = new Issue(IssueState.CLOSED, IssuePriority.NORMAL, 519L, 0.0);
        issue = issueManager.syncIssue(issue);
        assertSame(issue.getState(), IssueState.CLOSED);
        issue = issueManager.changeIssueState(issue, null ,null, IssueState.OPEN);
        assertSame(issue.getState(), IssueState.OPEN);
    }

    @Test
    public void findByProject() {
        Project project = new Project("IssueTestProject6");
        Issue issue1 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 156L, 0.0);
        issue1 = issueManager.addIssue(issue1, project);
        assertNotNull(issue1.getId());
        assertNotNull(issue1.getProject());

        assertEquals(issueManager.findByProject(project).get(0).getId(), issue1.getId());
    }

    @Test
    public void findByStateAndProject() {
        Project project = new Project("IssueTestProject8");
        Issue issue1 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 146L, 0.0);
        issue1 = issueManager.addIssue(issue1, project);
        assertNotNull(issue1.getId());
        assertNotNull(issue1.getProject());

        assertEquals(issueManager.findByStateAndProject(IssueState.OPEN, project).get(0).getId(), issue1.getId());
    }

    @Test
    public void findByEmployee() {
        Issue issue1 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 146L, 0.0);
        Employee employee1 =
                new Employee("Daniel", "Test007",
                        LocalDate.of(1996, 1, 1));
        issue1.setEmployee(employee1);
        issue1 = issueManager.syncIssue(issue1);
        assertEquals(issueManager.findByEmployee(employee1).get(0).getId(), issue1.getId());
    }

    @Test
    public void findAll() {
        Issue issue1 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 186L, 0.0);
        Issue issue2 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 646L, 0.0);
        issue1 = issueManager.syncIssue(issue1);
        issue2 = issueManager.syncIssue(issue2);

        assertTrue(issueManager.findAll().size() > 1);
    }
}