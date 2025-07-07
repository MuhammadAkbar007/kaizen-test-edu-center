package uz.akbar.edu_center_kaizen.payload.request;

import java.time.LocalDate;

import uz.akbar.edu_center_kaizen.enums.StudentStatus;

public record StudentUpdateDto(
		String username,
		String password,
		String firstName,
		String lastName,
		String phoneNumber,
		LocalDate dateOfBirth,
		LocalDate enrollmentDate,
		StudentStatus studentStatus

) {
}
