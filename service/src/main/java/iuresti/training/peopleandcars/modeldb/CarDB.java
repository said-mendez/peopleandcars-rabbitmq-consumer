package iuresti.training.peopleandcars.modeldb;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "car")
public class CarDB {
    @Id
    private String vin;
    private String brand;
    private String model;
    private int year;
    private String color;

    @ManyToMany(
            mappedBy = "cars",
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            }
    )
    @JsonIgnore
    private Set<PeopleDB> people = new HashSet<>();

    public Set<PeopleDB> getPeople() {
        return people;
    }

    public void setPeople(Set<PeopleDB> people) {
        this.people = people;
    }
}
