package pl.coderslab.simulationgamedev.services;

import org.springframework.stereotype.Service;
import pl.coderslab.simulationgamedev.entity.Basketball;
import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.entity.Player;
import pl.coderslab.simulationgamedev.entity.Teammates;
import pl.coderslab.simulationgamedev.repositories.BasketballRepository;
import pl.coderslab.simulationgamedev.repositories.PlayerRepository;
import pl.coderslab.simulationgamedev.repositories.TeammateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BasketballService implements GameInterface {
    private final BasketballRepository basketballRepository;
    private final TeammateRepository teammateRepository;
    private final PlayerRepository playerRepository;

    public BasketballService(BasketballRepository basketballRepository,
                             TeammateRepository teammateRepository,
                             PlayerRepository playerRepository) {
        this.basketballRepository = basketballRepository;
        this.teammateRepository = teammateRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Game createGame(String gameType) {
        Basketball basketball = new Basketball();
        basketball.setNameOfGame("Basketball");
        basketball.setNumberOfTeammates(5);
        basketballRepository.save(basketball);
        return basketball;
    }

    @Override
    public boolean addTeamMember(Game game, Player player, List<Teammates> teammates) {
        boolean success = true;
        for (Teammates teammate : teammates) {
            if (game.getNumberOfTeammates() > player.getTeammates().size() && teammate.getType().equals("Basketball")) {
                player.getTeammates().add(teammate);
            } else {
                success = false;
            }
        }
        if (success) {
            playerRepository.save(player);
        }
        return success;
    }

    @Override
    public void moveToNextPhase(Game game) {
        Basketball currentGame = (Basketball) game;
        Random r = new Random();
        int firstTeamScore = 0;
        int secondTeamScore = 0;
        List<Teammates> firstTeamMembers = currentGame.getPlayer1().getTeammates();
        List<Teammates> secondTeamMembers = currentGame.getPlayer2().getTeammates();
        for (int i = 0; i < firstTeamMembers.size(); i++) {
            int diceThrow = r.nextInt(6) + 1;
            firstTeamScore += firstTeamMembers.get(i).getRating() * diceThrow / 55;

            int diceThrow2 = r.nextInt(6) + 1;
            secondTeamScore += secondTeamMembers.get(i).getRating() * diceThrow2 / 55;
        }

        currentGame.setScore1(currentGame.getScore1() + firstTeamScore);
        currentGame.setScore2(currentGame.getScore2() + secondTeamScore);

        basketballRepository.save(currentGame);
    }

}

