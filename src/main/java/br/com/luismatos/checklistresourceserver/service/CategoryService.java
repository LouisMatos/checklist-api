package br.com.luismatos.checklistresourceserver.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;
import br.com.luismatos.checklistresourceserver.entity.ChecklistItemEntity;
import br.com.luismatos.checklistresourceserver.exception.CategoryServiceException;
import br.com.luismatos.checklistresourceserver.exception.ResourceNotFoundException;
import br.com.luismatos.checklistresourceserver.repository.CategoryRepository;
import br.com.luismatos.checklistresourceserver.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryService {

	private ChecklistItemRepository checklistItemRepository;

	private CategoryRepository categoryRepository;

	public CategoryService(ChecklistItemRepository checklistItemRepository, CategoryRepository categoryRepository) {
		this.checklistItemRepository = checklistItemRepository;
		this.categoryRepository = categoryRepository;
	}

	public CategoryEntity addNewCategory(String name) {
		if (!StringUtils.hasText(name)) {
			throw new IllegalArgumentException("Nome da categoria não pode ser nulo ou vazio!");
		}

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setGuid(UUID.randomUUID().toString());
		categoryEntity.setName(name);

		log.debug("Adicionando nova categoria com o nome [ nome = {} ]", name);

		return this.categoryRepository.save(categoryEntity);
	}

	public CategoryEntity updateCategory(String guid, String name) {
		if (!StringUtils.hasText(guid) || !StringUtils.hasText(name)) {
			throw new IllegalArgumentException("Parametros invalido para atualizar uma categoria!");
		}

		CategoryEntity categoryEntity = this.categoryRepository.findByGuid(guid)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada!"));

		categoryEntity.setName(name);

		log.debug("Atualizando categoria [ guid = {}, nome = {}]", guid, name);
		return this.categoryRepository.save(categoryEntity);
	}

	public void deleteCategory(String guid) {
		if (!StringUtils.hasText(guid)) {
			throw new IllegalArgumentException("Guid da categoria não pode ser nulo ou vazio!");
		}

		CategoryEntity categoryEntity = this.categoryRepository.findByGuid(guid)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada!"));

		List<ChecklistItemEntity> checklistItems = this.checklistItemRepository.findByCategoryGuid(guid);

		if (!CollectionUtils.isEmpty(checklistItems)) {
			throw new CategoryServiceException("Não é possivel apagar a categoria, esta sendo usado em um checklist!");
		}

		log.debug("Categoria deletada [ guid = {} ]", guid);
		this.categoryRepository.delete(categoryEntity);
	}

	public Iterable<CategoryEntity> findAllCategory() {
		return this.categoryRepository.findAll();
	}

	public CategoryEntity findCategoryByGuid(String guid) {
		if (!StringUtils.hasText(guid)) {
			throw new IllegalArgumentException("Guid da categoria não pode ser nulo ou vazio!");
		}
		return this.categoryRepository.findByGuid(guid)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada!"));
	}

}
