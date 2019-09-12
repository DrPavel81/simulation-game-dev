package pl.coderslab.simulationgamedev.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.repositories.BasketballRepository;
import pl.coderslab.simulationgamedev.repositories.FootballRepository;
import pl.coderslab.simulationgamedev.repositories.PlayerRepository;
import pl.coderslab.simulationgamedev.repositories.TeammateRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GameServiceTest {

    @Autowired
    private BasketballRepository basketballRepository;
    @Autowired
    private FootballRepository footballRepository;
    @Autowired
    private TeammateRepository teammateRepository;
    @Autowired
    PlayerRepository playerRepository;


    private GameService gameService;


    @Before
    public void setUp() throws Exception {
        FootballService footballService =  new FootballService(footballRepository, teammateRepository, playerRepository);
        BasketballService basketballService = new BasketballService(basketballRepository,teammateRepository, playerRepository);
        this.gameService = new GameService(basketballService, footballService);
    }

    @Test
    public void createGameForBasketball() {
        //given
        String nameOfGame = "Basketball";

        //when
        Game game = this.gameService.createGame(nameOfGame);
        //then
        String className = game.getClass().getName();
        assertEquals("pl.coderslab.simulationgamedev.entity.Basketball", className);
        assertNotNull(game.getId());

    }
    @Test
    public void createGameForFootball() {
        //given
        String nameOfGame = "Football";

        //when
        Game game = this.gameService.createGame(nameOfGame);
        //then
        String className = game.getClass().getName();
        assertEquals("pl.coderslab.simulationgamedev.entity.Football", className);
        assertNotNull(game.getId());

    }

}