package uz.akbar.edu_center_kaizen.service;

import uz.akbar.edu_center_kaizen.payload.request.TeacherCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.TeacherDetailsDto;

public interface TeacherService {

	AppResponse<TeacherDetailsDto> create(TeacherCreateDto dto);
}
