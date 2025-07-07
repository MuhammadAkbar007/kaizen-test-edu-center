package uz.akbar.edu_center_kaizen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.akbar.edu_center_kaizen.entity.Role;
import uz.akbar.edu_center_kaizen.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByRoleType(RoleType roleType);
}
