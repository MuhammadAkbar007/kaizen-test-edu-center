package uz.akbar.edu_center_kaizen.service.implementation;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.Group;
import uz.akbar.edu_center_kaizen.entity.Teacher;
import uz.akbar.edu_center_kaizen.enums.GroupStatus;
import uz.akbar.edu_center_kaizen.exception.AppBadRequestException;
import uz.akbar.edu_center_kaizen.mapper.GroupMapper;
import uz.akbar.edu_center_kaizen.payload.request.GroupCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.GroupDetailsDto;
import uz.akbar.edu_center_kaizen.repository.GroupRepository;
import uz.akbar.edu_center_kaizen.repository.TeacherRepository;
import uz.akbar.edu_center_kaizen.service.GroupService;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	private final GroupRepository repository;
	private final TeacherRepository teacherRepository;
	private final GroupMapper mapper;

	@Override
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

}
