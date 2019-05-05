package swt6.it.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.it.domain.ProjectPhase;

public interface ProjectPhaseDao extends JpaRepository<ProjectPhase, Long> {
}
