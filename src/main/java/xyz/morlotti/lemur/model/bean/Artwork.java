package xyz.morlotti.lemur.model.bean;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "lm_artworks")
@Table(name = "lm_artworks", indexes = {
	@Index(name = "uniqueIndex3", columnList = "wc_id", unique = true)
})
public class Artwork
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "wc_id", nullable = true)
	private Integer wcId;

	@Column(name = "wc_permalink", nullable = true, length = 512)
	private String wcPermalink;

	@Column(name = "name", nullable = false, length = 256)
	private String name;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "artistFK", nullable = true)
	private Artist artist;

	public void setArtistId(Integer id)
	{
		if(id > 0)
		{
			artist = new Artist();

			artist.setId(id);
		}
	}

	public Integer getArtistId()
	{
		if(artist != null)
		{
			return artist.getId();
		}
		else
		{
			return -1;
		}
	}

	public String getArtistPseudo()
	{
		if(artist != null)
		{
			return artist.getPseudo();
		}
		else
		{
			return "N/A";
		}
	}

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
	private Set<ArtworkTag> artworkTag;

	public String getTagString()
	{
		// On stream tous les bridges, récupère les tag names, et on colle les tag names
		// en une string séparée par des espaces

		return artworkTag.stream().map(x -> x.getTagName()).collect(Collectors.joining(" "));
	}
}
