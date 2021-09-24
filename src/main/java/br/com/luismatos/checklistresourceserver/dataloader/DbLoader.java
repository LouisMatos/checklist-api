package br.com.luismatos.checklistresourceserver.dataloader;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.com.luismatos.checklistresourceserver.entity.CategoryEntity;
import br.com.luismatos.checklistresourceserver.repository.CategoryRepository;
import br.com.luismatos.checklistresourceserver.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Profile("data-load")
@Slf4j
@Component
public class DbLoader implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryService categoryService;

	@Override
	public void run(String... args) throws Exception {

		log.info("Populando o banco de dados com as categorias!");

		List<String> categoryNames = Arrays.asList("Trabalho", "Saúde", "Educação", "Pessoal", "Outros");

		for (String categoryName : categoryNames) {
			Optional<CategoryEntity> catOp = this.categoryRepository.findByName(categoryName);
			if (!catOp.isPresent()) {
				categoryService.addNewCategory(categoryName);
			}
		}

	}

}
