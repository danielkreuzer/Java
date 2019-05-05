package swt6.it.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Entity
public class Issue {
    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private IssueState state;
    @Enumerated(EnumType.STRING)
    private IssuePriority priority;
    @Column(nullable = true)
    private Long estimatedTime;
    @Column(nullable = false)
    private Double completedPercent;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Project project;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Employee employee;
    @OneToMany(mappedBy = "issue", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<LogbookEntry> entries = new HashSet<>();

    public Issue() {
    }

    public Issue(IssueState state, IssuePriority priority, Long estimatedTime, Double completedPercent) {
        this.state = state;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
        this.completedPercent = completedPercent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IssueState getState() {
        return state;
    }

    public void setState(IssueState state) {
        this.state = state;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public Long getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Long estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Double getCompletedPercent() {
        return completedPercent;
    }

    public void setCompletedPercent(Double completedPercent) {
        this.completedPercent = completedPercent;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<LogbookEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<LogbookEntry> entries) {
        this.entries = entries;
    }

    public void addProject(Project project) {
        if(project == null) {
            throw new IllegalArgumentException("Project is null");
        }

        if(this.project != null) {
            removeProject();
        }

        project.getIssues().add(this);
        this.project = project;
    }

    public void removeProject(){
        if(project == null) {
            throw new IllegalArgumentException("Project is null");
        }

        this.project.getIssues().remove(this);
        this.project = null;
    }

    public void addEmployee(Employee employee) {
        this.employee = employee;
    }

    public void removeEmployee(){
        this.employee = null;
    }

    public void addLogbookEntry(LogbookEntry logbookEntry) {
        if(logbookEntry == null) {
            throw new IllegalArgumentException("logbookEntry is null");
        }

        if(logbookEntry.getIssue() != null) {
            removeLogbookEntry(logbookEntry);
        }

        entries.add(logbookEntry);
        logbookEntry.setIssue(this);
    }

    public void removeLogbookEntry(LogbookEntry logbookEntry) {
        if(logbookEntry == null) {
            throw new IllegalArgumentException("logbookEntry is null");
        }

        if(logbookEntry.getIssue() != null) {
            logbookEntry.setIssue(null);
        }

        entries.remove(logbookEntry);
    }



    @Override
    public String toString() {
        return new StringJoiner(", ", Issue.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("state=" + state)
                .add("priority=" + priority)
                .add("estimatedTime=" + estimatedTime)
                .add("completedPercent=" + completedPercent)
                .toString();
    }
}
