package swt6.it.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.it.domain.Employee;
import swt6.it.domain.Issue;
import swt6.it.domain.IssueState;
import swt6.it.domain.Project;

import java.util.List;

@Repository
public interface IssueDao extends JpaRepository<Issue, Long>, IssueDaoCustom {
}
