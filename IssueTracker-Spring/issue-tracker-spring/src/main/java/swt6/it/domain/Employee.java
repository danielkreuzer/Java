package swt6.it.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;


//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Employee {
    @Id @GeneratedValue
    private Long id;
    @Column(length = 30, nullable = false)
    private String firstName;
    @Column(length = 30, nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Address address;

    // Navigation relation not useful
//    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private Set<LogbookEntry> logbookEntries = new HashSet<>();
//
//    @OneToMany(mappedBy = "employee", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private Set<Issue> issues = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Project> projects = new HashSet<>();

    public Employee() {
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        if(project == null) {
            throw new IllegalArgumentException("Project is null");
        }

        project.getEmployees().add(this);
        projects.add(project);
    }

    public void removeProject(Project project) {
        if(project == null) {
            throw new IllegalArgumentException("Project is null");
        }

        project.getEmployees().remove(this);
        projects.remove(project);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("dateOfBirth=" + dateOfBirth)
                .toString();
    }
}
