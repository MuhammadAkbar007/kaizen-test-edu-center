package uz.akbar.edu_center_kaizen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.payload.jwt.JwtResponseDto;
import uz.akbar.edu_center_kaizen.payload.request.LoginDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.service.AuthService;
import uz.akbar.edu_center_kaizen.utils.Utils;

@RequiredArgsConstructor
@RestController
@RequestMapping(Utils.BASE_URL + "/auth")
public class AuthController {

	private final AuthService service;

	@PostMapping("/login")
	public ResponseEntity<AppResponse<JwtResponseDto>> loginToSystem(@Valid @RequestBody LoginDto dto) {
		AppResponse<JwtResponseDto> response = service.logIn(dto);
		return ResponseEntity.ok(response);
	}
}
