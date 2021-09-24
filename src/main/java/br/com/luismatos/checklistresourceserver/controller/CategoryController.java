package br.com.luismatos.checklistresourceserver.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luismatos.checklistresourceserver.dto.CategoryDTO;
import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;
import br.com.luismatos.checklistresourceserver.service.CategoryService;

@RestController
@RequestMapping("/v1/api/categories")
public class CategoryController {

	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {

		List<CategoryDTO> response = StreamSupport.stream(this.categoryService.findAllCategory().spliterator(), false)
				.map(categoryEntity -> CategoryDTO.toDTO(categoryEntity)).collect(Collectors.toList());

		return new ResponseEntity<List<CategoryDTO>>(response, HttpStatus.OK);
	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addNewCategory(@RequestBody CategoryDTO categoryDTO) {

		CategoryEntity categoryEntity = this.categoryService.addNewCategory(categoryDTO.getName());

		return new ResponseEntity<String>(categoryEntity.getGuid(), HttpStatus.CREATED);
	}

	@PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateCategory(@RequestBody CategoryDTO categoryDTO) {

			if (!StringUtils.hasText(categoryDTO.getGuid())) {
				throw new ValidationException("Categoria não pode ser vazio ou nulo !");
			}

		this.categoryService.updateCategory(categoryDTO.getGuid(), categoryDTO.getName());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "{guid}")
	public ResponseEntity<Void> deleteCategory(@PathVariable String guid) {

		this.categoryService.deleteCategory(guid);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
