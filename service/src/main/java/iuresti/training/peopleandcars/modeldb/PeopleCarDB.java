package iuresti.training.peopleandcars.modeldb;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "people_car")
public class PeopleCarDB {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    @Column(name = "peopleid")
    private String peopleId;
    @Column(name = "carid")
    private String carId;
}
