package iuresti.training.peopleandcars.repository;


import iuresti.training.peopleandcars.modeldb.PeopleDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleDao extends CrudRepository<PeopleDB, String> {
    List<PeopleDB> findAll();
//    String create(People people) throws MyCarBadRequestException;
//    void update(String id, People people) throws MyCarBadRequestException;
//    void delete(String id) throws MyCarBadRequestException;
//    Optional<People> getByGUID(String guid) throws MyCarResourceNotFoundException;
//    String createWithGUID(String id, People people) throws MyCarBadRequestException;
}
