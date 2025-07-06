package uz.akbar.edu_center_kaizen.payload.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequstDto(
		@NotBlank(message = "token is required") String refreshToken) {
}
