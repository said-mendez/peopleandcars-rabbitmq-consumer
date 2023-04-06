package iuresti.training.peopleandcars.repository;


import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.modeldb.PeopleCarDB;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PeopleCarDao extends CrudRepository<PeopleCarDB, String> {
    List<PeopleCarDB> findAll();
    List<CarDB> findAllPersonCars(String personId);
    List<PeopleDB> findAllCarPeople(String carId);
}
