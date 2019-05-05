package swt6.it.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.it.domain.Project;

import java.util.Optional;

public interface ProjectDao extends JpaRepository<Project, Long> {
}
