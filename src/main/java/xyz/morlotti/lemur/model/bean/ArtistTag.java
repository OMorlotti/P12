package xyz.morlotti.lemur.model.bean;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "lm_artisttag")
@Table(name = "lm_artisttag", indexes = {
	@Index(name = "uniqueIndex1", columnList = "artistFK, tagFK", unique = true)
})
public class ArtistTag
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "artistFK", nullable = false)
	private Artist artist;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "tagFK", nullable = false)
	private Tag tag;

	@JsonIgnore
	@org.hibernate.annotations.CreationTimestamp
	@Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDate created;
}
