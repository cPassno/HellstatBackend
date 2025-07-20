package passno.hellstatbackend.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import passno.hellstatbackend.model.PlanetStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class FactionStatsRepositoryImpl implements FactionStatsRepository {

    private final JdbcTemplate jdbcTemplate;

    public FactionStatsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PlanetStats> findAllInRange(LocalDateTime start, LocalDateTime end) {
        String sql = """
            SELECT planetIndex, timestamp, players, deaths, 
                   faction, isActive, missionsWon, missionsLost,
                   missionTime, bugKills, automatonKills, illuminateKills,
                   bulletsFired, bulletsHit, timePlayed, revives, friendlies,
                   missionSuccessRate, accuracy
            FROM planetStats
            WHERE timestamp BETWEEN ? AND ?
            ORDER BY timestamp
        """;

        return jdbcTemplate.query(sql, new Object[]{start, end}, new PlanetStatsRowMapper());
    }

    private static class PlanetStatsRowMapper implements RowMapper<PlanetStats> {
        @Override
        public PlanetStats mapRow(ResultSet rs, int rowNum) throws SQLException {
            PlanetStats ps = new PlanetStats();

            ps.setPlanetIndex(rs.getInt("planetIndex"));
            ps.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            ps.setPlayers(rs.getObject("players", Integer.class));
            ps.setDeaths(rs.getObject("deaths", Long.class));
            ps.setFaction(rs.getObject("faction", Integer.class));
            ps.setIsActive(rs.getObject("isActive", Boolean.class));
            ps.setMissionsWon(rs.getObject("missionsWon", Long.class));
            ps.setMissionsLost(rs.getObject("missionsLost", Long.class));
            ps.setMissionTime(rs.getObject("missionTime", Long.class));
            ps.setBugKills(rs.getObject("bugKills", Long.class));
            ps.setAutomatonKills(rs.getObject("automatonKills", Long.class));
            ps.setIlluminateKills(rs.getObject("illuminateKills", Long.class));
            ps.setBulletsFired(rs.getObject("bulletsFired", Long.class));
            ps.setBulletsHit(rs.getObject("bulletsHit", Long.class));
            ps.setTimePlayed(rs.getObject("timePlayed", Long.class));
            ps.setRevives(rs.getObject("revives", Long.class));
            ps.setFriendlies(rs.getObject("friendlies", Long.class));
            ps.setMissionSuccessRate(rs.getObject("missionSuccessRate", Integer.class));
            ps.setAccuracy(rs.getObject("accuracy", Integer.class));

            return ps;
        }
    }
}
