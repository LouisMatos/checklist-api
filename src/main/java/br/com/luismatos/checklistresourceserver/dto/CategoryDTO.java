package br.com.luismatos.checklistresourceserver.dto;

import javax.validation.constraints.NotBlank;

import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;
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
public class CategoryDTO {

	private String guid;
	
	@NotBlank(message = "Nome da Categoria não pode ser nulo ou vazio!")
	private String name;

	public static CategoryDTO toDTO(CategoryEntity categoryEntity) {
		return CategoryDTO.builder().guid(categoryEntity.getGuid()).name(categoryEntity.getName()).build();
	}

}
