package swt6.it.dao.jpa;

import org.springframework.stereotype.Repository;
import swt6.it.dao.interfaces.IssueDaoCustom;
import swt6.it.domain.Employee;
import swt6.it.domain.Issue;
import swt6.it.domain.IssueState;
import swt6.it.domain.Project;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class IssueDaoCustomImpl implements IssueDaoCustom {

    @PersistenceContext
    EntityManager em;


    @Override
    public List<Issue> findByStateAndProject(IssueState issueState, Project project) {
        TypedQuery<Issue> query =
                em.createQuery("select i from Issue i where i.project = :project and i.state = :state", Issue.class);
        query.setParameter("project", project);
        query.setParameter("state", issueState);
        return query.getResultList();
    }

    @Override
    public List<Issue> findByProject(Project project) {
        TypedQuery<Issue> query =
                em.createQuery("select i from Issue i where i.project = :project", Issue.class);
        query.setParameter("project", project);
        return query.getResultList();
    }

    @Override
    public List<Issue> findByEmployee(Employee employee) {
        TypedQuery<Issue> query =
                em.createQuery("select i from Issue i where i.employee = :empl", Issue.class);
        query.setParameter("empl", employee);
        return query.getResultList();
    }
}
