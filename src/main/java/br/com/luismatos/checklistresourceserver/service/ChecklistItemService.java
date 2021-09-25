package br.com.luismatos.checklistresourceserver.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;
import br.com.luismatos.checklistresourceserver.entity.ChecklistItemEntity;
import br.com.luismatos.checklistresourceserver.exception.ResourceNotFoundException;
import br.com.luismatos.checklistresourceserver.repository.CategoryRepository;
import br.com.luismatos.checklistresourceserver.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChecklistItemService {

	@Autowired
	public ChecklistItemRepository checklistItemRepository;

	@Autowired
	public CategoryRepository categoryRepository;

	private void validateChecklistData(String description, Boolean isCompleted, LocalDate deadline,
			String categoryGuid) {

		if (!StringUtils.hasText(description)) {
			throw new IllegalArgumentException("Campo descrição é obrigatório");
		}

		if (!StringUtils.hasText(categoryGuid)) {
			throw new IllegalArgumentException("Campo guid é obrigatório");
		}

		if (isCompleted == null) {
			throw new IllegalArgumentException("Campo completo é obrigatório");
		}

		if (deadline == null) {
			throw new IllegalArgumentException("Campo deadline é obrigatório");
		}
	}

	public ChecklistItemEntity updateChecklistItem(String guid, String description, Boolean isCompleted,
			LocalDate deadline, String categoryGuid) {
		if (!StringUtils.hasText(guid)) {
			throw new IllegalArgumentException("Campo guid não pode ser vazio ou nulo!");
		}

		ChecklistItemEntity checklistItemEntity = this.checklistItemRepository.findByGuid(guid)
				.orElseThrow(() -> new ResourceNotFoundException("Checklist não encontrada"));

		if (!StringUtils.hasText(description)) {
			checklistItemEntity.setDescription(description);
		}

		if (isCompleted != null) {
			checklistItemEntity.setIsCompleted(isCompleted);
		}

		if (deadline != null) {
			checklistItemEntity.setDeadline(deadline);
		}

		if (!StringUtils.hasText(categoryGuid)) {
			CategoryEntity categoryEntity = this.categoryRepository.findByGuid(categoryGuid)
					.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

			checklistItemEntity.setCategory(categoryEntity);
		}

		log.debug("Atualizando o checklist item [ checklistItem = {}]", checklistItemEntity.toString());
		return this.checklistItemRepository.save(checklistItemEntity);
	}

	public ChecklistItemEntity addChecklistItem(String description, Boolean isCompleted, LocalDate deadline,
			String categoryGuid) {

		this.validateChecklistData(description, isCompleted, deadline, categoryGuid);

		CategoryEntity categoryEntity = this.categoryRepository.findByGuid(categoryGuid)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

		ChecklistItemEntity checklistItemEntity = new ChecklistItemEntity();
		checklistItemEntity.setGuid(UUID.randomUUID().toString());
		checklistItemEntity.setDeadline(deadline);
		checklistItemEntity.setDescription(description);
		checklistItemEntity.setPostedDate(LocalDate.now());
		checklistItemEntity.setCategory(categoryEntity);
		checklistItemEntity.setIsCompleted(isCompleted);

		log.debug("Adicionando novo Checklist item [ checklistItem = {} ]", checklistItemEntity);

		return this.checklistItemRepository.save(checklistItemEntity);
	}

	public ChecklistItemEntity findChecklistyByGuid(String guid) {
		if (!StringUtils.hasText(guid)) {
			throw new IllegalArgumentException("Guid da categoria não pode ser nulo ou vazio!");
		}
		return this.checklistItemRepository.findByGuid(guid)
				.orElseThrow(() -> new ResourceNotFoundException("ChecklistItem não encontrada!"));
	}

	public Iterable<ChecklistItemEntity> findAllChecklistItems() {
		return this.checklistItemRepository.findAll();
	}

	public void deleteChecklistItem(String guid) {
		if (!StringUtils.hasText(guid)) {
			throw new IllegalArgumentException("Campo guid não pode ser vazio ou nulo!");
		}

		ChecklistItemEntity checklistItemEntity = this.checklistItemRepository.findByGuid(guid)
				.orElseThrow(() -> new ResourceNotFoundException("Checklist não encontrada"));

		log.debug("Deletando checklist item [ guid = {}]", guid);

		this.checklistItemRepository.delete(checklistItemEntity);

	}

	public void updateIsCompleteStatus(String guid, boolean isComplete) {
		if (!StringUtils.hasText(guid)) {
			throw new IllegalArgumentException("Guid não pode ser nulo ou vazio");
		}
		ChecklistItemEntity retrievedItem = this.checklistItemRepository.findByGuid(guid)
				.orElseThrow(() -> new ResourceNotFoundException("Item da lista de verificação não encontrado"));

		log.debug("Atualizando o status do item da lista de verificação concluído [ guid ={}, isCompete={} ]", guid,
				isComplete);

		retrievedItem.setIsCompleted(isComplete);

		this.checklistItemRepository.save(retrievedItem);
	}

}
