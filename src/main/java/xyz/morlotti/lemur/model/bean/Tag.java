package xyz.morlotti.lemur.model.bean;

import lombok.*;

import javax.persistence.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "lm_tags")
@Table(name = "lm_tags", indexes = {
	@Index(name = "uniqueIndex1", columnList = "name", unique = true)
})
public class Tag
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false, length = 128)
	private String name;
}
