package pl.coderslab.simulationgamedev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.simulationgamedev.entity.Game;
import pl.coderslab.simulationgamedev.entity.Player;
import pl.coderslab.simulationgamedev.entity.Teammates;
import pl.coderslab.simulationgamedev.services.GameService;
import pl.coderslab.simulationgamedev.services.PlayerService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {


    private GameService gameService;
    private PlayerService playerService;

    public MainController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/creategame/{type}")
    public String createGame(@PathVariable(name = "type") String type) {
        Game game = this.gameService.createGame(type);
        return "redirect:/createplayers/"+type+ "/" +game.getId();
    }

    @GetMapping("/createplayers/{type}/{gameId}")
    public String createPlayers(@PathVariable String type, @PathVariable Long gameId, Model model, HttpSession session) {
        Game game = this.gameService.getDetails(type, gameId);
        if (session.getAttribute("createPlayerStep") == null) {
            model.addAttribute("createPlayerStep", 1);
        } else {
            model.addAttribute("createPlayerStep", (int) session.getAttribute("createPlayerStep") + 1);
        }
        model.addAttribute("player", new Player());
        return "playerForm";
    }

    @PostMapping("/createplayers/{type}/{gameId}")
    public String createPlayers(@PathVariable Long gameId,
                                @PathVariable String type,
                                @ModelAttribute Player player,
                                HttpSession session) {
        Game game = this.gameService.getDetails(type, gameId);
        type =game.getNameOfGame();
        int playerNumber = 0;
        try {
            playerService.create(player.getName(), game);
            if (session.getAttribute("createPlayerStep") == null) {
                playerNumber = 1;
            } else {
                playerNumber = (int) session.getAttribute("createPlayerStep");
                playerNumber++;
            }
            session.setAttribute("createPlayerStep", playerNumber);

            if (playerNumber >= game.getPlayerLimit()) {
                return "redirect:/teamselection/" + type + "/" + gameId;
            } else {
                return "redirect:/createplayers/" + type + "/" + gameId;
            }

        } catch (Exception e) {
            return "redirect:/teamselection/" + type + "/" + gameId;
        }
    }

    @GetMapping("/teamselection/{type}/{gameId}")
    public String teamSelection(@PathVariable Long gameId,
                                @PathVariable String type,
                                HttpSession session,
                                Model model) {

        Game game = this.gameService.getDetails(type, gameId);
        type =game.getNameOfGame();
        //todo if payer teams full redirect to game stages

        int playerNumber =
                session.getAttribute("playerNumber") != null ? (int) session.getAttribute("playerNumber") : 1;
        session.setAttribute("playerNumber", playerNumber);

        Player player = null;
        if (playerNumber == 2 && player.getTeammates().size() == 5) {
            return "redirect:/gamePhases/" + type + "/" + gameId;
        }

        try {
            player = gameService.getPlayer(game, playerNumber);
        } catch (Exception e) {
            model.addAttribute("error", "too many teammates");
        }

        List<Teammates> teammebersToChose = gameService.getAvailableTeammatesToChose(game);

        model.addAttribute("player", player);
        model.addAttribute("teammebersToChose", teammebersToChose);
        return "teamselectionForm";
    }

    @PostMapping("/teamselection/{type}/{gameId}")
    public String teamSelection(@PathVariable Long gameId,
                                @PathVariable String type,
                                @ModelAttribute Player player,
                                HttpSession session,
                                Model model,
                                @RequestParam Long teammemberId) {

        Game game = this.gameService.getDetails(type, gameId);
        type =game.getNameOfGame();

        int playerNumber = session.getAttribute("playerNumber") != null ? (int) session.getAttribute("playerNumber") : 1;

        try {
            player = gameService.getPlayer(game, playerNumber);
        } catch (Exception e) {
            return "error";
        }
        model.addAttribute("player", player);

        List<Teammates> teammates = gameService.getAvailableTeammatesToChose(game); //todo
        gameService.addTeamMember(game, player, teammates);

        if (playerNumber >= game.getPlayerLimit()) {
            playerNumber = 1;
        } else {
            playerNumber++;
        }
        session.setAttribute("playerNumber", playerNumber);

        return "redirect:/teamselection/" + type + "/" + gameId;
    }

    @GetMapping("/gamePhases/{type}/{gameId}")
    public String gamePhases(@PathVariable Long gameId,
                             @PathVariable String type,
                             HttpSession session,
                             Model model) {

        Game game = this.gameService.getDetails(type, gameId);
        type =game.getNameOfGame();
        if (game.getCurrentPhase() <= game.getNumberOfPhase()) {
            try {
                gameService.moveToNextPhase(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return "gameResult";
        }
        return "phaseResult";
    }
}
