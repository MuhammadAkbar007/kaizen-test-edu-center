package uz.akbar.edu_center_kaizen.payload.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TeacherDetailsDto(
		Long id,
		UUID userId,
		String firstName,
		String lastName,
		String phoneNumber,
		String specialization,
		String qualification,
		LocalDate hireDate,
		Instant createdAt,
		Instant updatedAt,
		UUID createdBy,
		UUID updatedBy,
		List<GroupDetailsDto> groups) {
}
