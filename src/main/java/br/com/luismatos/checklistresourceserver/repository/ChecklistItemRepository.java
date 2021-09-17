package br.com.luismatos.checklistresourceserver.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.luismatos.checklistresourceserver.entity.ChecklistItemEntity;

public interface ChecklistItemRepository extends PagingAndSortingRepository<ChecklistItemRepository, Long> {

	Optional<ChecklistItemEntity> findByGuid(String guid);

	List<ChecklistItemEntity> findByDescriptionAndCompleted(String description, boolean isCompleted);

}
