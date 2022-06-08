package xyz.morlotti.lemur.model.bean;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "lm_artists")
@Table(name = "lm_artists", indexes = {
	@Index(name = "uniqueIndex1", columnList = "pseudo", unique = true),
})
public class Artist
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "first_name", nullable = true, length = 256)
	private String firstName;

	@Column(name = "last_name", nullable = true, length = 256)
	private String lastName;

	@Column(name = "pseudo", nullable = true, length = 128)
	private String pseudo;

	@Email
	@Column(name = "email", nullable = true, length = 256)
	private String email;

	@Column(name = "year_of_birth", nullable = true, columnDefinition = "YEAR")
	private Integer yearOfBirth;

	@Column(name = "year_of_death", nullable = true, columnDefinition = "YEAR")
	private Integer yearOfDeath;

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

	@JsonIgnore
	@ToString.Exclude // Pour éviter la récursion
	@OneToMany(mappedBy = "artwork", fetch = FetchType.EAGER)
	private Set<ArtworkTag> artistTags;

	public String getTagString()
	{
		return artistTags.stream().map(x -> x.getTagName()).collect(Collectors.joining(" "));
	}
}
