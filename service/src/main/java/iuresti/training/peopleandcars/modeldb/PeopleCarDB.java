package iuresti.training.peopleandcars.modeldb;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "people_car")
public class PeopleCarDB {
    @Id
    private String uuid;
    @Column(name = "peopleid")
    private String peopleId;
    @Column(name = "carid")
    private String carId;

//    @ManyToOne
//    @JoinColumn(name = "carid")
//    CarDB car;
//
//    @ManyToOne
//    @JoinColumn(name = "peopleid")
//    PeopleDB people;
}
