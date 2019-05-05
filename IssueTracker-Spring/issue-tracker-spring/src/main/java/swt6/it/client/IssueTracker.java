package swt6.it.client;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.it.ui.interfaces.IssueTrackerUIProcess;
import swt6.util.PrintUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IssueTracker {
    public static void PrintCommands() {
        System.out.println("--- Commands: ---");
        System.out.println("[1]     List all employees");
        System.out.println("[2]     Add new project");
        System.out.println("[3]     Add employees to project");
        System.out.println("[4]     Remove employees from project");
        System.out.println("[5]     List all employees with project");
        System.out.println("[6]     Add issue");
        System.out.println("[7]     Update issue");
        System.out.println("[8]     Add employee to issue");
        System.out.println("[9]     Add logbook entry");
        System.out.println("[10]    List issues from project");
        System.out.println("[11]    List issues from project grouped by employee");
        System.out.println("[12]    Project work time statistics");
        System.out.println("[quit]  Close");
    }

    public static void main(String[] args) {
        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext(
                             "swt6/it/client/applicationContext.xml")) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            IssueTrackerUIProcess issueTrackerUIProcess = factory.getBean(IssueTrackerUIProcess.class);
            issueTrackerUIProcess.createTestEnvironment();

            PrintUtil.printTitle("ISSUE TRACKER SPRING", 20, '+');
            System.out.println("Type [help] for commands");

            String userCommand = PrintUtil.promptFor(in, "");

            while (!userCommand.equals("quit")) {
                switch (userCommand) {
                    case "help":
                        PrintUtil.printSeparator(20, '-');
                        PrintCommands();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "1":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.listAllEmployees();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "2":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.addNewProject();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "3":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.addEmployeesToProject();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "4":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.removeEmployeesFromProject();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "5":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.listAllEmployeesWithProject();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "6":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.addIssue();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "7":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.updateIssue();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "8":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.addEmployeeToIssue();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "9":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.addLogbookEntry();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "10":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.listAllIssuesFromProject();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "11":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.listAllIssuesFromProjectGroupedByEmployee();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    case "12":
                        PrintUtil.printSeparator(20, '-');
                        issueTrackerUIProcess.listWorkTime();
                        PrintUtil.printSeparator(20, '-');
                        break;
                    default:
                        System.out.println("invalid command! Type [help] for commands!");
                        break;
                }
                userCommand = PrintUtil.promptFor(in, "");
            }


        } catch (Exception e) {
            System.out.println("Error occurred, contact your sysadmin");
        }
    }
}
