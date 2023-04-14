package iuresti.training.peopleandcars.repository;


import iuresti.training.peopleandcars.modeldb.PeopleCarDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PeopleCarDao extends CrudRepository<PeopleCarDB, String> {
    List<PeopleCarDB> findAll();
    Integer countByCarId(String carId);
    Integer countByCarIdAndPeopleId(String carId, String peopleId);
}
