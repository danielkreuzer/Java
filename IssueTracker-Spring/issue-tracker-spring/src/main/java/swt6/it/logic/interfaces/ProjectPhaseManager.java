package swt6.it.logic.interfaces;

import swt6.it.domain.ProjectPhase;

import java.util.List;

public interface ProjectPhaseManager {
    ProjectPhase syncProjectPhase(ProjectPhase projectPhase);
    List<ProjectPhase> findAll();
    ProjectPhase findById(Long id);
}
