package swt6.it.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swt6.it.domain.Employee;
import swt6.it.domain.Project;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long> {
    List<Employee> findByProjects(Project project);
}
