package uz.akbar.edu_center_kaizen.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.Group;
import uz.akbar.edu_center_kaizen.entity.Role;
import uz.akbar.edu_center_kaizen.entity.Student;
import uz.akbar.edu_center_kaizen.entity.Teacher;
import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.DeleteType;
import uz.akbar.edu_center_kaizen.enums.GeneralStatus;
import uz.akbar.edu_center_kaizen.enums.RoleType;
import uz.akbar.edu_center_kaizen.enums.StudentStatus;
import uz.akbar.edu_center_kaizen.exception.AppBadRequestException;
import uz.akbar.edu_center_kaizen.mapper.StudentMapper;
import uz.akbar.edu_center_kaizen.payload.request.StudentCreateDto;
import uz.akbar.edu_center_kaizen.payload.request.StudentUpdateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;
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

	@Override
	@Transactional(readOnly = true)
	public AppResponse<PaginationData<StudentDetailsDto>> getAll(int page, int size) {
		Page<Student> studentsPaged = repository.findAll(PageRequest.of(page, size));
		List<StudentDetailsDto> studentDetailsDtoList = mapper.toDetailsDtoList(studentsPaged.getContent());

		return AppResponse.success("Students retrieved successfully",
				PaginationData.of(studentDetailsDtoList, studentsPaged));
	}

	@Override
	@Transactional(readOnly = true)
	public AppResponse<StudentDetailsDto> getById(Long id, User user) {
		Student student = repository.findByIdAndVisibleTrue(id)
				.orElseThrow(() -> new AppBadRequestException("Student not found with id: " + id));

		if (user.getRole().getRoleType().equals(RoleType.ROLE_STUDENT)) {
			if (!user.getId().equals(student.getUser().getId()))
				throw new AppBadRequestException("You don't have permission to get the student with id: " + id);
		}

		return AppResponse.success("Student successfully retrieved", mapper.toDetailsDto(student));
	}

	@Override
	public AppResponse<StudentDetailsDto> update(Long id, StudentUpdateDto dto) {
		String username = dto.username();
		String password = dto.password();
		String firstName = dto.firstName();
		String lastName = dto.lastName();
		String phoneNumber = dto.phoneNumber();
		LocalDate dateOfBirth = dto.dateOfBirth();
		LocalDate enrollmentDate = dto.enrollmentDate();
		StudentStatus studentStatus = dto.studentStatus();
		boolean isUserChanged = false;
		boolean isStudentChanged = false;

		Student student = repository.findByIdAndVisibleTrue(id)
				.orElseThrow(() -> new AppBadRequestException("Student not found with id: " + id));

		User user = student.getUser();

		if (StringUtils.hasText(username) && !user.getUsername().equals(username)) {
			if (userRepository.existsByUsernameAndIdNot(username, user.getId()))
				throw new AppBadRequestException("Username is already taken: " + username);

			user.setUsername(username);
			isUserChanged = true;
		}

		if (StringUtils.hasText(password) && !user.getPassword().equals(password)) {
			user.setPassword(password);
			isUserChanged = true;
		}

		if (StringUtils.hasText(firstName) && !student.getFirstName().equals(firstName)) {
			student.setFirstName(firstName);
			isStudentChanged = true;
		}

		if (StringUtils.hasText(lastName) && !student.getLastName().equals(lastName)) {
			student.setLastName(lastName);
			isStudentChanged = true;
		}

		if (StringUtils.hasText(phoneNumber) && !student.getPhoneNumber().equals(phoneNumber)) {
			if (repository.existsByPhoneNumberAndIdNot(phoneNumber, student.getId()))
				throw new AppBadRequestException("Phone number is already taken: " + phoneNumber);

			student.setPhoneNumber(phoneNumber);
			isStudentChanged = true;
		}

		if (dateOfBirth != null && !dateOfBirth.isEqual(student.getDateOfBirth())) {
			student.setDateOfBirth(dateOfBirth);
			isStudentChanged = true;
		}

		if (enrollmentDate != null && !enrollmentDate.isEqual(student.getEnrollmentDate())) {
			student.setEnrollmentDate(enrollmentDate);
			isStudentChanged = true;
		}

		if (studentStatus != null && !studentStatus.equals(student.getStudentStatus())) {
			student.setStudentStatus(studentStatus);
			isStudentChanged = true;
		}

		if (isUserChanged) {
			User savedUser = userRepository.save(user);
			student.setUser(savedUser);
			isStudentChanged = true;
		}

		if (isStudentChanged) {
			Student saved = repository.save(student);
			return AppResponse.success("Student successfully updated", mapper.toDetailsDto(saved));
		}

		return AppResponse.error("Nothing changed");
	}

	@Override
	public void delete(Long id, DeleteType deleteType) {
		Student student = null;

		switch (deleteType) {
			case HARD:
				student = repository.findById(id)
						.orElseThrow(() -> new AppBadRequestException("Student not found with id: " + id));

				userRepository.delete(student.getUser());
				repository.delete(student);
				break;

			case SOFT:
				student = repository.findByIdAndVisibleTrue(id)
						.orElseThrow(() -> new AppBadRequestException("Student not found with id: " + id));

				User user = student.getUser();
				user.setVisible(false);
				User savedUser = userRepository.save(user);

				student.setVisible(false);
				student.setUser(savedUser);
				repository.save(student);
				break;
			default:
				throw new AppBadRequestException("Wrong delete type: " + deleteType);
		}
	}

}
