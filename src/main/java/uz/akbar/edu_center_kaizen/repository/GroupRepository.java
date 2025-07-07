package uz.akbar.edu_center_kaizen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.akbar.edu_center_kaizen.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	boolean existsByName(String name);
}
