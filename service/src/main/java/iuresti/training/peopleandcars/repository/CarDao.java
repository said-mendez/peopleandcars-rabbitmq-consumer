package iuresti.training.peopleandcars.repository;

import iuresti.training.peopleandcars.modeldb.CarDB;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao extends CrudRepository<CarDB, String> {
    List<CarDB> findAll();
}
