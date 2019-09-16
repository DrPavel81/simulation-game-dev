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
import java.util.stream.Collectors;

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
        basketball.setNumberOfPhase(4);
        basketball.setNumberOfTeammates(5);
        basketball.setPlayerLimit(2);
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
    public void moveToNextPhase(Game game) throws Exception {
        Basketball currentGame = (Basketball) game;

        if(currentGame.getCurrentPhase()==currentGame.getNumberOfPhase()){
            throw new Exception("End of game");
        }

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
        currentGame.setCurrentPhase( currentGame.getCurrentPhase() + 1);
        basketballRepository.save(currentGame);
    }

    @Override
    public Player getPlayer(Game game, int playerNumber) throws Exception {

        Basketball basketball = (Basketball) game;
        switch (playerNumber){
            case 1: return basketball.getPlayer1();
            case 2: return basketball.getPlayer2();
            default: throw new Exception("Player number over limit");
        }

    }

    @Override
    public List<Teammates> getAvailableTeammatesToChose(Game game) {
        Basketball basketball = (Basketball) game;
        List<Teammates> allTeammates = teammateRepository.findAllByType("Basketball");

        List<Player> players = new ArrayList<>();
        players.add(basketball.getPlayer1());
        players.add(basketball.getPlayer2());

        List<Teammates> alreadyChosen = teammateRepository.findAllByPlayersIn(players);
        return allTeammates.stream()
                .filter( teammate -> !alreadyChosen.contains(teammate) )
                .collect(Collectors.toList());
    }

    public Game getDetails(String type, Long gameId) {
       return  basketballRepository.getOne(gameId);
    }
}

