package iuresti.training.peopleandcars.modeldb;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "car")
public class CarDB {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String vin;
    private String brand;
    private String model;
    private int year;
    private String color;
}
