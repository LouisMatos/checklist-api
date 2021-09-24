package br.com.luismatos.checklistresourceserver.dto;

import javax.validation.constraints.NotBlank;

import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDTO {

	private String guid;
	
	@NotBlank(message = "Nome da Categoria não pode ser nulo ou vazio!")
	private String name;

	public static CategoryDTO toDTO(CategoryEntity categoryEntity) {
		return CategoryDTO.builder().guid(categoryEntity.getGuid()).name(categoryEntity.getName()).build();
	}

}
