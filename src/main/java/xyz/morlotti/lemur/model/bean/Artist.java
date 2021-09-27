package xyz.morlotti.lemur.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "lm_artists")
@Table(name = "lm_artists", indexes = {
	@Index(name = "uniqueIndex1", columnList = "pseudo", unique = true),
	@Index(name = "uniqueIndex2", columnList = "email", unique = true)
})
public class Artist
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "first_name", nullable = true, length = 256)
	private String first_name;

	@Column(name = "last_name", nullable = true, length = 256)
	private String last_name;

	@Column(name = "pseudo", nullable = true, length = 128)
	private String pseudo;

	@Email
	@Column(name = "email", nullable = true, length = 256)
	private String email;

	@Column(name = "date_of_birth", nullable = true)
	private Date date_of_birth;

	@Column(name = "date_of_death", nullable = true)
	private Date date_of_death;

	@Column(name = "description", nullable = true, length = 4096)
	private String description;

	@JsonIgnore
	@org.hibernate.annotations.CreationTimestamp
	@Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDate created;

	@JsonIgnore
	@org.hibernate.annotations.UpdateTimestamp
	@Column(name = "modified", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDate modified;
}
