package pl.coderslab.simulationgamedev.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Teammates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String fullName;

    @Max(99)
    private int rating;

    private String type;

    @ManyToMany(mappedBy = "teammates")
    private List<Player> players;

    public Teammates(@NotEmpty String fullName, @Max(99) int rating, String type) {
        this.fullName = fullName;
        this.rating = rating;
        this.type = type;
    }

    public Teammates() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}