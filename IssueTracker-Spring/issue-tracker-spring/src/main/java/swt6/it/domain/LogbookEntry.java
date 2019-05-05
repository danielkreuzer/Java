package swt6.it.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
public class LogbookEntry {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String activity;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Issue issue;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Employee employee;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private ProjectPhase projectPhase;

    public LogbookEntry() {
    }

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ProjectPhase getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(ProjectPhase projectPhase) {
        this.projectPhase = projectPhase;
    }

    public void addIssue(Issue issue) {
        if(issue == null) {
            throw new IllegalArgumentException("Issue is null");
        }

        if(this.issue != null) {
            removeIssue();
        }

        this.issue = issue;
        issue.getEntries().add(this);
    }

    public void removeIssue() {
        this.issue.getEntries().remove(this);
        this.issue = null;
    }

    public void addEmployee(Employee employee) {
        this.employee = employee;
    }

    public void addProjectPhase(ProjectPhase projectPhase) {
        this.projectPhase = projectPhase;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LogbookEntry.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("activity='" + activity + "'")
                .add("startTime=" + startTime)
                .add("endTime=" + endTime)
                .toString();
    }
}
