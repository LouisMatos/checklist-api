package br.com.luismatos.checklistresourceserver.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.xml.bind.ValidationException;

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

import br.com.luismatos.checklistresourceserver.dto.ChecklistItemDTO;
import br.com.luismatos.checklistresourceserver.dto.newResourceDTO;
import br.com.luismatos.checklistresourceserver.entity.ChecklistItemEntity;
import br.com.luismatos.checklistresourceserver.service.ChecklistItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/api/checklist-items")
public class ChecklistItemController {

	private ChecklistItemService checklistItemService;

	public ChecklistItemController(ChecklistItemService checklistItemService) {
		this.checklistItemService = checklistItemService;
	}

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChecklistItemDTO>> getAllChecklistItems() {

		List<ChecklistItemDTO> response = StreamSupport
				.stream(this.checklistItemService.findAllChecklistItems().spliterator(), false)
				.map(ChecklistItemEntity -> ChecklistItemDTO.toDTO(ChecklistItemEntity)).collect(Collectors.toList());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(description = "Inserido um novo checklist item")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Criado um novo Checklist item"),
			@ApiResponse(responseCode = "422", description = "guid da categoria não foi encontrado") })
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<newResourceDTO> createNewChecklistItem(@RequestBody ChecklistItemDTO checklistItemDTO)
			throws ValidationException {

		if (checklistItemDTO.getCategory().getGuid() == null) {
			throw new ValidationException("Categoria não pode ser nulo");
		}

		ChecklistItemEntity checklistItemEntity = this.checklistItemService.addChecklistItem(
				checklistItemDTO.getDescription(), checklistItemDTO.getIsCompleted(), checklistItemDTO.getDeadline(),
				checklistItemDTO.getCategory().getGuid());

		return new ResponseEntity<>(new newResourceDTO(checklistItemEntity.getGuid()), HttpStatus.CREATED);
	}

	@PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateChecklistItem(@RequestBody ChecklistItemDTO checklistItemDTO)
			throws ValidationException {

		if (!StringUtils.hasLength(checklistItemDTO.getGuid())) {
			throw new ValidationException("Checklist item guid é um campo obrigatório");
		}

		this.checklistItemService.updateChecklistItem(checklistItemDTO.getGuid(), checklistItemDTO.getDescription(),
				checklistItemDTO.getIsCompleted(), checklistItemDTO.getDeadline(),
				checklistItemDTO.getCategory() != null ? checklistItemDTO.getCategory().getGuid() : null);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteChecklistItem(@PathVariable String guid) {
		this.checklistItemService.deleteChecklistItem(guid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
