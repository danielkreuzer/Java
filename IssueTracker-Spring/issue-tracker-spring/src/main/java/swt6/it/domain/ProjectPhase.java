package swt6.it.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.StringJoiner;


//@Cacheable
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class ProjectPhase {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;

    //@OneToMany(mappedBy = "projectPhase", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    //private Set<LogbookEntry> logbookEntries = new HashSet<>();

    public ProjectPhase() {
    }

    public ProjectPhase(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProjectPhase.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
