package uz.akbar.edu_center_kaizen.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.Role;
import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.GeneralStatus;
import uz.akbar.edu_center_kaizen.enums.RoleType;
import uz.akbar.edu_center_kaizen.repository.RoleRepository;
import uz.akbar.edu_center_kaizen.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class DataInitializationService {

	private final UserRepository repository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${app.admin.username:akbarjondev007@gmail.com}")
	private String adminUsername;

	@Value("${app.admin.password:root123}")
	private String adminPassword;

	@PostConstruct
	public void initializeAdmin() {
		try {
			Optional<User> existingAdmin = repository.findByUsername(adminUsername);
			if (existingAdmin.isPresent()) {
				System.out.println("Admin user already exists. Skipping initialization.");
				return;
			}

			Role adminRole = createOrGetAdminRole();

			User admin = User.builder()
					.username(adminUsername)
					.password(passwordEncoder.encode(adminPassword))
					.status(GeneralStatus.ACTIVE)
					.roles(Set.of(adminRole))
					.visible(true)
					.build();

			repository.save(admin);
			System.out.println("Admin user created successfully with username: " + adminUsername);
		} catch (Exception e) {
			System.out.println("Error occurred while initializing admin user");
			throw new RuntimeException("Failed to initialize admin user", e);
		}
	}

	private Role createOrGetAdminRole() {
		Optional<Role> optional = roleRepository.findByRoleType(RoleType.ADMIN);
		if (optional.isPresent())
			return optional.get();

		Role adminRole = Role.builder()
				.roleType(RoleType.ADMIN)
				.description("Default admin role")
				.build();

		return roleRepository.save(adminRole);
	}
}
