package swt6.it.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.it.domain.Employee;
import swt6.it.domain.Issue;
import swt6.it.domain.LogbookEntry;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogbookEntryDao extends JpaRepository<LogbookEntry, Long> {
    LogbookEntry findByEmployee(Employee employee);
    Optional<LogbookEntry> findByActivity(String activity);
    List<LogbookEntry> findByEmployeeAndIssue(Employee employee, Issue issue);
}
