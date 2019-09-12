package pl.coderslab.simulationgamedev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.simulationgamedev.entity.Football;

public interface FootballRepository extends JpaRepository<Football, Long> {
}
