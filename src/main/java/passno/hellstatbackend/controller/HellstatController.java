package passno.hellstatbackend.controller;

import passno.hellstatbackend.model.Planet;
import passno.hellstatbackend.model.PlanetStats;
import passno.hellstatbackend.repository.PlanetStatsRepository;
import passno.hellstatbackend.repository.PlanetRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/hellstat")
public class HellstatController {

    private final PlanetStatsRepository statsRepo;
    private final PlanetRepository planetRepo;

    public HellstatController(PlanetStatsRepository statsRepo, PlanetRepository planetRepo) {
        this.statsRepo = statsRepo;
        this.planetRepo = planetRepo;
    }

    @GetMapping("/planetStats")
    public List<PlanetStats> getPlanetStats(
            @RequestParam Integer planetIndex,
            @RequestParam String start,
            @RequestParam String end) {

        LocalDateTime startTime = LocalDate.parse(start).atStartOfDay();
        LocalDateTime endTime = LocalDate.parse(end).atTime(LocalTime.MAX);

        return statsRepo.getPlanetRange(planetIndex, startTime, endTime);
    }

    @GetMapping("/planets")
    public List<Planet> getAllPlanets() {
        return planetRepo.findAllSortedByName();
    }
}

