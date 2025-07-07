package uz.akbar.edu_center_kaizen.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.akbar.edu_center_kaizen.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByUsername(String username);

	boolean existsByUsernameAndIdNot(String username, UUID excludedUserId);

	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndVisibleTrue(String username);
}
