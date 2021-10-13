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
@Entity(name = "lm_artworktag")
@Table(name = "lm_artworktag", indexes = {
	@Index(name = "uniqueIndex1", columnList = "artworkFK, tagFK", unique = true)
})
public class ArtworkTag
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "artworkFK", nullable = false)
	private Artwork artwork;

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
