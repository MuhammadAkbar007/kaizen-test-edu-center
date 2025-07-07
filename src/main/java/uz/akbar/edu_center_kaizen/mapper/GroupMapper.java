package uz.akbar.edu_center_kaizen.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import uz.akbar.edu_center_kaizen.entity.Group;
import uz.akbar.edu_center_kaizen.payload.response.GroupDetailsDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GroupMapper {

	@Mapping(target = "teacherId", source = "teacher.id")
	GroupDetailsDto toDetailsDto(Group group);

	List<GroupDetailsDto> toDetailsDtoList(List<Group> groups);
}
