package uz.akbar.edu_center_kaizen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.enums.DeleteType;
import uz.akbar.edu_center_kaizen.payload.request.StudentCreateDto;
import uz.akbar.edu_center_kaizen.payload.request.StudentUpdateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;
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

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AppResponse<PaginationData<StudentDetailsDto>>> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		AppResponse<PaginationData<StudentDetailsDto>> response = service.getAll(page, size);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AppResponse<StudentDetailsDto>> getById(@PathVariable Long id,
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		AppResponse<StudentDetailsDto> response = service.getById(id, customUserDetails.getUser());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AppResponse<StudentDetailsDto>> update(@PathVariable Long id,
			@RequestBody StudentUpdateDto dto) {

		AppResponse<StudentDetailsDto> response = service.update(id, dto);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam DeleteType deleteType) {
		service.delete(id, deleteType);
		return ResponseEntity.noContent().build();
	}
}
