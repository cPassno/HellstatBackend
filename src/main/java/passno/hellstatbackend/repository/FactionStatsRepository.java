package passno.hellstatbackend.repository;

import passno.hellstatbackend.model.PlanetStats;

import java.time.LocalDateTime;
import java.util.List;

public interface FactionStatsRepository {
    List<PlanetStats> findAllInRange(LocalDateTime start, LocalDateTime end);
}
