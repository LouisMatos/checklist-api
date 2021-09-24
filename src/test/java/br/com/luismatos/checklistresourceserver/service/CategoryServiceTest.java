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

}
