package uz.akbar.edu_center_kaizen.payload.jwt;

import java.time.Instant;

import lombok.Builder;

@Builder
public record JwtResponseDto(
		String username,
		String accessToken,
		String refreshToken,
		Instant accessTokenExpiryTime,
		Instant refreshTokenExpiryTime) {
}
