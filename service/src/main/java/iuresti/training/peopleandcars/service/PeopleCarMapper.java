package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.modeldb.PeopleCarDB;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PeopleCarMapper implements Function<PeopleCarDB, PeopleCar> {
    @Override
    public PeopleCar apply(PeopleCarDB peopleCarDB) {
        PeopleCar peopleCar = new PeopleCar();
        peopleCar.setUuid(peopleCarDB.getUuid());
        peopleCar.setCarId(peopleCarDB.getCarId());
        peopleCar.setPeopleId(peopleCarDB.getPeopleId());

        return peopleCar;
    }
}
