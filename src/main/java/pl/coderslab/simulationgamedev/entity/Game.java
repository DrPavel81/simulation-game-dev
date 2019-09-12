package pl.coderslab.simulationgamedev.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
@Entity
public abstract class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nameOfGame;

    @Min(1)
    private int numberOfTeammates;

    private int numberOfPhase;

    public int getNumberOfPhase() {
        return numberOfPhase;
    }

    public void setNumberOfPhase(int numberOfPhase) {
        this.numberOfPhase = numberOfPhase;
    }

    public int getNumberOfTeammates() {
        return numberOfTeammates;
    }

    public void setNumberOfTeammates(int numberOfTeammates) {
        this.numberOfTeammates = numberOfTeammates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfGame() {
        return nameOfGame;
    }

    public void setNameOfGame(String nameOfGame) {
        this.nameOfGame = nameOfGame;
    }


}
