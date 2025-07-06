package uz.akbar.edu_center_kaizen.payload.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.data.domain.Page;

import lombok.Builder;

@Builder
@JsonPropertyOrder(alphabetic = true, value = { "content" })
public record PaginationData<T>(
		int pageNumber,
		int pageSize,
		int numberOfElements,
		int totalPages,
		long totalElements,
		boolean hasNext,
		boolean hasPrevious,
		boolean isFirst,
		boolean isLast,
		List<T> content) {

	public static <T> PaginationData<T> of(Page<T> page) {
		return PaginationData.<T>builder()
				.pageNumber(page.getNumber())
				.pageSize(page.getSize())
				.totalElements(page.getTotalElements())
				.totalPages(page.getTotalPages())
				.numberOfElements(page.getNumberOfElements())
				.isFirst(page.isFirst())
				.isLast(page.isLast())
				.hasNext(page.hasNext())
				.hasPrevious(page.hasPrevious())
				.content(page.getContent())
				.build();
	}

	public static <T> PaginationData<T> of(List<T> content, Page<?> page) {
		return PaginationData.<T>builder()
				.totalPages(page.getTotalPages())
				.totalElements(page.getTotalElements())
				.pageNumber(page.getNumber())
				.pageSize(page.getSize())
				.isFirst(page.isFirst())
				.isLast(page.isLast())
				.hasNext(page.hasNext())
				.hasPrevious(page.hasPrevious())
				.content(content)
				.build();
	}
}
