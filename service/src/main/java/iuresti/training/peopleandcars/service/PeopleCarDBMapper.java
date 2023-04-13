package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.modeldb.PeopleCarDB;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PeopleCarDBMapper implements Function<PeopleCar, PeopleCarDB> {
    @Override
    public PeopleCarDB apply(PeopleCar peopleCar) {
        PeopleCarDB peopleCarDB = new PeopleCarDB();
        peopleCarDB.setUuid(peopleCar.getUuid());
        peopleCarDB.setPeopleId(peopleCar.getPeopleId());
        peopleCarDB.setCarId(peopleCar.getUuid());

        return peopleCarDB;
    }
}
