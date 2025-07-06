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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.akbar.edu_center_kaizen.entity.template.AbsLongAuditableEntity;
import uz.akbar.edu_center_kaizen.enums.GroupStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "groups")
public class Group extends AbsLongAuditableEntity {

	@Column(nullable = false, unique = true)
	private String name; // e.g., "Java-101", "Spring-Advanced"

	@Column(columnDefinition = "text")
	private String description;

	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = false)
	private Teacher teacher;

	@Column(nullable = false)
	private String subject; // "Java", "English", "Math"

	@Builder.Default
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private GroupStatus groupStatus = GroupStatus.STUDYING;

	@Builder.Default
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "groups_students", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private Set<Student> students = new HashSet<>();
}
