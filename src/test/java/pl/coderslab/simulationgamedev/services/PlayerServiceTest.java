package pl.coderslab.simulationgamedev.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.simulationgamedev.entity.Basketball;
import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.entity.Player;
import pl.coderslab.simulationgamedev.repositories.BasketballRepository;
import pl.coderslab.simulationgamedev.repositories.FootballRepository;
import pl.coderslab.simulationgamedev.repositories.PlayerRepository;
import pl.coderslab.simulationgamedev.repositories.TeammateRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerServiceTest {
    @Autowired
    private BasketballRepository basketballRepository;
    @Autowired
    private FootballRepository footballRepository;
    @Autowired
    private TeammateRepository teammateRepository;
    @Autowired
    PlayerRepository playerRepository;

    private PlayerService playerService;

    private GameService gameService;


    @Before
    public void setUp() throws Exception {
        FootballService footballService =  new FootballService(footballRepository, teammateRepository, playerRepository);
        BasketballService basketballService = new BasketballService(basketballRepository,teammateRepository, playerRepository);
        this.playerService = new PlayerService(basketballRepository, playerRepository);
        this.gameService = new GameService(basketballService,footballService);
    }

    @Test
    public void create() throws Exception {
        //given
        Game game = gameService.createGame("Basketball");
        String playerName="jon";
        String playerTwoName="greg";
        //when
        Player player1 = playerService.create(playerName,game);

        Player player2 = playerService.create(playerTwoName,game);
        //then
        assertEquals("jon",player1.getName());
        assertNotNull(player1.getId());
        assertEquals("greg",player2.getName());
        assertNotNull(player2.getId());




    }
    @Test(expected = Exception.class)
    public void createForBasketballError() throws Exception {
        //given
        Game game = gameService.createGame("Basketball");
        Player player1 = new Player();
        player1.setName("jon");
        Player player2 = new Player();
        player2.setName("greg");
        Player player3 = new Player();
        player3.setName("paul");
        //when
        playerService.createForBasketball(player1,game);
        playerService.createForBasketball(player2,game);
        playerService.createForBasketball(player3,game);
        //then throws


    }
}