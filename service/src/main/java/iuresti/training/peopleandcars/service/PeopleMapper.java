package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PeopleMapper implements Function<PeopleDB, People> {
    @Override
    public People apply(PeopleDB peopleDB) {
        People peopleAPI = new People();
        peopleAPI.setGuid(peopleDB.getGuid());
        peopleAPI.setFirstName(peopleDB.getFirstName());
        peopleAPI.setLastName(peopleDB.getLastName());
        peopleAPI.setEmail(peopleDB.getEmail());
        peopleAPI.setGender(peopleDB.getGender());

        return peopleAPI;
    }
}
