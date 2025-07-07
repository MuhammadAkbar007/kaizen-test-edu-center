package uz.akbar.edu_center_kaizen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.payload.request.GroupCreateDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.payload.response.GroupDetailsDto;
import uz.akbar.edu_center_kaizen.service.GroupService;
import uz.akbar.edu_center_kaizen.utils.Utils;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(Utils.BASE_URL + "/group")
public class GroupController {

	private final GroupService service;

	@PostMapping
	public ResponseEntity<AppResponse<GroupDetailsDto>> create(@RequestBody GroupCreateDto dto) {
		AppResponse<GroupDetailsDto> response = service.create(dto);
		return ResponseEntity.ok(response);
	}
}
