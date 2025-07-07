package uz.akbar.edu_center_kaizen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.payload.request.TeacherCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.TeacherDetailsDto;
import uz.akbar.edu_center_kaizen.service.TeacherService;
import uz.akbar.edu_center_kaizen.utils.Utils;

@RequiredArgsConstructor
@RestController
@RequestMapping(Utils.BASE_URL + "/teacher")
public class TeacherController {

	private final TeacherService service;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AppResponse<TeacherDetailsDto>> create(@RequestBody TeacherCreateDto dto) {
		AppResponse<TeacherDetailsDto> response = service.create(dto);
		return ResponseEntity.ok(response);
	}
}
