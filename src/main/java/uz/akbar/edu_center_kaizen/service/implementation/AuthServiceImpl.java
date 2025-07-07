package uz.akbar.edu_center_kaizen.service.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uz.akbar.edu_center_kaizen.entity.User;
import uz.akbar.edu_center_kaizen.enums.GeneralStatus;
import uz.akbar.edu_center_kaizen.exception.AppBadRequestException;
import uz.akbar.edu_center_kaizen.payload.jwt.JwtDto;
import uz.akbar.edu_center_kaizen.payload.jwt.JwtResponseDto;
import uz.akbar.edu_center_kaizen.payload.request.LoginDto;
import uz.akbar.edu_center_kaizen.payload.response.AppResponse;
import uz.akbar.edu_center_kaizen.security.CustomUserDetails;
import uz.akbar.edu_center_kaizen.security.jwt.JwtProvider;
import uz.akbar.edu_center_kaizen.service.AuthService;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;

	@Override
	public AppResponse<JwtResponseDto> logIn(LoginDto dto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		User user = userDetails.getUser();

		if (!user.getStatus().equals(GeneralStatus.ACTIVE))
			throw new AppBadRequestException("Wrong status");

		SecurityContextHolder.getContext().setAuthentication(authentication);

		JwtDto accessTokenDto = jwtProvider.generateAccessToken(authentication);
		JwtDto refreshTokenDto = jwtProvider.generateRefreshToken(authentication);

		JwtResponseDto jwtResponseDto = JwtResponseDto.builder()
				.username(user.getUsername())
				.accessToken(accessTokenDto.token())
				.refreshToken(refreshTokenDto.token())
				.accessTokenExpiryTime(accessTokenDto.expiryDate())
				.refreshTokenExpiryTime(refreshTokenDto.expiryDate())
				.build();

		return AppResponse.success("User successfully logged in", jwtResponseDto);
	}

}
