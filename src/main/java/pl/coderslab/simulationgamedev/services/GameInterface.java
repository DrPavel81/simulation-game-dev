package pl.coderslab.simulationgamedev.services;

import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.entity.Player;
import pl.coderslab.simulationgamedev.entity.Teammates;

import java.util.List;

public interface GameInterface {
    Game createGame(String gameType);

    boolean addTeamMember(Game game, Player player,  List<Teammates> teammate);

    void moveToNextPhase(Game game);

}
