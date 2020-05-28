package sch.work.backendstudy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sch.work.backendstudy.domain.AdminEntity;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    AdminEntity findByAccountName(String accountName);
    Page<AdminEntity> findAllByRoleAndStatus(Pageable pageable, Integer role, Integer status);
    List<AdminEntity> findAllByStatusAndRole(Integer status, Integer role);
}
