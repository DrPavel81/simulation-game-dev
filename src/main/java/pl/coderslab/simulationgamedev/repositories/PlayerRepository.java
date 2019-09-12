package pl.coderslab.simulationgamedev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.simulationgamedev.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
