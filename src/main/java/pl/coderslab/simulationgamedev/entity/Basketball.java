package pl.coderslab.simulationgamedev.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Basketball extends Game {

    @ManyToOne
    private Player player1;
    @ManyToOne
    private Player player2;

    private int score1;

    private int score2;

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
