package uz.akbar.edu_center_kaizen.service.implementation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.Group;
import uz.akbar.edu_center_kaizen.entity.Student;
import uz.akbar.edu_center_kaizen.entity.Teacher;
import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.DeleteType;
import uz.akbar.edu_center_kaizen.enums.GroupStatus;
import uz.akbar.edu_center_kaizen.enums.RoleType;
import uz.akbar.edu_center_kaizen.exception.AppBadRequestException;
import uz.akbar.edu_center_kaizen.mapper.GroupMapper;
import uz.akbar.edu_center_kaizen.payload.request.GroupCreateDto;
import uz.akbar.edu_center_kaizen.payload.request.GroupUpdateDto;
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
			case ROLE_ADMIN -> repository.findAllByVisibleTrue(pageable);

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

	@Override
	@Transactional(readOnly = true)
	public AppResponse<GroupDetailsDto> getById(Long id, User user) {
		Group group = repository.findByIdAndVisibleTrue(id)
				.orElseThrow(() -> new AppBadRequestException("Group not found with id: " + id));

		// if user is student and he/she is not enrolled this group
		if (user.getRole().getRoleType().equals(RoleType.ROLE_STUDENT)) {
			Student student = studentRepository.findByUserAndVisibleTrue(user)
					.orElseThrow(
							() -> new AppBadRequestException("Student not found with username: " + user.getUsername()));

			if (!group.getStudents().contains(student))
				throw new AppBadRequestException("You don't have permission to get this group with id: " + id);
		}

		// if user is teacher and this is not his/her group
		if (user.getRole().getRoleType().equals(RoleType.ROLE_TEACHER)) {
			Teacher teacher = teacherRepository.findByUserAndVisibleTrue(user)
					.orElseThrow(
							() -> new AppBadRequestException("Teacher not found with username: " + user.getUsername()));

			if (!teacher.getId().equals(group.getTeacher().getId()))
				throw new AppBadRequestException("You don't have permission to get this group with id: " + id);
		}

		return AppResponse.success("Group successfully retrieved", mapper.toDetailsDto(group));
	}

	@Override
	@Transactional
	public AppResponse<GroupDetailsDto> update(Long id, GroupUpdateDto dto, User user) {
		boolean isGroupChanged = false;
		String name = dto.name();
		String description = dto.description();
		String subject = dto.subject();
		Long teacherId = dto.teacherId();
		GroupStatus groupStatus = dto.groupStatus();

		Group group = repository.findByIdAndVisibleTrue(id)
				.orElseThrow(() -> new AppBadRequestException("Group not found with id: " + id));

		// if user is teacher and this is not his/her group
		if (user.getRole().getRoleType().equals(RoleType.ROLE_TEACHER)) {
			Teacher teacher = teacherRepository.findByUserAndVisibleTrue(user)
					.orElseThrow(
							() -> new AppBadRequestException("Teacher not found with username: " + user.getUsername()));

			if (!teacher.getId().equals(group.getTeacher().getId()))
				throw new AppBadRequestException("You don't have permission to get this group with id: " + id);
		}

		if (StringUtils.hasText(name) && !name.equals(group.getName())) {
			group.setName(name);
			isGroupChanged = true;
		}

		if (StringUtils.hasText(description) && !description.equals(group.getDescription())) {
			group.setDescription(description);
			isGroupChanged = true;
		}

		if (groupStatus != null && !groupStatus.equals(group.getGroupStatus())) {
			group.setGroupStatus(groupStatus);
			isGroupChanged = true;
		}

		if (StringUtils.hasText(subject) && !subject.equals(group.getSubject())) {
			group.setSubject(subject);
			isGroupChanged = true;
		}

		if (teacherId != null) {
			Teacher teacher = teacherRepository.findByIdAndVisibleTrue(teacherId)
					.orElseThrow(() -> new AppBadRequestException("Teacher not found with id: " + teacherId));

			if (teacher.getId() != teacherId)
				group.setTeacher(teacher);
			isGroupChanged = true;
		}

		if (isGroupChanged) {
			Group saved = repository.save(group);
			return AppResponse.success("Group successfully updated", mapper.toDetailsDto(saved));
		}

		return AppResponse.error("Nothing changed");
	}

	@Override
	@Transactional
	public void delete(Long id, DeleteType deleteType) {
		switch (deleteType) {
			case SOFT:
				Group group = repository.findByIdAndVisibleTrue(id)
						.orElseThrow(() -> new AppBadRequestException("Group not found with id: " + id));

				group.setVisible(false);
				repository.save(group);
				break;

			case HARD:
				if (!repository.existsById(id))
					throw new AppBadRequestException("Group not found with id: " + id);

				repository.deleteById(id);
				break;

			default:
				throw new AppBadRequestException("Wrong delete type: " + deleteType);
		}
	}

}
