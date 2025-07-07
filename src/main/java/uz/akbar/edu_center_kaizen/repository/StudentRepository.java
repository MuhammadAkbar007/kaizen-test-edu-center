package uz.akbar.edu_center_kaizen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.akbar.edu_center_kaizen.entity.Student;
import uz.akbar.edu_center_kaizen.entity.User;

public interface StudentRepository extends JpaRepository<Student, Long> {

	boolean existsByPhoneNumber(String phoneNumber);

	Optional<Student> findByUserAndVisibleTrue(User user);
}
