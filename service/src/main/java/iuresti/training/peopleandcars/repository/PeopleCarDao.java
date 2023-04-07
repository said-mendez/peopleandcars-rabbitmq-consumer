package iuresti.training.peopleandcars.repository;


import iuresti.training.peopleandcars.modeldb.PeopleCarDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PeopleCarDao extends CrudRepository<PeopleCarDB, String> {
    List<PeopleCarDB> findAll();
//    List<CarDB> findAllPersonCars(String personId);
//    List<PeopleDB> findAllCarPeople(String carId);
//    List<CarDB> findAllByPeopleId(String peopleId);
//    List<PeopleDB> findAllByCarId(String carId);
    Integer countByCarId(String carId);
    Integer countByCarIdAndPeopleId(String carId, String peopleId);

//    List<PeopleCarDB> findAll();
//    List<CarDB> findAllByPeopleGuid(String peopleId);
//    List<PeopleDB> findAllByCarVin(String carId);
//    Integer countByCarVin(String carId);
//    //Integer countByCarIdAndPeopleId(String carId, String peopleId);
}
