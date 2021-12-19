package br.com.luismatos.checklistresourceserver.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.luismatos.checklistresourceserver.entity.ChecklistItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ChecklistItemDTO {

	private String guid;

	@NotBlank(message = "Checklist item description cannot be either null or empty")
	private String description;

	@NotNull(message = "Is completed is mandatory")
	private Boolean isCompleted;

	@NotNull(message = "Deadline is mandatory")
	private LocalDate deadline;

	private LocalDate postedDate;

	private CategoryDTO category;

	public static ChecklistItemDTO toDTO(ChecklistItemEntity checklistItemEntity) {
		return ChecklistItemDTO.builder().guid(checklistItemEntity.getGuid())
				.description(checklistItemEntity.getDescription()).deadline(checklistItemEntity.getDeadline())
				.postedDate(checklistItemEntity.getPostedDate()).isCompleted(checklistItemEntity.getIsCompleted())
				.category(
						checklistItemEntity.getCategory() != null
								? CategoryDTO.builder().guid(checklistItemEntity.getCategory().getGuid())
										.name(checklistItemEntity.getCategory().getName()).build()
								: null)
				.build();
	}

}
