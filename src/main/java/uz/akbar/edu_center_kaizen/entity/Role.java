package uz.akbar.edu_center_kaizen.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.akbar.edu_center_kaizen.entity.template.AbsLongEntity;
import uz.akbar.edu_center_kaizen.enums.RoleType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "roles")
public class Role extends AbsLongEntity {

	@Column(nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Column(nullable = false, columnDefinition = "text")
	private String description;

	@Builder.Default
	@OneToMany(mappedBy = "role")
	Set<User> users = new HashSet<>();
}
