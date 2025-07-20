package passno.hellstatbackend.controller;

import org.springframework.web.bind.annotation.*;
import passno.hellstatbackend.model.Planet;
import passno.hellstatbackend.model.PlanetStats;
import passno.hellstatbackend.repository.FactionStatsRepository;
import passno.hellstatbackend.repository.PlanetRepository;
import passno.hellstatbackend.repository.PlanetStatsRepository;
import passno.hellstatbackend.dto.FactionStatsDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hellstat")
public class HellstatController {

    private final PlanetStatsRepository statsRepo;
    private final PlanetRepository planetRepo;
    private final FactionStatsRepository factionStatsRepo;

    public HellstatController(PlanetStatsRepository statsRepo,
                              PlanetRepository planetRepo,
                              FactionStatsRepository factionStatsRepo) {
        this.statsRepo = statsRepo;
        this.planetRepo = planetRepo;
        this.factionStatsRepo = factionStatsRepo;
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

    @GetMapping("/factionStats")
    public List<FactionStatsDTO> getFactionStats(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDateTime startTime = LocalDate.parse(start).atStartOfDay();
        LocalDateTime endTime = LocalDate.parse(end).atTime(LocalTime.MAX);

        List<PlanetStats> allStats = factionStatsRepo.findAllInRange(startTime, endTime);

        Map<LocalDateTime, Map<String, List<PlanetStats>>> grouped = allStats.stream()
                .collect(Collectors.groupingBy(
                        PlanetStats::getTimestamp,
                        Collectors.groupingBy(this::inferFaction)
                ));

        List<FactionStatsDTO> result = new ArrayList<>();

        for (var entry : grouped.entrySet()) {
            LocalDateTime timestamp = entry.getKey();
            Map<String, List<PlanetStats>> factionGroups = entry.getValue();

            for (var factionEntry : factionGroups.entrySet()) {
                String faction = factionEntry.getKey();
                List<PlanetStats> statsList = factionEntry.getValue();

                long totalPlayers = statsList.stream().filter(ps -> ps.getPlayers() != null).mapToLong(PlanetStats::getPlayers).sum();
                long totalDeaths = statsList.stream().filter(ps -> ps.getDeaths() != null).mapToLong(PlanetStats::getDeaths).sum();
                long bugKills = statsList.stream().filter(ps -> ps.getBugKills() != null).mapToLong(PlanetStats::getBugKills).sum();
                long autoKills = statsList.stream().filter(ps -> ps.getAutomatonKills() != null).mapToLong(PlanetStats::getAutomatonKills).sum();
                long illuKills = statsList.stream().filter(ps -> ps.getIlluminateKills() != null).mapToLong(PlanetStats::getIlluminateKills).sum();
                long missionsWon = statsList.stream().filter(ps -> ps.getMissionsWon() != null).mapToLong(PlanetStats::getMissionsWon).sum();
                long missionsLost = statsList.stream().filter(ps -> ps.getMissionsLost() != null).mapToLong(PlanetStats::getMissionsLost).sum();

                long totalKills = bugKills + autoKills + illuKills;
                double kdr = totalDeaths == 0 ? 0.0 : (double) totalKills / totalDeaths;
                long totalMissions = missionsWon + missionsLost;
                double missionSuccessRate = totalMissions == 0 ? 0.0 : (double) missionsWon * 100.0 / totalMissions;

                result.add(new FactionStatsDTO(
                        timestamp,
                        faction,
                        totalPlayers,
                        totalDeaths,
                        kdr,
                        missionsWon,
                        missionSuccessRate
                ));
            }
        }

        result.sort(Comparator.comparing(FactionStatsDTO::getTimestamp));
        return result;
    }

    private String inferFaction(PlanetStats ps) {
        long bug = ps.getBugKills() != null ? ps.getBugKills() : 0;
        long illu = ps.getIlluminateKills() != null ? ps.getIlluminateKills() : 0;
        long auto = ps.getAutomatonKills() != null ? ps.getAutomatonKills() : 0;

        if (bug == 0 && illu == 0 && auto == 0) return "None";
        if (bug >= illu && bug >= auto) return "Terminid";
        if (illu >= bug && illu >= auto) return "Illuminate";
        return "Automaton";
    }
}
