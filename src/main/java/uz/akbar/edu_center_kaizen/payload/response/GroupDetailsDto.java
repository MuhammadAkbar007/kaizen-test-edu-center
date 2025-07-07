package uz.akbar.edu_center_kaizen.payload.response;

import java.time.Instant;
import java.util.UUID;

import uz.akbar.edu_center_kaizen.enums.GroupStatus;

public record GroupDetailsDto(
		Long id,
		String name,
		String description,
		Long teacherId,
		String subject,
		GroupStatus groupStatus,
		Instant createdAt,
		Instant updatedAt,
		UUID createdBy,
		UUID updatedBy) {
}
