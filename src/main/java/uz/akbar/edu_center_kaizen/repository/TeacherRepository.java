package uz.akbar.edu_center_kaizen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.akbar.edu_center_kaizen.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	boolean existsByPhoneNumber(String phoneNumber);

	Optional<Teacher> findByIdAndVisibleTrue(Long id);
}
