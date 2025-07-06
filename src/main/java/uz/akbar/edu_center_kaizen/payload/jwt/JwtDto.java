package uz.akbar.edu_center_kaizen.payload.jwt;

import java.time.Instant;

import lombok.Builder;

@Builder
public record JwtDto(String token, Instant expiryDate) {
}
