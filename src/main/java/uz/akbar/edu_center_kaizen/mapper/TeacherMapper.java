package uz.akbar.edu_center_kaizen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import uz.akbar.edu_center_kaizen.entity.Teacher;
import uz.akbar.edu_center_kaizen.payload.request.TeacherCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.TeacherDetailsDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {
		GroupMapper.class })
public interface TeacherMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "groups", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "visible", ignore = true)
	Teacher toEntity(TeacherCreateDto dto);

	@Mapping(target = "userId", source = "user.id")
	TeacherDetailsDto toDetailsDto(Teacher teacher);
}
