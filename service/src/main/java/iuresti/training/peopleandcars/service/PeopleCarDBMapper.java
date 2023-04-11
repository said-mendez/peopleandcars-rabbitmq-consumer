package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.modeldb.PeopleCarDB;

import java.util.function.Function;

public class PeopleCarDBMapper implements Function<PeopleCar, PeopleCarDB> {
    @Override
    public PeopleCarDB apply(PeopleCar peopleCar) {
        PeopleCarDB peopleCarDB = new PeopleCarDB();
        peopleCarDB.setPeopleId(peopleCar.getPeopleId());
        peopleCarDB.setCarId(peopleCar.getUuid());

        return peopleCarDB;
    }
}
