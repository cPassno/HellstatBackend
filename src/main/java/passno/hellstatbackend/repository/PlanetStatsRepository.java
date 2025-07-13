package passno.hellstatbackend.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import passno.hellstatbackend.model.Planet;
import passno.hellstatbackend.model.PlanetStats;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PlanetStatsRepository extends CrudRepository<PlanetStats, Long> {

    @Query("""
    SELECT planetIndex, timestamp, players, deaths, 
           faction, isActive, missionsWon, missionsLost,
           missionTime, bugKills, automatonKills, illuminateKills,
           bulletsFired, bulletsHit, timePlayed, revives, friendlies,
           missionSuccessRate, accuracy
    FROM planetStats
    WHERE planetIndex = :planetIndex
      AND timestamp BETWEEN :start AND :end
    ORDER BY timestamp
""")
    List<PlanetStats> getPlanetRange(@Param("planetIndex") Integer planetIndex,
                                     @Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end);
}

