package sch.work.backendstudy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sch.work.backendstudy.domain.StudentAnswerEntity;
import java.util.List;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswerEntity, Integer> {
    Page<StudentAnswerEntity> findAllByStatus(Pageable pageable, Integer status);
    List<StudentAnswerEntity> findAllByStatus(Integer status);
}
