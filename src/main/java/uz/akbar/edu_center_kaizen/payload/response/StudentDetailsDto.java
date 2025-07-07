package uz.akbar.edu_center_kaizen.payload.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import uz.akbar.edu_center_kaizen.enums.StudentStatus;

public record StudentDetailsDto(
		Long id,
		UUID userId,
		String firstName,
		String lastName,
		String phoneNumber,
		LocalDate dateOfBirth,
		LocalDate enrollmentDate,
		StudentStatus studentStatus,
		Set<GroupDetailsDto> groups) {
}
