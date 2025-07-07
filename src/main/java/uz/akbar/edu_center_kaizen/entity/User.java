package uz.akbar.edu_center_kaizen.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.akbar.edu_center_kaizen.entity.template.AbsUUIDEntity;
import uz.akbar.edu_center_kaizen.enums.GeneralStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends AbsUUIDEntity {

	@Column(nullable = false, unique = true)
	private String username; // email or phone

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private GeneralStatus status; // ACTIVE or BLOCK

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<RefreshToken> refreshTokens = new HashSet<>();

	// @ManyToMany(fetch = FetchType.EAGER)
	// @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id",
	// referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name =
	// "role_id", referencedColumnName = "id"))
	// private Set<Role> roles;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;
}
