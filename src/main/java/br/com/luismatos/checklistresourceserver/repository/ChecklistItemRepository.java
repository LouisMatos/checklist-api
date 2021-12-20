package br.com.luismatos.checklistresourceserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.luismatos.checklistresourceserver.entity.ChecklistItemEntity;

@Repository
public interface ChecklistItemRepository extends PagingAndSortingRepository<ChecklistItemEntity, Long> {

	Optional<ChecklistItemEntity> findByGuid(String guid);

	List<ChecklistItemEntity> findByDescriptionAndIsCompleted(String description, boolean isCompleted);
	
	List<ChecklistItemEntity> findByCategoryGuid(String guid);

}
