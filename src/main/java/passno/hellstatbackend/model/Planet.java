package passno.hellstatbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("planets")
public class Planet {

    @Id
    @Column("planetIndex")
    private Integer planetIndex;

    private String name;

    public Planet() {}

    public Planet(Integer planetIndex, String name) {
        this.planetIndex = planetIndex;
        this.name = name;
    }

    public Integer getPlanetIndex() { return planetIndex; }
    public void setPlanetIndex(Integer planetIndex) { this.planetIndex = planetIndex; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
