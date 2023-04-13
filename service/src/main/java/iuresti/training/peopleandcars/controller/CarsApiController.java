package iuresti.training.peopleandcars.controller;

import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;

import iuresti.training.peopleandcars.service.CarService;
import iuresti.training.peopleandcars.service.PeopleCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-05T18:10:17.554620-06:00[America/Mexico_City]")
@RestController
@RequestMapping("${openapi.myCar.base-path:/api}")
public class CarsApiController implements CarsApi {

    private final NativeWebRequest request;
    private final CarService carService;
    private final PeopleCarService peopleCarService;

    @Autowired
    public CarsApiController(NativeWebRequest request, CarService carService, PeopleCarService peopleCarService) {
        this.request = request;
        this.carService = carService;
        this.peopleCarService = peopleCarService;
    }

    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Car> addCar(Car body) {
        return new ResponseEntity<>(carService.addCar(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Car> addCarWithVIN(String vin, Car car) {
        return new ResponseEntity<>(carService.addCarWithVIN(vin, car), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> deleteCar(String vin) {
        carService.deleteCar(vin);
        Map<String, Boolean> map = new HashMap<>();
        map.put("sucess", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<People>> fetchAllCarPeople(String vin) {
        return new ResponseEntity<>(peopleCarService.fetchAllCarPeople(vin), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Car>> fetchAllCars() {
        return new ResponseEntity<>(carService.fetchAllCars(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Car> fetchCarById(String vin) {
        return new ResponseEntity<>(carService.fetchCarById(vin), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> updateCar(String vin, Car body) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("sucess", true);
        carService.updateCar(vin, body);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
