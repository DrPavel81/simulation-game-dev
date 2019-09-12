package pl.coderslab.simulationgamedev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.simulationgamedev.entity.Basketball;

public interface BasketballRepository extends JpaRepository<Basketball, Long> {
}
