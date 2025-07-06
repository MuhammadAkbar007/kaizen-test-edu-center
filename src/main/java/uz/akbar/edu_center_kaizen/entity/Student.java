package uz.akbar.edu_center_kaizen.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import uz.akbar.edu_center_kaizen.entity.template.AbsLongEntity;
import uz.akbar.edu_center_kaizen.enums.StudentStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "students")
public class Student extends AbsLongEntity {

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(unique = true)
	private String phoneNumber;

	private String studentId; // enrollment number

	private LocalDate dateOfBirth;

	private LocalDate enrollmentDate;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	private StudentStatus studentStatus = StudentStatus.STUDYING;

	@Builder.Default
	@ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
	private Set<Group> groups = new HashSet<>();
}
