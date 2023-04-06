package iuresti.training.peopleandcars.modeldb;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "people")
public class PeopleDB {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String guid;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    private String email;
    private String gender;
}
