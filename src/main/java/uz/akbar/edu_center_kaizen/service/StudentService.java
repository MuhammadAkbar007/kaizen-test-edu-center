package uz.akbar.edu_center_kaizen.service;

import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.payload.request.StudentCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.StudentDetailsDto;

public interface StudentService {

	AppResponse<StudentDetailsDto> create(StudentCreateDto dto, User user);

}
