package sch.work.backendstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sch.work.backendstudy.domain.ProjectEntity;
import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    List<ProjectEntity> findAllByAdminIdAndStatus(Integer adminId, Integer status);
    ProjectEntity findByIdAndStatus(Integer id, Integer status);
}
