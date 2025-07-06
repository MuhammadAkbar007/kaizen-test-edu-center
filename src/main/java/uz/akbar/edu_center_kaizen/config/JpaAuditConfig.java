package uz.akbar.edu_center_kaizen.config;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import uz.akbar.edu_center_kaizen.security.CustomUserDetails;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {

	@Bean
	public AuditorAware<UUID> auditorProvider() {
		return () -> {
			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (authentication == null
						|| !authentication.isAuthenticated()
						|| "anonymousUser".equals(authentication.getPrincipal())) {
					return Optional.empty();
				}

				if (authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
					UUID userId = customUserDetails.getUserId();
					return userId != null ? Optional.of(userId) : Optional.empty();
				}

				return Optional.empty();
			} catch (Exception e) {
				return Optional.empty();
			}

		};
	}
}
