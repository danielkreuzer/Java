package swt6.it.ui.impl;

import org.springframework.data.util.Pair;
import swt6.it.domain.*;
import swt6.it.logic.interfaces.*;
import swt6.it.ui.interfaces.IssueTrackerUIProcess;
import swt6.util.PrintUtil;
import swt6.util.UiUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

public class IssueTrackerUIProcessImpl implements IssueTrackerUIProcess {
    //region Injection
    private AddressManager addressManager;
    private EmployeeManager employeeManager;
    private IssueManager issueManager;
    private LogbookEntryManager logbookEntryManager;
    private ProjectManager projectManager;
    private ProjectPhaseManager projectPhaseManager;
    private TestEnvironmentManager testEnvironmentManager;

    public void setAddressManager(AddressManager addressManager) {
        this.addressManager = addressManager;
    }

    public void setEmployeeManager(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
    }

    public void setIssueManager(IssueManager issueManager) {
        this.issueManager = issueManager;
    }

    public void setLogbookEntryManager(LogbookEntryManager logbookEntryManager) {
        this.logbookEntryManager = logbookEntryManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public void setProjectPhaseManager(ProjectPhaseManager projectPhaseManager) {
        this.projectPhaseManager = projectPhaseManager;
    }

    public void setTestEnvironmentManager(TestEnvironmentManager testEnvironmentManager) {
        this.testEnvironmentManager = testEnvironmentManager;
    }

    //endregion

    @Override
    public void createTestEnvironment() {
        testEnvironmentManager.createTestEnvironment();
    }

    //Anzeige aller Mitarbeiter.
    @Override
    public void listAllEmployees() {
        for(Employee employee : employeeManager.getAllEmployees()) {
            System.out.println(employee);
        }
    }

    //Anlegen eines neuen Projekts
    @Override
    public void addNewProject() {
        Project project =  UiUtil.getNewProjectFromUser();
        if(project != null) {
            project = projectManager.syncProject(project);
            System.out.println("You created:");
            System.out.println(project);
        } else {
            System.out.println("Wrong input!");
        }
    }

    //Zuordnung von Mitarbeitern zu einem Projekt, Rücknahme dieser Zuordnung
    @Override
    public void addEmployeesToProject() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Project project = getProjectFromUser();
        if(project != null) {
            String stopString = "";
            while (!stopString.equals("no")) {
                Employee employee = getEmployeeFromUser();
                if (employee != null) {
                    project = projectManager.addEmployeeToProject(project, employee);
                    System.out.println("Employee added!");
                } else {
                    System.out.println("Wrong input!");
                }
                System.out.println("Add more employees to project? [yes] [no]");
                stopString = PrintUtil.promptFor(in, "");
            }
        } else {
            System.out.println("Wrong input!");
        }
    }

    //Zuordnung von Mitarbeitern zu einem Projekt, Rücknahme dieser Zuordnung
    @Override
    public void removeEmployeesFromProject() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Project project = getProjectFromUser();
        if(project != null) {
            String stopString = "";
            while (!stopString.equals("no")) {
                Employee employee = getEmployeeFromUserForProject(project);
                if (employee != null) {
                    project = projectManager.removeEmployeeFormProject(project, employee);
                    System.out.println("Employee removed!");
                } else {
                    System.out.println("Wrong input!");
                }
                System.out.println("Remove more employees from project? [yes] [no]");
                stopString = PrintUtil.promptFor(in, "");
            }
        } else {
            System.out.println("Wrong input!");
        }
    }

    //Auflistung aller einem Projekt zugeordneten Mitarbeiter
    @Override
    public void listAllEmployeesWithProject() {
        for (Employee employee : employeeManager.getAllEmployeesWithProject()) {
            System.out.println(employee);
            for (Project project : employee.getProjects()) {
                System.out.println("    " + project);
            }
        }
    }

    //Hinzufügen/Aktualisieren eines Issues (inklusive der Zuordnung zu einem Projekt)
    @Override
    public void addIssue() {
        Issue issue = UiUtil.getNewIssueFromUser();
        if(issue != null) {
            System.out.println("Add project to issue");
            Project project = getProjectFromUser();
            if(project != null) {
                issueManager.addIssue(issue, project);
                System.out.println("You created:");
                System.out.println(issue + " and added project " + project);
            } else {
                System.out.println("wrong input");
            }
        } else {
            System.out.println("Wrong input");
        }
    }

    //Hinzufügen/Aktualisieren eines Issues (inklusive der Zuordnung zu einem Projekt)
    @Override
    public void updateIssue() {
        try {
            Issue issue = getIssueFromUser();
            if (issue != null) {
                issue = UiUtil.updateIssueFromUser(issue);
                System.out.println("Update project from issue");
                Project project = getProjectFromUser();
                if (project != null && issue != null) {
                    issue.setProject(project);
                    issue = issueManager.syncIssue(issue);
                    System.out.println("You updated:");
                    System.out.println(issue + " and updated project " + project);
                } else {
                    System.out.println("wrong input");
                }
            } else {
                System.out.println("Wrong input");
            }
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
    }

    //Zuordnung eines Verantwortlichen zu einem Issue
    @Override
    public void addEmployeeToIssue() {
        Issue issue = getIssueFromUser();
        if(issue != null) {
            Employee employee = getEmployeeFromUser();
            if(employee != null) {
                issue.addEmployee(employee);
                issue = issueManager.syncIssue(issue);
                System.out.println(issue + " added " + employee);
            } else {
                System.out.println("Wrong input");
            }
        } else {
            System.out.println("Wrong input");
        }
    }

    //Erfassung von Tagebucheinträgen und Zuordnung des Eintrags zu einem Issue/Modul (Details s. o)
    @Override
    public void addLogbookEntry() {
        LogbookEntry logbookEntry = UiUtil.getNewLogbookEntryFromUser();
        ProjectPhase projectPhase = getProjectPhaseFromUser();
        System.out.println("Add issue to logbook entry");
        Issue issue = getIssueFromUser();
        if(logbookEntry != null && issue != null && projectPhase != null) {
            logbookEntry.addProjectPhase(projectPhase);
            logbookEntryManager.addLogbookEntry(logbookEntry, issue, null);
            System.out.println("You added:");
            System.out.println(logbookEntry +  " to issue " + issue);
        } else {
            System.out.println("Wrong input");
        }
    }

    //Auflistung aller Issues eines Projekts. Sehen Sie eine Filterungsmöglichkeit nach dem Status eines Issues vor
    @Override
    public void listAllIssuesFromProject() {
        try {

            Project project =  getProjectFromUser();
            IssueState issueState = UiUtil.getIssueStateFromUser();

            if(project != null) {
                List<Issue> issues;
                if (issueState != null) {
                    issues = issueManager.findByStateAndProject(issueState, project);
                } else {
                    issues = issueManager.findByProject(project);
                }

                System.out.println("Found issues:");
                for(Issue issue : issues) {
                    System.out.println(issue);
                }
            } else {
                System.out.println("Wrong project input");
            }


        } catch (Exception e) {
            System.out.println("Wrong input!");
        }
    }

    //Auflistung aller Issues eines Projekts, gruppiert nach Mitarbeiter.
    // Auch hier soll wieder nach dem Status des Issues gefiltert werden können
    @Override
    public void listAllIssuesFromProjectGroupedByEmployee() {
        try {
            Project project =  getProjectFromUser();
            IssueState issueState = UiUtil.getIssueStateFromUser();

            if(project != null) {
                System.out.println("found issues:");
                List<Employee> employees = employeeManager.findByProject(project);
                for(Employee employee : employees) {
                    List<Issue> issues;
                    issues = issueManager.findByEmployee(employee);
                    if (issueState != null) {
                        issues.removeIf(issue -> issue.getState() != issueState);
                    }
                    if(!issues.isEmpty()) {
                        System.out.println(employee);
                        for (Issue issue : issues) {
                            System.out.println("    " + issue);
                        }
                    }
                }
            } else {
                System.out.println("Wrong project input");
            }


        } catch (Exception e) {
            System.out.println("Wrong input!");
        }
    }

    // 1st
    //Auflistung der geleisteten bzw. noch zu erbringenden Arbeitszeit für ein Projekt.
    // Die Arbeitszeiten sind für jeden Mitarbeiter getrennt anzuführen.

    // 2nd
    // Geben Sie auch an, in welchem Ausmaß die Bearbeitung eines Issues bereits abgeschlossen ist
    // (geschätzte Zeit vs. Tagebucheinträge, die diesem Issue zugeordnet sind).

    // Aus dieser Auswertung soll ablesbar sein, wann die Arbeiten an einem Projekt abgeschlossen sein werden
    // (wenn man nur die erfassten Issues berücksichtigt) und welcher Mitarbeiter sich am kritischen Pfad befindet
    // (gehen Sie der Einfachheit halber davon aus, dass ein Mitarbeiter 8 Stunden pro Werktag arbeitet)
    @Override
    public void listWorkTime() {
        Project project = getProjectFromUser();
        if(project != null) {
            System.out.println(project);
            // First statistic
            System.out.println("--- Project-Employees statistic ---");
            for(Employee employee : project.getEmployees()) {
                System.out.println(employee);
                System.out.println("    Employee needs to spend: " +
                        projectManager.employeeNeedsToInvest(project, employee) + "min");
                System.out.println("    Employee spent: " +
                        projectManager.employeeInvested(project, employee) + "min");
            }

            // 2nd statistic
            System.out.println("--- Project-Issues statistic ---");
            for(Issue issue : project.getIssues()) {
                System.out.println(issue);
                Long spent1 = projectManager.TimeInvested(issue);
                Long needed = issue.getEstimatedTime();

                System.out.println("    Time spent: " + spent1);
                System.out.println("    Time estimated: " + needed);
                System.out.println("    Factor: " + calculateFactor(spent1, needed));
            }

            //3th statistic
            System.out.println("--- Project statistic ---");
            Long toInvest = projectManager.timeNeededToInvest(project);
            Long spent = projectManager.timeInvested(project);
            System.out.println("Project needs " + toInvest
                    + " work minutes and " + spent + " min are already spent");
            System.out.println("Spent/needed factor: " + calculateFactor(spent, toInvest));

            System.out.println("--- Critical Path ---");
            Pair<Long, Set<Employee>> pair = projectManager.getCriticalPath(project);
            Double days = Math.round((Double.parseDouble(pair.getFirst().toString())/480.0)*100.0)/100.0;
            System.out.println("Days last employee/s finish: " + days);
            System.out.println("Employees on critical path: ");
            for(Employee employee : pair.getSecond()) {
                System.out.println("    " + employee);
            }
        }
    }

    //region Utils
    private Project getProjectFromUser() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Projects:");
            for (Project project : projectManager.findAllProjects()) {
                System.out.println("    " + project);
            }
            System.out.println("Select project");
            String selection = PrintUtil.promptFor(in, "Project id");
            Long projectId = Long.parseLong(selection);

            return projectManager.findById(projectId);
        } catch (Exception e) {
            System.out.println("Wrong project input!");
        }
        return null;
    }

    private Employee getEmployeeFromUser() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Choose one Employee by id!");
            listAllEmployees();
            Long employeeId = Long.parseLong(PrintUtil.promptFor(in, "employee id"));
            return employeeManager.getEmployeeById(employeeId);
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
        return null;
    }

    private Issue getIssueFromUser() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Choose one Issue by id!");
            List<Issue> issues = issueManager.findAll();
            for(Issue issue : issues) {
                System.out.println(issue);
            }
            Long issueId = Long.parseLong(PrintUtil.promptFor(in, "issue id"));
            return issues.stream().filter(issue -> issue.getId().equals(issueId)).findAny().orElse(null);
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
        return null;
    }

    private Employee getEmployeeFromUserForProject(Project project) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Choose one Employee by id!");
            for(Employee employee : employeeManager.findByProject(project)) {
                System.out.println(employee);
            }
            Long employeeId = Long.parseLong(PrintUtil.promptFor(in, "employee id"));
            return employeeManager.getEmployeeById(employeeId);
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
        return null;
    }

    private ProjectPhase getProjectPhaseFromUser() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Choose one Project Phase by id!");
            for(ProjectPhase projectPhase : projectPhaseManager.findAll()) {
                System.out.println(projectPhase);
            }
            Long projectPhaseId = Long.parseLong(PrintUtil.promptFor(in, "project phase id"));
            return projectPhaseManager.findById(projectPhaseId);
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
        return null;
    }

    private Double calculateFactor(Long spent, Long toInvest) {
        Double factor = 0.0;
        if(spent > toInvest) toInvest = spent;
        if(toInvest == 0) {
            factor = 1.0;
        } else {
            factor = Math.round(((Double.parseDouble(spent.toString())/Double.parseDouble(toInvest.toString()))*100.0))/100.0;
        }
        return factor;
    }
    //endregion
}
