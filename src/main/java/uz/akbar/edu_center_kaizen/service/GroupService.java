package uz.akbar.edu_center_kaizen.service;

import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.DeleteType;
import uz.akbar.edu_center_kaizen.payload.request.GroupCreateDto;
import uz.akbar.edu_center_kaizen.payload.request.GroupUpdateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.GroupDetailsDto;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;

public interface GroupService {

	AppResponse<GroupDetailsDto> create(GroupCreateDto dto);

	AppResponse<PaginationData<GroupDetailsDto>> getAll(int page, int size, User user);

	AppResponse<GroupDetailsDto> getById(Long id, User user);

	AppResponse<GroupDetailsDto> update(Long id, GroupUpdateDto dto, User user);

	void delete(Long id, DeleteType deleteType);
}
