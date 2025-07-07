package uz.akbar.edu_center_kaizen.service.implementation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.Role;
import uz.akbar.edu_center_kaizen.entity.Teacher;
import uz.akbar.edu_center_kaizen.entity.User;
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

}
