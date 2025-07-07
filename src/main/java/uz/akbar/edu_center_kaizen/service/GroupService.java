package uz.akbar.edu_center_kaizen.service;

import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.payload.request.GroupCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.GroupDetailsDto;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;

public interface GroupService {

	AppResponse<GroupDetailsDto> create(GroupCreateDto dto);

	AppResponse<PaginationData<GroupDetailsDto>> getAll(int page, int size, User user);
}
