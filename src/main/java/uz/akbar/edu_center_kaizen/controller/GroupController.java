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
import uz.akbar.edu_center_kaizen.payload.request.GroupCreateDto;
import uz.akbar.edu_center_kaizen.payload.request.GroupUpdateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.GroupDetailsDto;
import uz.akbar.edu_center_kaizen.payload.response.PaginationData;
import uz.akbar.edu_center_kaizen.security.CustomUserDetails;
import uz.akbar.edu_center_kaizen.service.GroupService;
import uz.akbar.edu_center_kaizen.utils.Utils;

@RestController
@RequiredArgsConstructor
@RequestMapping(Utils.BASE_URL + "/group")
public class GroupController {

	private final GroupService service;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AppResponse<GroupDetailsDto>> create(@RequestBody GroupCreateDto dto) {
		AppResponse<GroupDetailsDto> response = service.create(dto);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AppResponse<PaginationData<GroupDetailsDto>>> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		AppResponse<PaginationData<GroupDetailsDto>> response = service.getAll(page, size, customUserDetails.getUser());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AppResponse<GroupDetailsDto>> getById(@PathVariable Long id,
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		AppResponse<GroupDetailsDto> response = service.getById(id, customUserDetails.getUser());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
	public ResponseEntity<AppResponse<GroupDetailsDto>> update(
			@PathVariable Long id,
			@RequestBody GroupUpdateDto dto,
			@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		AppResponse<GroupDetailsDto> response = service.update(id, dto, customUserDetails.getUser());
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(
			@PathVariable Long id,
			@RequestParam(defaultValue = "SOFT") DeleteType deleteType) {

		service.delete(id, deleteType);
		return ResponseEntity.noContent().build();
	}
}
