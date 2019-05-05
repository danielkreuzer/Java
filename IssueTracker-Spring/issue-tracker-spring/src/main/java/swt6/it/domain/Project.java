package swt6.it.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Entity
public class Project {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "ProjectEmployee", joinColumns = {
            @JoinColumn(name = "projectId")},
            inverseJoinColumns = {
            @JoinColumn(name = "employeeId")
    })
    private Set<Employee> employees = new HashSet<>();
    @OneToMany(mappedBy = "project", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Issue> issues = new HashSet<>();

    public Project() {
    }

    public Project(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public void addIssue(Issue issue) {
        if(issue == null) {
            throw new IllegalArgumentException("issue is null");
        }

        if(issue.getProject() != null) {
            issue.getProject().getIssues().remove(issue);
        }

        issue.setProject(this);
        this.issues.add(issue);
    }

    public void removeIssue(Issue issue) {
        if(issue == null) {
            throw new IllegalArgumentException("issue is null");
        }

        this.issues.remove(issue);
        issue.setProject(null);
    }

    public void addEmployee(Employee employee) {
        if(employee == null) {
            throw new IllegalArgumentException("issue is null");
        }

        this.employees.add(employee);
        employee.getProjects().add(this);
    }

    public void removeEmployee(Employee employee) {
        if(employee == null) {
            throw new IllegalArgumentException("issue is null");
        }

        this.employees.remove(employee);
        employee.getProjects().remove(this);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Project.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("title='" + title + "'")
                .toString();
    }
}
