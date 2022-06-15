package xyz.morlotti.lemur.model.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import xyz.morlotti.lemur.model.bean.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>
{
	@Query("SELECT t FROM lm_tags t WHERE t.id = :id")
	public Optional<Tag> findById(@Param("id") String id);

	@Query("SELECT t FROM lm_tags t WHERE t.name = :name")
	public Optional<Tag> findByName(@Param("name") String name);
}
