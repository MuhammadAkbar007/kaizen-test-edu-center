package uz.akbar.edu_center_kaizen.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.Role;
import uz.akbar.edu_center_kaizen.entity.Teacher;
import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.DeleteType;
import uz.akbar.edu_center_kaizen.enums.GeneralStatus;
import uz.akbar.edu_center_kaizen.enums.RoleType;
import uz.akbar.edu_center_kaizen.exception.AppBadRequestException;
import uz.akbar.edu_center_kaizen.mapper.TeacherMapper;
import uz.akbar.edu_center_kaizen.payload.request.TeacherCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;
import uz.akbar.edu_center_kaizen.payload.response.TeacherDetailsDto;
import uz.akbar.edu_center_kaizen.repository.RoleRepository;
import uz.akbar.edu_center_kaizen.repository.TeacherRepository;
import uz.akbar.edu_center_kaizen.repository.UserRepository;
import uz.akbar.edu_center_kaizen.service.TeacherService;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

	private final TeacherRepository repository;
	private final TeacherMapper mapper;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public AppResponse<TeacherDetailsDto> create(TeacherCreateDto dto) {

		if (repository.existsByPhoneNumber(dto.phoneNumber()))
			throw new AppBadRequestException(
					"Teacher already exists with phoneNumber: " + dto.phoneNumber());

		if (userRepository.existsByUsername(dto.username()))
			throw new AppBadRequestException(
					"Teacher already exists with username: " + dto.username());

		Role teacherRole = roleRepository.findByRoleType(RoleType.ROLE_TEACHER).orElseGet(() -> {
			Role newRole = Role.builder()
					.roleType(RoleType.ROLE_TEACHER)
					.description("Teacher role for edu center")
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
				.role(teacherRole)
				.build();

		User savedUser = userRepository.save(user);

		Teacher teacher = mapper.toEntity(dto);
		teacher.setVisible(true);
		teacher.setUser(savedUser);

		Teacher saved = repository.save(teacher);

		TeacherDetailsDto teacherDetailsDto = mapper.toDetailsDto(saved);

		return AppResponse.success("Teacher successfully created", teacherDetailsDto);
	}

	@Transactional(readOnly = true)
	@Override
	public AppResponse<PaginationData<TeacherDetailsDto>> getAll(int page, int size) {
		Page<Teacher> teachersPage = repository.findAll(PageRequest.of(page, size));
		List<TeacherDetailsDto> teacherDetailsDtoList = mapper.toDetailsDtoList(teachersPage.getContent());
		PaginationData<TeacherDetailsDto> teachers = PaginationData.of(teacherDetailsDtoList, teachersPage);
		return AppResponse.success("Teachers retieved successfully", teachers);
	}

	@Transactional(readOnly = true)
	@Override
	public AppResponse<TeacherDetailsDto> getById(Long id) {
		Teacher teacher = repository.findByIdAndVisibleTrue(id)
				.orElseThrow(() -> new AppBadRequestException("Teacher not found with id: " + id));

		return AppResponse.success("teacher retrieved successfully", mapper.toDetailsDto(teacher));
	}

	@Transactional
	@Override
	public AppResponse<TeacherDetailsDto> update(Long id, TeacherCreateDto dto) {
		String newUsername = dto.username();
		String newPhoneNumber = dto.phoneNumber();
		String newSpecialization = dto.specialization();
		String newQualification = dto.qualification();
		LocalDate newHireDate = dto.hireDate();
		boolean isUserModified = false;
		boolean isTeacherModified = false;

		Teacher teacher = repository.findByIdAndVisibleTrue(id)
				.orElseThrow(() -> new AppBadRequestException("Teacher not found with id: " + id));

		User user = teacher.getUser();

		// set newUsername if available
		if (!user.getUsername().equals(newUsername)) { // if newUsername is different than user's
			if (userRepository.existsByUsernameAndIdNot(newUsername, user.getId()))
				throw new AppBadRequestException("Username is already taken: " + newUsername);

			user.setUsername(newUsername);
			isUserModified = true;
		}

		// set newPhoneNumber if available
		if (!teacher.getPhoneNumber().equals(newPhoneNumber)) { // if newPhoneNumber is different than teacher's
			if (repository.existsByPhoneNumberAndIdNot(newPhoneNumber, teacher.getId()))
				throw new AppBadRequestException("Phone number is already taken: " + newPhoneNumber);

			teacher.setPhoneNumber(newPhoneNumber);
			isTeacherModified = true;
		}

		// if newPassword is different than user's
		if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(dto.password()));
			isUserModified = true;
		}

		if (!dto.firstName().equals(teacher.getFirstName())) {
			teacher.setFirstName(dto.firstName());
			isTeacherModified = true;
		}

		if (!dto.lastName().equals(teacher.getLastName())) {
			teacher.setLastName(dto.lastName());
			isTeacherModified = true;
		}

		if (StringUtils.hasText(newSpecialization) && !newSpecialization.equals(teacher.getSpecialization())) {
			teacher.setSpecialization(newSpecialization);
			isTeacherModified = true;
		}

		if (StringUtils.hasText(newQualification) && !newQualification.equals(teacher.getQualification())) {
			teacher.setQualification(newQualification);
			isTeacherModified = true;
		}

		if (newHireDate != null && !newHireDate.isEqual(teacher.getHireDate())) {
			teacher.setHireDate(newHireDate);
			isTeacherModified = true;
		}

		if (isUserModified) {
			User savedUser = userRepository.save(user);
			teacher.setUser(savedUser);
			isTeacherModified = true;
		}

		if (isTeacherModified) {
			Teacher saved = repository.save(teacher);
			return AppResponse.success("Teacher successfully updated", mapper.toDetailsDto(saved));
		}
		return AppResponse.error("Nothing updated");
	}

	@Transactional
	@Override
	public void delete(Long id, DeleteType deleteType) {
		Teacher teacher = null;

		switch (deleteType) {
			case HARD:
				teacher = repository.findById(id)
						.orElseThrow(() -> new AppBadRequestException("Teacher is not found with id: " + id));
				userRepository.delete(teacher.getUser());
				repository.delete(teacher);
				break;

			case SOFT:
				teacher = repository.findByIdAndVisibleTrue(id)
						.orElseThrow(() -> new AppBadRequestException("Teacher is not found with id: " + id));

				User user = teacher.getUser();
				user.setVisible(false);
				User savedUser = userRepository.save(user);

				teacher.setVisible(false);
				teacher.setUser(savedUser);
				repository.save(teacher);
				break;
			default:
				throw new AppBadRequestException("Wrong deletion type");
		}
	}

}
