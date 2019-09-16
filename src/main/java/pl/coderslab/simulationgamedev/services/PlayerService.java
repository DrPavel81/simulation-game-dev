package pl.coderslab.simulationgamedev.services;

import org.springframework.stereotype.Service;
import pl.coderslab.simulationgamedev.entity.Basketball;
import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.entity.Player;
import pl.coderslab.simulationgamedev.repositories.BasketballRepository;
import pl.coderslab.simulationgamedev.repositories.PlayerRepository;
@Service
public class PlayerService implements PlayerInterface{

    BasketballRepository basketballRepository;
    PlayerRepository playerRepository;

    public PlayerService(BasketballRepository basketballRepository, PlayerRepository playerRepository) {
        this.basketballRepository = basketballRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Player create(String playerName, Game game) throws Exception {
        Player player = new Player();
        player.setName(playerName);
        playerRepository.save(player);


        String gameType = game.getClass().getName();
        switch (gameType){
            case "pl.coderslab.simulationgamedev.entity.Basketball":
                    createForBasketball(player, game);
                break;
            default:
                throw new Exception("Wrong game type");
        }

        return player;
    }


    protected Player createForBasketball(Player player, Game game) throws Exception{
        Basketball basketball = (Basketball) game;
        if(basketball.getPlayer1() == null){
            basketball.setPlayer1(player);
        }else if(basketball.getPlayer2()==null){
            basketball.setPlayer2(player);
        }else{
            throw new Exception("Have reached player limit");
        }
        basketballRepository.save(basketball);
        return player;
    }
}
