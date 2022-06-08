package xyz.morlotti.lemur.model.bean;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "lm_artisttag")
@Table(name = "lm_artisttag", indexes = {
	@Index(name = "uniqueIndex2", columnList = "artistFK, tagFK", unique = true)
})
public class ArtistTag
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "artistFK", nullable = false)
	private Artist artist;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "tagFK", nullable = false)
	private Tag tag;

	@org.hibernate.annotations.CreationTimestamp
	@Column(name = "created", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDate created;

	@ToString.Include
	public String getArtistPseudo()
	{
		return artist.getPseudo();
	}

	@ToString.Include
	public String getTagName()
	{
		return tag.getName();
	}
}
