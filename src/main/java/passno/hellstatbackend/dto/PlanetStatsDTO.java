package passno.hellstatbackend.dto;

import passno.hellstatbackend.model.PlanetStats;

import java.time.LocalDateTime;

public class PlanetStatsDTO {
    private LocalDateTime timestamp;
    private Integer players;
    private Long deaths;
    private Long bugKills;
    private Long automatonKills;
    private Long illuminateKills;
    private Long missionsWon;
    private Long missionsLost;
    private Double kdr;
    private Double missionSuccessRate;

    public PlanetStatsDTO(PlanetStats stats) {
        this.timestamp = stats.getTimestamp();
        this.players = stats.getPlayers();
        this.deaths = stats.getDeaths();
        this.bugKills = stats.getBugKills();
        this.automatonKills = stats.getAutomatonKills();
        this.illuminateKills = stats.getIlluminateKills();
        this.missionsWon = stats.getMissionsWon();
        this.missionsLost = stats.getMissionsLost();

        long totalKills = safeSum(stats.getBugKills(), stats.getAutomatonKills(), stats.getIlluminateKills());
        long deathCount = stats.getDeaths() != null ? stats.getDeaths() : 0L;
        this.kdr = deathCount == 0 ? null : (double) totalKills / deathCount;

        long totalMissions = safeSum(stats.getMissionsWon(), stats.getMissionsLost());
        this.missionSuccessRate = totalMissions == 0 ? null : (double) stats.getMissionsWon() * 100.0 / totalMissions;
    }

    private long safeSum(Long... values) {
        long sum = 0;
        for (Long v : values) {
            if (v != null) sum += v;
        }
        return sum;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public Integer getPlayers() { return players; }
    public Long getDeaths() { return deaths; }
    public Long getBugKills() { return bugKills; }
    public Long getAutomatonKills() { return automatonKills; }
    public Long getIlluminateKills() { return illuminateKills; }
    public Long getMissionsWon() { return missionsWon; }
    public Long getMissionsLost() { return missionsLost; }
    public Double getKdr() { return kdr; }
    public Double getMissionSuccessRate() { return missionSuccessRate; }
}
