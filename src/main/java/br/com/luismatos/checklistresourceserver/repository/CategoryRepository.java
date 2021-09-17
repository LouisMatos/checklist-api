package br.com.luismatos.checklistresourceserver.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;

public interface CategoryRepository extends PagingAndSortingRepository<CategoryRepository, Long> {

	Optional<CategoryEntity> findByName(String name);

	Optional<CategoryEntity> findByGuid(String guid);
}
