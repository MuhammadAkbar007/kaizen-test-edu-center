package uz.akbar.edu_center_kaizen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import uz.akbar.edu_center_kaizen.payload.request.TeacherCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;
import uz.akbar.edu_center_kaizen.payload.response.TeacherDetailsDto;
import uz.akbar.edu_center_kaizen.service.TeacherService;
import uz.akbar.edu_center_kaizen.utils.Utils;

@RequiredArgsConstructor
@RestController
@RequestMapping(Utils.BASE_URL + "/teacher")
@PreAuthorize("hasRole('ADMIN')")
public class TeacherController {

	private final TeacherService service;

	@PostMapping
	public ResponseEntity<AppResponse<TeacherDetailsDto>> create(@RequestBody TeacherCreateDto dto) {
		AppResponse<TeacherDetailsDto> response = service.create(dto);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<AppResponse<PaginationData<TeacherDetailsDto>>> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		AppResponse<PaginationData<TeacherDetailsDto>> response = service.getAll(page, size);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AppResponse<TeacherDetailsDto>> getById(@PathVariable Long id) {
		AppResponse<TeacherDetailsDto> response = service.getById(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AppResponse<TeacherDetailsDto>> update(@PathVariable Long id,
			@RequestBody TeacherCreateDto dto) {

		AppResponse<TeacherDetailsDto> response = service.update(id, dto);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
			@PathVariable Long id,
			@RequestParam(defaultValue = "SOFT") DeleteType deleteType) {

		service.delete(id, deleteType);
		return ResponseEntity.noContent().build();
	}
}
