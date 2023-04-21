package iuresti.training.peopleandcars.controller;

import iuresti.training.peopleandcars.annotation.IgnoreCoverage;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleCar;


import iuresti.training.peopleandcars.service.PeopleCarService;
import iuresti.training.peopleandcars.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-05T18:10:17.554620-06:00[America/Mexico_City]")
@Controller
@RequestMapping("${openapi.myCar.base-path:/api}")
public class PeopleApiController implements PeopleApi {

    private final NativeWebRequest request;
    private final PeopleService peopleService;
    private final PeopleCarService peopleCarService;

    @Autowired
    public PeopleApiController(NativeWebRequest request, PeopleService peopleService, PeopleCarService peopleCarService) {
        this.request = request;
        this.peopleService = peopleService;
        this.peopleCarService = peopleCarService;
    }

    @IgnoreCoverage
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<People> addPeople(People body) {
        return new ResponseEntity<>(peopleService.addPeople(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PeopleCar> addPeopleCar(PeopleCar body) {
        return new ResponseEntity<>(peopleCarService.addPeopleCar(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<People> addPeopleWithGUID(String guid, People people) {
        return new ResponseEntity<>(peopleService.addPeopleWithGUID(guid, people), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deletePeople(String guid) {
        peopleService.deletePeople(guid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<People>> fetchAllPeople() {
        return new ResponseEntity(peopleService.fetchAllPeople(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PeopleCar>> fetchAllPeopleCars() {
        return new ResponseEntity<>(peopleCarService.fetchAllPeopleCars(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Car>> fetchAllPersonCars(String guid) {
        return new ResponseEntity<>(peopleCarService.fetchAllPersonCars(guid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<People> fetchPeopleById(String guid) {
        return new ResponseEntity<>(peopleService.fetchPeopleById(guid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> updatePeople(String guid, People body) {
        peopleService.updatePeople(guid, body);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
