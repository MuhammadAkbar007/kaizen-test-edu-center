package uz.akbar.edu_center_kaizen.security;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.GeneralStatus;

/** CustomUserDetails */
public class CustomUserDetails implements UserDetails {

	private User user;

	public CustomUserDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public UUID getUserId() {
		return user.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// return user.getRoles().stream()
		// .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
		// .collect(Collectors.toList());

		if (user.getRole() != null) {
			return Collections.singletonList(
					new SimpleGrantedAuthority(user.getRole().getRoleType().name()));
		}

		return Collections.emptyList();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getStatus() != GeneralStatus.BLOCK;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getStatus() != GeneralStatus.BLOCK;
	}
}
