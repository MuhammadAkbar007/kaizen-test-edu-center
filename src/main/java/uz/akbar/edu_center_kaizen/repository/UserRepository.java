package uz.akbar.edu_center_kaizen.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.akbar.edu_center_kaizen.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUsernameAndVisibleTrue(String username);
}
