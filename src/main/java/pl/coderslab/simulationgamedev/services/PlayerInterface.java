package pl.coderslab.simulationgamedev.services;

import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.entity.Player;

public interface PlayerInterface {
    Player create(String playerName, Game game) throws Exception;

}
