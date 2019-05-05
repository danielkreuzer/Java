package swt6.it.ui.interfaces;

public interface IssueTrackerUIProcess {
    void createTestEnvironment();
    void listAllEmployees();
    void addNewProject();
    void addEmployeesToProject();
    void removeEmployeesFromProject();
    void listAllEmployeesWithProject();
    void addIssue();
    void updateIssue();
    void addEmployeeToIssue();
    void addLogbookEntry();
    void listAllIssuesFromProject();
    void listAllIssuesFromProjectGroupedByEmployee();
    void listWorkTime();
}
