package uz.akbar.edu_center_kaizen.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.repository.UserRepository;

/** CustomUserDetailsService */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository
				.findByUsernameAndVisibleTrue(username)
				.orElseThrow(
						() -> new UsernameNotFoundException(
								"User not found with username: " + username));

		return new CustomUserDetails(user);
	}
}
