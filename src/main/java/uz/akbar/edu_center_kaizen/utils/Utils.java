package uz.akbar.edu_center_kaizen.utils;

import java.time.format.DateTimeFormatter;

public interface Utils {

	String BASE_URL = "/api/v1";

	String DATE_TIME_PATTERN = "yyyy/MM/dd";

	DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
}
