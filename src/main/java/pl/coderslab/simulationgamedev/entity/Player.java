package pl.coderslab.simulationgamedev.entity;

import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Teammates> teammates = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teammates> getTeammates() {
        return teammates;
    }

    public void setTeammates(List<Teammates> teammates) {
        this.teammates = teammates;
    }
}