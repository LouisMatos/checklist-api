package br.com.luismatos.checklistresourceserver.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(indexes = { @Index(name = "IDX_GUID_CK_IT", columnList = "guid") })
public class ChecklistItemEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long checklistItemId;

	private boolean isCompleted;

	private String description;

	private LocalTime deadline;

	private LocalTime postedDate;

	@ManyToOne
	private CategoryEntity category;
}