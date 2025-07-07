package uz.akbar.edu_center_kaizen.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import uz.akbar.edu_center_kaizen.entity.Group;
import uz.akbar.edu_center_kaizen.entity.Student;
import uz.akbar.edu_center_kaizen.entity.Teacher;

public interface GroupRepository extends JpaRepository<Group, Long> {

	boolean existsByName(String name);

	Optional<Group> findByIdAndVisibleTrue(Long id);

	Page<Group> findAllByVisibleTrue(Pageable pageable);

	Page<Group> findAllByTeacherAndVisibleTrue(Pageable pageable, Teacher teacher);

	Page<Group> findAllByStudentsContainingAndVisibleTrue(Pageable pageable, Student student);
}
