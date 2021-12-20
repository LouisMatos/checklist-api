package br.com.luismatos.checklistresourceserver.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;
import br.com.luismatos.checklistresourceserver.exception.ResourceNotFoundException;
import br.com.luismatos.checklistresourceserver.repository.CategoryRepository;
import br.com.luismatos.checklistresourceserver.repository.ChecklistItemRepository;

@ExtendWith({ MockitoExtension.class })
public class CategoryServiceTest {

	private CategoryService categoryService;

	@Mock
	private ChecklistItemRepository checklistItemRepository;
	@Mock
	private CategoryRepository categoryRepository;

	@BeforeEach
	public void initTest() {
		this.categoryService = new CategoryService(checklistItemRepository, categoryRepository);
	}

	@Test
	public void shouldCreateACategorySuccessfully() {

		// having
		String categoryName = "Personal";

		when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(new CategoryEntity());

		// when
		CategoryEntity categoryEntity = this.categoryService.addNewCategory(categoryName);

		// then
		Assertions.assertNotNull(categoryEntity);
		verify(categoryRepository, times(1))
				.save(argThat(categoryEntityArg -> categoryEntityArg.getName().equals(categoryName)
						&& categoryEntityArg.getGuid() != null));

	}

	@Test
	public void shouldThrowAnExceptionWhenCategoryNameIsNullOrEmpty() {

		Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> this.categoryService.addNewCategory(null));

		assertThat(exception.getMessage(), is("Nome da categoria não pode ser nulo ou vazio!"));

	}

	@Test
	public void shouldUpdateCategorySuccessfully() {

		// having
		String guid = UUID.randomUUID().toString();
		String name = "Other";

		CategoryEntity savedCategory = new CategoryEntity();
		savedCategory.setGuid(guid);
		savedCategory.setName("Personal");

		when(categoryRepository.findByGuid(guid)).thenReturn(Optional.of(savedCategory));

		when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(new CategoryEntity());

		// when
		CategoryEntity categoryEntity = this.categoryService.updateCategory(guid, name);

		// then
		Assertions.assertNotNull(categoryEntity);

		verify(categoryRepository, times(1)).save(argThat(categoryEntityArg -> categoryEntityArg.getName().equals(name)
				&& categoryEntityArg.getGuid().equals(guid)));

	}

	@Test
	public void shouldThrownAnExceptionWhenTryToUpdateAndCategoryAndGuidIsNullOrEmpty() {
		Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> this.categoryService.updateCategory("", "Sample Name"));

		assertThat(exception.getMessage(), is("Parametros invalido para atualizar uma categoria!"));
	}

	@Test
	public void shouldThrownAnExceptionWhenTryToUpdateAndCategoryAndNameIsNullOrEmpty() {
		Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
				() -> this.categoryService.updateCategory("any value", ""));

		assertThat(exception.getMessage(), is("Parametros invalido para atualizar uma categoria!"));
	}

	@Test
	public void shouldThrownAnExceptionWhenTryToUpdateAndCategoryAndItDoesNotExist() {
		when(categoryRepository.findByGuid(anyString())).thenReturn(Optional.empty());

		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class,
				() -> this.categoryService.updateCategory("any value", "any name"));

		assertThat(exception.getMessage(), is("Categoria não encontrada!"));
	}

}
