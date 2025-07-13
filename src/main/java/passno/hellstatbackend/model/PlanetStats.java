package passno.hellstatbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("planetStats")
public class PlanetStats {

    @Id
    @Column("planetIndex")
    private Integer planetIndex;

    @Column("timestamp")
    private LocalDateTime timestamp;

    @Column("isActive")
    private Boolean isActive;

    @Column("players")
    private Integer players;

    @Column("faction")
    private Integer faction;

    @Column("missionsWon")
    private Long missionsWon;

    @Column("missionsLost")
    private Long missionsLost;

    @Column("missionTime")
    private Long missionTime;

    @Column("bugKills")
    private Long bugKills;

    @Column("automatonKills")
    private Long automatonKills;

    @Column("illuminateKills")
    private Long illuminateKills;

    @Column("bulletsFired")
    private Long bulletsFired;

    @Column("bulletsHit")
    private Long bulletsHit;

    @Column("timePlayed")
    private Long timePlayed;

    @Column("deaths")
    private Long deaths;

    @Column("revives")
    private Long revives;

    @Column("friendlies")
    private Long friendlies;

    @Column("missionSuccessRate")
    private Integer missionSuccessRate;

    @Column("accuracy")
    private Integer accuracy;


    public Integer getPlanetIndex() {
        return planetIndex;
    }

    public void setPlanetIndex(Integer planetIndex) {
        this.planetIndex = planetIndex;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getPlayers() {
        return players;
    }

    public void setPlayers(Integer players) {
        this.players = players;
    }

    public Integer getFaction() {
        return faction;
    }

    public void setFaction(Integer faction) {
        this.faction = faction;
    }

    public Long getMissionsWon() {
        return missionsWon;
    }

    public void setMissionsWon(Long missionsWon) {
        this.missionsWon = missionsWon;
    }

    public Long getMissionsLost() {
        return missionsLost;
    }

    public void setMissionsLost(Long missionsLost) {
        this.missionsLost = missionsLost;
    }

    public Long getMissionTime() {
        return missionTime;
    }

    public void setMissionTime(Long missionTime) {
        this.missionTime = missionTime;
    }

    public Long getBugKills() {
        return bugKills;
    }

    public void setBugKills(Long bugKills) {
        this.bugKills = bugKills;
    }

    public Long getAutomatonKills() {
        return automatonKills;
    }

    public void setAutomatonKills(Long automatonKills) {
        this.automatonKills = automatonKills;
    }

    public Long getIlluminateKills() {
        return illuminateKills;
    }

    public void setIlluminateKills(Long illuminateKills) {
        this.illuminateKills = illuminateKills;
    }

    public Long getBulletsFired() {
        return bulletsFired;
    }

    public void setBulletsFired(Long bulletsFired) {
        this.bulletsFired = bulletsFired;
    }

    public Long getBulletsHit() {
        return bulletsHit;
    }

    public void setBulletsHit(Long bulletsHit) {
        this.bulletsHit = bulletsHit;
    }

    public Long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Long getRevives() {
        return revives;
    }

    public void setRevives(Long revives) {
        this.revives = revives;
    }

    public Long getFriendlies() {
        return friendlies;
    }

    public void setFriendlies(Long friendlies) {
        this.friendlies = friendlies;
    }

    public Integer getMissionSuccessRate() {
        return missionSuccessRate;
    }

    public void setMissionSuccessRate(Integer missionSuccessRate) {
        this.missionSuccessRate = missionSuccessRate;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }
}
