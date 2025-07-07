package uz.akbar.edu_center_kaizen.service;

import uz.akbar.edu_center_kaizen.payload.request.GroupCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.GroupDetailsDto;

public interface GroupService {

	AppResponse<GroupDetailsDto> create(GroupCreateDto dto);
}
