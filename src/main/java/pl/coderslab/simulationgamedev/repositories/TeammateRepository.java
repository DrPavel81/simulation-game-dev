package pl.coderslab.simulationgamedev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.simulationgamedev.entity.Teammates;

import java.util.List;

public interface TeammateRepository extends JpaRepository<Teammates,Long> {
    List<Teammates> findAllByType(String type);
}
