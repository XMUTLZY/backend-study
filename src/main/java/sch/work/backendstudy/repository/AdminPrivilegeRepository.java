package sch.work.backendstudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sch.work.backendstudy.domain.AdminPrivilegeEntity;

@Repository
public interface AdminPrivilegeRepository extends JpaRepository<AdminPrivilegeEntity, Integer> {
}
