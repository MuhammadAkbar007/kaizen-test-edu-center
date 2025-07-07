package uz.akbar.edu_center_kaizen.service;

import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.DeleteType;
import uz.akbar.edu_center_kaizen.payload.request.StudentCreateDto;
import uz.akbar.edu_center_kaizen.payload.request.StudentUpdateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;
import uz.akbar.edu_center_kaizen.payload.response.StudentDetailsDto;

public interface StudentService {

	AppResponse<StudentDetailsDto> create(StudentCreateDto dto, User user);

	AppResponse<PaginationData<StudentDetailsDto>> getAll(int page, int size);

	AppResponse<StudentDetailsDto> getById(Long id, User user);

	AppResponse<StudentDetailsDto> update(Long id, StudentUpdateDto dto);

	void delete(Long id, DeleteType deleteType);
}
