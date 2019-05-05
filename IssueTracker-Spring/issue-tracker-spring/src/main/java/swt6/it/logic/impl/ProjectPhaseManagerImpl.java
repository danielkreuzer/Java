package swt6.it.logic.impl;

import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import swt6.it.dao.interfaces.ProjectPhaseDao;
import swt6.it.domain.ProjectPhase;
import swt6.it.logic.interfaces.ProjectPhaseManager;

import java.util.List;
import java.util.Optional;

@Transactional
public class ProjectPhaseManagerImpl implements ProjectPhaseManager {
    private ProjectPhaseDao projectPhaseDao;

    public void setProjectPhaseDao(ProjectPhaseDao projectPhaseDao) {
        this.projectPhaseDao = projectPhaseDao;
    }

    @Override
    @CachePut(value = "projectPhases", key = "#projectPhase.id")
    public ProjectPhase syncProjectPhase(ProjectPhase projectPhase) {
        return projectPhaseDao.save(projectPhase);
    }

    @Override
    @Cacheable("projectPhases")
    public List<ProjectPhase> findAll() {
        return projectPhaseDao.findAll();
    }

    @Override
    @Cacheable(value = "projectPhase", key = "#id")
    public ProjectPhase findById(Long id) {
        Optional<ProjectPhase> projectPhase = projectPhaseDao.findById(id);
        return projectPhase.orElse(null);
    }
}
