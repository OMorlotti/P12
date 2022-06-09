package xyz.morlotti.lemur.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "lm_config")
@Table(name = "lm_config", indexes = {
	@Index(name = "uniqueIndex5", columnList = "param_key", unique = true)
})
public class Config
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "param_key", nullable = false, length = 256)
	String key;

	@Column(name = "param_val", nullable = true, length = 4096)
	String val;

	@JsonIgnore
	@org.hibernate.annotations.UpdateTimestamp
	@Column(name = "modified", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDate modified;
}
