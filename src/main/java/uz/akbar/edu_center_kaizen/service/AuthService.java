package uz.akbar.edu_center_kaizen.service;

import uz.akbar.edu_center_kaizen.payload.jwt.JwtResponseDto;
import uz.akbar.edu_center_kaizen.payload.request.LoginDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;

public interface AuthService {

	AppResponse<JwtResponseDto> logIn(LoginDto dto);

}
