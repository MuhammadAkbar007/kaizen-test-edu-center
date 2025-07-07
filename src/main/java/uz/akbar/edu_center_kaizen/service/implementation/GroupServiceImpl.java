package uz.akbar.edu_center_kaizen.service.implementation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.Group;
import uz.akbar.edu_center_kaizen.entity.Student;
import uz.akbar.edu_center_kaizen.entity.Teacher;
import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.GroupStatus;
import uz.akbar.edu_center_kaizen.enums.RoleType;
import uz.akbar.edu_center_kaizen.exception.AppBadRequestException;
import uz.akbar.edu_center_kaizen.mapper.GroupMapper;
import uz.akbar.edu_center_kaizen.payload.request.GroupCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.GroupDetailsDto;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;
import uz.akbar.edu_center_kaizen.repository.GroupRepository;
import uz.akbar.edu_center_kaizen.repository.StudentRepository;
import uz.akbar.edu_center_kaizen.repository.TeacherRepository;
import uz.akbar.edu_center_kaizen.service.GroupService;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	private final GroupRepository repository;
	private final TeacherRepository teacherRepository;
	private final StudentRepository studentRepository;
	private final GroupMapper mapper;

	@Override
	@Transactional
	public AppResponse<GroupDetailsDto> create(GroupCreateDto dto) {
		String name = dto.name();

		if (repository.existsByName(name))
			throw new AppBadRequestException("Group with the name " + name + " already exists");

		Teacher teacher = teacherRepository.findByIdAndVisibleTrue(dto.teacherId())
				.orElseThrow(() -> new AppBadRequestException("Teacher is not found with id: " + dto.teacherId()));

		Group group = Group.builder()
				.name(name)
				.description(dto.description())
				.teacher(teacher)
				.subject(dto.subject())
				.groupStatus(GroupStatus.STUDYING)
				.visible(true)
				.build();

		Group saved = repository.save(group);

		return AppResponse.success("Group successfully created", mapper.toDetailsDto(saved));
	}

	@Override
	@Transactional(readOnly = true)
	public AppResponse<PaginationData<GroupDetailsDto>> getAll(int page, int size, User user) {
		RoleType roleType = user.getRole().getRoleType();
		Pageable pageable = PageRequest.of(page, size);

		Page<Group> groupsPaged = switch (roleType) {
			case ROLE_ADMIN -> repository.findAllAndVisibleTrue(pageable);

			case ROLE_TEACHER -> {
				Teacher teacher = teacherRepository.findByUserAndVisibleTrue(user)
						.orElseThrow(
								() -> new AppBadRequestException("Teacher not found for user: " + user.getUsername()));

				yield repository.findAllByTeacherAndVisibleTrue(pageable, teacher);
			}

			case ROLE_STUDENT -> {
				Student student = studentRepository.findByUserAndVisibleTrue(user)
						.orElseThrow(
								() -> new AppBadRequestException("Student not found for user: " + user.getUsername()));

				yield repository.findAllByStudentsContainingAndVisibleTrue(pageable, student);
			}

			default -> throw new AppBadRequestException("Wrong role type: " + roleType);
		};

		List<GroupDetailsDto> groupDetailsDtoList = mapper.toDetailsDtoList(groupsPaged.getContent());

		return AppResponse.success("Groups successfully retrieved",
				PaginationData.of(groupDetailsDtoList, groupsPaged));
	}

}
