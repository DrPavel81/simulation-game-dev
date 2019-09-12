package pl.coderslab.simulationgamedev.services;

import org.springframework.stereotype.Service;
import pl.coderslab.simulationgamedev.entity.*;
import pl.coderslab.simulationgamedev.repositories.FootballRepository;
import pl.coderslab.simulationgamedev.repositories.PlayerRepository;
import pl.coderslab.simulationgamedev.repositories.TeammateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FootballService implements GameInterface{
    private FootballRepository footballRepository;
    private TeammateRepository teammateRepository;
    private PlayerRepository playerRepository;

    public FootballService(FootballRepository footballRepository,
                           TeammateRepository teammateRepository, PlayerRepository playerRepository) {
        this.footballRepository = footballRepository;
        this.teammateRepository = teammateRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Game createGame(String gameType) {
        Football football = new Football();
        football.setNameOfGame("Football");
        football.setNumberOfTeammates(11);
        footballRepository.save(football);
        return football;

    }

    @Override
    public boolean addTeamMember(Game game, Player player, List<Teammates>teammates) {
        boolean success = true;
        for (Teammates teammate : teammates) {
            if (game.getNumberOfTeammates() > player.getTeammates().size() && teammate.getType().equals("Football")) {
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

        Football currentGame = (Football) game;
        Random r = new Random();
        int firstTeamScore = 0;
        int secondTeamScore = 0;
        List<Teammates> firstTeamMembers = currentGame.getPlayer1().getTeammates();
        List<Teammates> secondTeamMembers = new ArrayList<>();
        for (int i = 0; i < firstTeamMembers.size(); i++) {
            int diceThrow = r.nextInt(6) + 1;
            firstTeamScore += firstTeamMembers.get(i).getRating() * diceThrow / 3000;

            int diceThrow2 = r.nextInt(6) + 1;
            secondTeamScore += secondTeamMembers.get(i).getRating() * diceThrow2 / 3000;
        }

        currentGame.setScore1(currentGame.getScore1() + firstTeamScore);
        currentGame.setScore2(currentGame.getScore2() + secondTeamScore);

        footballRepository.save(currentGame);
    }
}
