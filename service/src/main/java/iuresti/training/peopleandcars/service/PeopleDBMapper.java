package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PeopleDBMapper implements Function<People, PeopleDB> {
    @Override
    public PeopleDB apply(People people) {
        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid(people.getGuid());
        peopleDB.setFirstName(people.getFirstName());
        peopleDB.setLastName(people.getLastName());
        peopleDB.setEmail(people.getEmail());
        peopleDB.setGender(people.getGender());

        return peopleDB;
    }
}
