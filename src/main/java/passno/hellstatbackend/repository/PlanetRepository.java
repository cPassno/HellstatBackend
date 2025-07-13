package passno.hellstatbackend.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import passno.hellstatbackend.model.Planet;

import java.util.List;

public interface PlanetRepository extends CrudRepository<Planet, Integer> {

    @Query("SELECT * FROM planets ORDER BY name ASC")
    List<Planet> findAllSortedByName();
}
