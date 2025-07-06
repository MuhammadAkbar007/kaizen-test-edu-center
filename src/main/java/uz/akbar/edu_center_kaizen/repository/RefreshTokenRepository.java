package uz.akbar.edu_center_kaizen.repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import uz.akbar.edu_center_kaizen.entity.RefreshToken;
import uz.akbar.edu_center_kaizen.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

	Optional<RefreshToken> findByToken(String token);

	void deleteByUser(User user);

	long countByUserAndExpiryDateAfter(User user, Instant now);

	Optional<RefreshToken> findFirstByUserOrderByCreatedAtAsc(User user);

}
