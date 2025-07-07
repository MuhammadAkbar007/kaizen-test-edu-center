package uz.akbar.edu_center_kaizen.service;

import uz.akbar.edu_center_kaizen.enums.DeleteType;
import uz.akbar.edu_center_kaizen.payload.request.TeacherCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;
import uz.akbar.edu_center_kaizen.payload.response.TeacherDetailsDto;

public interface TeacherService {

	AppResponse<TeacherDetailsDto> create(TeacherCreateDto dto);

	AppResponse<PaginationData<TeacherDetailsDto>> getAll(int page, int size);

	AppResponse<TeacherDetailsDto> getById(Long id);

	AppResponse<TeacherDetailsDto> update(Long id, TeacherCreateDto dto);

	void delete(Long id, DeleteType deleteType);
}
