package uz.akbar.edu_center_kaizen.service.implementation;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.Group;
import uz.akbar.edu_center_kaizen.entity.Role;
import uz.akbar.edu_center_kaizen.entity.Student;
import uz.akbar.edu_center_kaizen.entity.Teacher;
import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.GeneralStatus;
import uz.akbar.edu_center_kaizen.enums.RoleType;
import uz.akbar.edu_center_kaizen.enums.StudentStatus;
import uz.akbar.edu_center_kaizen.exception.AppBadRequestException;
import uz.akbar.edu_center_kaizen.mapper.StudentMapper;
import uz.akbar.edu_center_kaizen.payload.request.StudentCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.StudentDetailsDto;
import uz.akbar.edu_center_kaizen.repository.GroupRepository;
import uz.akbar.edu_center_kaizen.repository.RoleRepository;
import uz.akbar.edu_center_kaizen.repository.StudentRepository;
import uz.akbar.edu_center_kaizen.repository.TeacherRepository;
import uz.akbar.edu_center_kaizen.repository.UserRepository;
import uz.akbar.edu_center_kaizen.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentRepository repository;
	private final StudentMapper mapper;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final GroupRepository groupRepository;
	private final TeacherRepository teacherRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public AppResponse<StudentDetailsDto> create(StudentCreateDto dto, User currentUser) {
		Long groupId = dto.groupId();

		if (userRepository.existsByUsername(dto.username()))
			throw new AppBadRequestException(
					"Student already exists with username: " + dto.username());

		if (repository.existsByPhoneNumber(dto.phoneNumber()))
			throw new AppBadRequestException(
					"Teacher already exists with phoneNumber: " + dto.phoneNumber());

		Group group = groupRepository.findByIdAndVisibleTrue(groupId)
				.orElseThrow(() -> new AppBadRequestException("Group is not found with id: " + groupId));

		if (currentUser.getRole().getRoleType().equals(RoleType.ROLE_TEACHER)) {
			Teacher teacher = teacherRepository.findByUserAndVisibleTrue(currentUser)
					.orElseThrow(
							() -> new AppBadRequestException(
									"Teacher not found with username: " + currentUser.getUsername()));

			if (!teacher.getGroups().contains(group))
				throw new AppBadRequestException("You do not have permission fro this group: " + groupId);
		}

		Role studentRole = roleRepository.findByRoleType(RoleType.ROLE_STUDENT).orElseGet(() -> {
			Role newRole = Role.builder()
					.roleType(RoleType.ROLE_STUDENT)
					.description("Student role for edu center")
					.visible(true)
					.build();
			return roleRepository.save(newRole);
		});

		User user = User.builder()
				.username(dto.username())
				.password(passwordEncoder.encode(dto.password()))
				.status(GeneralStatus.ACTIVE)
				.visible(true)
				// .roles(Set.of(teacherRole))
				.role(studentRole)
				.build();

		Student student = mapper.toEntity(dto);
		student.setStudentStatus(StudentStatus.STUDYING);
		student.setUser(user);
		student.setVisible(true);
		student.setGroups(Set.of(group));

		Student saved = repository.save(student);
		return AppResponse.success("Student successfully created", mapper.toDetailsDto(saved));
	}

}
