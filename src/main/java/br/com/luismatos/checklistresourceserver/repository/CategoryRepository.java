package br.com.luismatos.checklistresourceserver.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {

	Optional<CategoryEntity> findByName(String name);

	Optional<CategoryEntity> findByGuid(String guid);
}
