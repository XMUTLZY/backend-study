package sch.work.backendstudy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sch.work.backendstudy.domain.QuestionEntity;
import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
    Page<QuestionEntity> findAllByProjectIdIn(Pageable pageable, List<Integer> projectIdList);
    List<QuestionEntity> findAllByProjectIdIn(List<Integer> projectIdList);
}
