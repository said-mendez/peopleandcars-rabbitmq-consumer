package iuresti.training.peopleandcars.repository;


import iuresti.training.peopleandcars.modeldb.PeopleDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleDao extends CrudRepository<PeopleDB, String> {
    List<PeopleDB> findAll();
    List<PeopleDB> findPeopleByCarsVin(String carId);
}
