package swt6.util;

import swt6.it.domain.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class UiUtil {
    public static IssueState getIssueStateFromUser() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        IssueState issueState = null;
        System.out.println("Select issue state (Type [no] for no filtering)");
        System.out.println("[1] New");
        System.out.println("[2] Open");
        System.out.println("[3] Resolved");
        System.out.println("[4] Closed");
        System.out.println("[5] Rejected");

        String selection = PrintUtil.promptFor(in, "issue State id");
        switch (selection) {
            case "1":
                issueState = IssueState.NEW;
                break;
            case "2":
                issueState = IssueState.OPEN;
                break;
            case "3":
                issueState = IssueState.RESOLVED;
                break;
            case "4":
                issueState = IssueState.CLOSED;
                break;
            case "5":
                issueState = IssueState.REJECTED;
                break;
        }

        return issueState;
    }

    private static IssuePriority getIssuePriorityFromUser() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        IssuePriority issuePriority = null;
        System.out.println("Select issue priority (Type [no] for no filtering)");
        System.out.println("[1] Low");
        System.out.println("[2] Normal");
        System.out.println("[3] High");

        String selection = PrintUtil.promptFor(in, "issue State id");
        switch (selection) {
            case "1":
                issuePriority = IssuePriority.LOW;
                break;
            case "2":
                issuePriority = IssuePriority.NORMAL;
                break;
            case "3":
                issuePriority = IssuePriority.HIGH;
                break;
        }

        return issuePriority;
    }

    public static Project getNewProjectFromUser() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Project project;

            System.out.println("Enter title for new project");
            String title =  PrintUtil.promptFor(in, "project title");

            project = new Project(title);
            return project;

        } catch (Exception e) {
            System.out.println("Wrong input!");
        }
        return null;
    }

    public static Issue getNewIssueFromUser() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Issue issue;

            IssueState issueState = getIssueStateFromUser();
            IssuePriority issuePriority = getIssuePriorityFromUser();

            System.out.println("Estimated Time in minutes");
            String time =  PrintUtil.promptFor(in, "time");
            System.out.println("Completed Percent e.g. 20.0");
            String percent = PrintUtil.promptFor(in, "%");

            issue = new Issue(issueState, issuePriority, Long.parseLong(time), Double.parseDouble(percent));
            return issue;
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
        return null;
    }

    public static Issue updateIssueFromUser(Issue issue) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            IssueState issueState = getIssueStateFromUser();
            IssuePriority issuePriority = getIssuePriorityFromUser();

            System.out.println("Estimated Time in minutes");
            String time =  PrintUtil.promptFor(in, "time");
            System.out.println("Completed Percent e.g. 20.0");
            String percent = PrintUtil.promptFor(in, "%");

            issue.setState(issueState);
            issue.setPriority(issuePriority);
            issue.setEstimatedTime(Long.parseLong(time));
            issue.setCompletedPercent(Double.parseDouble(percent));
            return issue;
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
        return null;
    }

    public static LogbookEntry getNewLogbookEntryFromUser() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("New Logbook entry");
            System.out.println("Logbook entry activity");
            String activity = PrintUtil.promptFor(in, "activity");
            System.out.println("Add needed time in minutes");
            String neededTime = PrintUtil.promptFor(in, "needed time");

            LocalDateTime localDateTime = LocalDateTime.of(2000, 1, 1, 9, 0);
            LocalDateTime localDateTime1 = localDateTime.plusMinutes(Long.parseLong(neededTime));

            return new LogbookEntry(activity, localDateTime, localDateTime1);
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
        return null;
    }
}
