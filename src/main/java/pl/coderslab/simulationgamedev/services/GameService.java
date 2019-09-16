package pl.coderslab.simulationgamedev.services;

import org.springframework.stereotype.Service;
import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.entity.Player;
import pl.coderslab.simulationgamedev.entity.Teammates;

import java.util.List;

@Service
public class GameService implements GameInterface{
    private BasketballService basketballService;
    private FootballService footballService;

    public GameService(BasketballService basketballService, FootballService footballService) {
        this.basketballService = basketballService;
        this.footballService = footballService;
    }

    @Override
    public Game createGame(String gameType) {
        Game game;
        switch (gameType){
            case "Basketball":
                    game = basketballService.createGame(gameType);
                break;
            case "Football":
                    game = footballService.createGame(gameType);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + gameType);
        }

        return game;
    }

    @Override
    public boolean addTeamMember(Game game, Player player, List<Teammates> teammate) {
        GameInterface gameService = recognizeServiceForGame(game);
        gameService.addTeamMember(game, player, teammate);
        return true;
    }

    @Override
    public void moveToNextPhase(Game game) throws Exception {
        GameInterface gameService = recognizeServiceForGame(game);
        gameService.moveToNextPhase(game);

    }

    @Override
    public Player getPlayer(Game game, int playerNumber) throws Exception {
        GameInterface gameService = recognizeServiceForGame(game);
        return gameService.getPlayer(game, playerNumber);
    }

    @Override
    public List<Teammates> getAvailableTeammatesToChose(Game game) {
        GameInterface gameService = recognizeServiceForGame(game);
        return gameService.getAvailableTeammatesToChose(game);
    }

    private GameInterface recognizeServiceForGame(Game game){
        String gameType = game.getClass().getName();

        if(gameType.contains("pl.coderslab.simulationgamedev.entity.Basketball")){
            return  basketballService;
        }

        switch (gameType){
            case "pl.coderslab.simulationgamedev.entity.Basketball":
                return  basketballService;

            case "pl.coderslab.simulationgamedev.entity.Football":
                return  footballService;

            default:
                throw new IllegalStateException("Unexpected value: " + gameType);
        }
    }


    public Game getDetails(String type, Long gameId) {
        switch (type){
            case "Basketball": return basketballService.getDetails(type, gameId);
            default: return null;
        }

    }
}
