package xyz.morlotti.lemur.model.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import xyz.morlotti.lemur.model.bean.Config;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer>
{
}
