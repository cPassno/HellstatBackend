package passno.hellstatbackend.dto;

import java.time.LocalDateTime;

public class FactionStatsDTO {
    private LocalDateTime timestamp;
    private String factionName;
    private Long players;
    private Long deaths;
    private Double kdr; // kill/death ratio
    private Long missionsWon;
    private Double missionSuccessRate;

    public FactionStatsDTO(LocalDateTime timestamp, String factionName, Long players, Long deaths, Double kdr, Long missionsWon, Double missionSuccessRate) {
        this.timestamp = timestamp;
        this.factionName = factionName;
        this.players = players;
        this.deaths = deaths;
        this.kdr = kdr;
        this.missionsWon = missionsWon;
        this.missionSuccessRate = missionSuccessRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public Long getPlayers() {
        return players;
    }

    public void setPlayers(Long players) {
        this.players = players;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Double getKdr() {
        return kdr;
    }

    public void setKdr(Double kdr) {
        this.kdr = kdr;
    }

    public Long getMissionsWon() {
        return missionsWon;
    }

    public void setMissionsWon(Long missionsWon) {
        this.missionsWon = missionsWon;
    }

    public Double getMissionSuccessRate() {
        return missionSuccessRate;
    }

    public void setMissionSuccessRate(Double missionSuccessRate) {
        this.missionSuccessRate = missionSuccessRate;
    }
}
