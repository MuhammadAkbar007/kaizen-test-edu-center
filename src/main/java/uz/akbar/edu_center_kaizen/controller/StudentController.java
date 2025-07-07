package uz.akbar.edu_center_kaizen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.payload.request.StudentCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.StudentDetailsDto;
import uz.akbar.edu_center_kaizen.security.CustomUserDetails;
import uz.akbar.edu_center_kaizen.service.StudentService;
import uz.akbar.edu_center_kaizen.utils.Utils;

@RestController
@RequiredArgsConstructor
@RequestMapping(Utils.BASE_URL + "/student")
public class StudentController {

	private final StudentService service;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
	public ResponseEntity<AppResponse<StudentDetailsDto>> create(
			@RequestBody StudentCreateDto dto,
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		AppResponse<StudentDetailsDto> response = service.create(dto, customUserDetails.getUser());
		return ResponseEntity.ok(response);
	}
}
