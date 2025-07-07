package uz.akbar.edu_center_kaizen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import uz.akbar.edu_center_kaizen.entity.Student;
import uz.akbar.edu_center_kaizen.payload.request.StudentCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.StudentDetailsDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {
		GroupMapper.class })
public interface StudentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "studentStatus", ignore = true)
	@Mapping(target = "groups", ignore = true)
	@Mapping(target = "visible", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	Student toEntity(StudentCreateDto dto);

	@Mapping(target = "userId", source = "user.id")
	StudentDetailsDto toDetailsDto(Student student);
}
