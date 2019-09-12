package pl.coderslab.simulationgamedev.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.simulationgamedev.entity.Basketball;
import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.entity.Player;
import pl.coderslab.simulationgamedev.entity.Teammates;
import pl.coderslab.simulationgamedev.repositories.BasketballRepository;
import pl.coderslab.simulationgamedev.repositories.FootballRepository;
import pl.coderslab.simulationgamedev.repositories.PlayerRepository;
import pl.coderslab.simulationgamedev.repositories.TeammateRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BasketballServiceTest {

    @Autowired
    private BasketballRepository basketballRepository;
    @Autowired
    private FootballRepository footballRepository;
    @Autowired
    private TeammateRepository teammateRepository;
    @Autowired
    private PlayerRepository playerRepository;

    private GameService gameService;

    private BasketballService basketballService;

    private PlayerService playerService;

    @Before
    public void setUp() throws Exception {
        FootballService footballService = new FootballService(footballRepository, teammateRepository, playerRepository);
        BasketballService basketballService = new BasketballService(basketballRepository,
                teammateRepository,
                playerRepository);
        this.gameService = new GameService(basketballService, footballService);
        this.basketballService = new BasketballService(basketballRepository,teammateRepository,playerRepository);
        this.playerService = new PlayerService(basketballRepository,playerRepository);
    }

    @Test
    public void createBasketballGame() {
        //given
        Basketball basketball = new Basketball();
        basketball.setNameOfGame("Basketball");
        String test = basketball.getNameOfGame();
        //when
        Game game = this.gameService.createGame(test);

        //then
        String className = game.getClass().getName();
        assertEquals("pl.coderslab.simulationgamedev.entity.Basketball", className);
        assertNotNull(game.getId());

    }

    @Test
    public void tryToAddTeamMember()throws Exception{
        //given
        Game game = gameService.createGame("Basketball");
        Player p1 = playerService.create("Jon",game);
        Player p2 = playerService.create("Joel",game);

        List<Teammates> teammates = teammateRepository.findAllByType("Basketball");
        List<Teammates> teammatesP1 = new ArrayList<>();
        List<Teammates> teammatesP2 = new ArrayList<>();
        for (Teammates teammate : teammates) {
            if (teammate.getId()% 2 == 0) {
                teammatesP1.add(teammate);
            }else {
                teammatesP2.add(teammate);
            }
        }

        boolean result1 = gameService.addTeamMember(game, p1,teammatesP1);
        boolean result2 = gameService.addTeamMember(game, p2,teammatesP2);
        //then
        Basketball basketball = (Basketball) game;
        assertTrue(result1);
        assertEquals(game.getNumberOfTeammates(), basketball.getPlayer1().getTeammates().size());

        Player p1FromDB = playerRepository.getOne(p1.getId());
        assertEquals(game.getNumberOfTeammates(), p1FromDB.getTeammates().size());

        assertEquals(0,teammateRepository.findAll().size());


    }


    @Test
    public void moveToNextPhase() {
        //given

        //when

        //then
    }

}