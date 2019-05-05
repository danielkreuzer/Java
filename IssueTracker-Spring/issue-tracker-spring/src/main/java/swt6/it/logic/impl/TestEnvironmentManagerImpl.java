package swt6.it.logic.impl;

import org.springframework.transaction.annotation.Transactional;
import swt6.it.dao.interfaces.*;
import swt6.it.domain.*;
import swt6.it.logic.interfaces.TestEnvironmentManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SuppressWarnings("Duplicates")
@Transactional
public class TestEnvironmentManagerImpl implements TestEnvironmentManager {
    //region Injection
    private AddressDao addressDao;
    private EmployeeDao employeeDao;
    private IssueDao issueDao;
    private LogbookEntryDao logbookEntryDao;
    private ProjectDao projectDao;
    private ProjectPhaseDao projectPhaseDao;

    public void setAddressDao(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setIssueDao(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    public void setLogbookEntryDao(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public void setProjectPhaseDao(ProjectPhaseDao projectPhaseDao) {
        this.projectPhaseDao = projectPhaseDao;
    }
    //endregion

    @Override
    public void createTestEnvironment() {
        if(logbookEntryDao.findByActivity("create Test Environment").isEmpty()) {
            Address address1 = new Address("Marktplatz 4", "4193", "Reichenthal");
            Address address2 = new Address("Marktplatz 5", "4190", "Bad Leonfelden");
            Address address3 = new Address("Hauptplatz 3", "4020", "Linz");

            address1 = addressDao.save(address1);
            address2 = addressDao.save(address2);
            address3 = addressDao.save(address3);

            Employee employee1 =
                    new Employee("Daniel", "Kreuzer",
                            LocalDate.of(1996, 1, 1));
            Employee employee2 =
                    new Employee("Florian", "Kreuzer",
                            LocalDate.of(2000, 1, 1));
            Employee employee3 =
                    new Employee("Elisa", "Kreuzer",
                            LocalDate.of(1998, 1, 1));

            employee1.setAddress(address1);
            employee2.setAddress(address2);
            employee3.setAddress(address3);

            employee1 = employeeDao.save(employee1);
            employee2 = employeeDao.save(employee2);
            employee3 = employeeDao.save(employee3);

            Project project = new Project("Wetr");
            Project project1 = new Project("Inwego");

            project.addEmployee(employee1);
            project.addEmployee(employee2);
            project1.addEmployee(employee3);

            project = projectDao.save(project);
            project1 = projectDao.save(project1);

            Issue issue1 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 120L, 0.0);
            Issue issue2 = new Issue(IssueState.OPEN, IssuePriority.HIGH, 60L, 0.0);
            Issue issue3 = new Issue(IssueState.OPEN, IssuePriority.LOW, 60L, 0.0);
            Issue issue4 = new Issue(IssueState.OPEN, IssuePriority.LOW, 60L, 0.0);
            Issue issue5 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 120L, 0.0);
            Issue issue6 = new Issue(IssueState.OPEN, IssuePriority.NORMAL, 120L, 0.0);

            issue1.addProject(project);
            issue2.addProject(project);
            issue3.addProject(project);
            issue4.addProject(project1);
            issue5.addProject(project1);
            issue6.addProject(project1);

            issue1.addEmployee(employee1);
            issue2.addEmployee(employee2);
            issue3.addEmployee(employee3);

            issue1 = issueDao.save(issue1);
            issue2 = issueDao.save(issue2);
            issue3 = issueDao.save(issue3);
            issue4 = issueDao.save(issue4);
            issue5 = issueDao.save(issue5);
            issue6 = issueDao.save(issue6);

            ProjectPhase projectPhase1 = new ProjectPhase("planning");
            ProjectPhase projectPhase2 = new ProjectPhase("implementation");
            ProjectPhase projectPhase3 = new ProjectPhase("testing");

            projectPhase1 = projectPhaseDao.save(projectPhase1);
            projectPhase2 = projectPhaseDao.save(projectPhase2);
            projectPhase3 = projectPhaseDao.save(projectPhase3);

            LogbookEntry logbookEntry1 = new LogbookEntry("Waste time of colleges",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));
            LogbookEntry logbookEntry2 = new LogbookEntry("Amazon shopping",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));
            LogbookEntry logbookEntry3 = new LogbookEntry("Implementing",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));
            LogbookEntry logbookEntry4 = new LogbookEntry("Testing",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));
            LogbookEntry logbookEntry5 = new LogbookEntry("Testing",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));
            LogbookEntry logbookEntry6 = new LogbookEntry("Coffee break",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));
            LogbookEntry logbookEntry7 = new LogbookEntry("Goodie tested",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));
            LogbookEntry logbookEntry8 = new LogbookEntry("Eeaster egg implementation",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));
            LogbookEntry logbookEntry9 = new LogbookEntry("Netflix",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));

            LogbookEntry logbookEntrySAVE = new LogbookEntry("create Test Environment",
                    LocalDateTime.of(2000, 1, 1, 9, 0),
                    LocalDateTime.of(2000, 1, 1, 9, 30));

            logbookEntry1.addIssue(issue1);
            logbookEntry2.addIssue(issue2);
            logbookEntry3.addIssue(issue3);
            logbookEntry4.addIssue(issue4);
            logbookEntry5.addIssue(issue5);
            logbookEntry6.addIssue(issue6);
            logbookEntry7.addIssue(issue6);
            logbookEntry8.addIssue(issue6);
            logbookEntry9.addIssue(issue6);

            logbookEntry1.addEmployee(employee1);
            logbookEntry2.addEmployee(employee2);
            logbookEntry3.addEmployee(employee3);
            logbookEntry4.addEmployee(employee1);
            logbookEntry5.addEmployee(employee2);
            logbookEntry6.addEmployee(employee3);
            logbookEntry7.addEmployee(employee1);
            logbookEntry8.addEmployee(employee2);
            logbookEntry9.addEmployee(employee3);

            logbookEntry1.addProjectPhase(projectPhase1);
            logbookEntry2.addProjectPhase(projectPhase2);
            logbookEntry3.addProjectPhase(projectPhase3);
            logbookEntry4.addProjectPhase(projectPhase1);
            logbookEntry5.addProjectPhase(projectPhase2);
            logbookEntry6.addProjectPhase(projectPhase3);
            logbookEntry7.addProjectPhase(projectPhase1);
            logbookEntry8.addProjectPhase(projectPhase2);
            logbookEntry9.addProjectPhase(projectPhase3);

            logbookEntry1 = logbookEntryDao.save(logbookEntry1);
            logbookEntry2 = logbookEntryDao.save(logbookEntry2);
            logbookEntry3 = logbookEntryDao.save(logbookEntry3);
            logbookEntry4 = logbookEntryDao.save(logbookEntry4);
            logbookEntry5 = logbookEntryDao.save(logbookEntry5);
            logbookEntry6 = logbookEntryDao.save(logbookEntry6);
            logbookEntry7 = logbookEntryDao.save(logbookEntry7);
            logbookEntry8 = logbookEntryDao.save(logbookEntry8);
            logbookEntry9 = logbookEntryDao.save(logbookEntry9);
            logbookEntrySAVE = logbookEntryDao.save(logbookEntrySAVE);
        }
    }
}
