package iuresti.training.peopleandcars.controller;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleAndCarsError;
import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.repository.CarDao;
import iuresti.training.peopleandcars.service.CarService;
import iuresti.training.peopleandcars.service.PeopleCarService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerTest {
    @MockBean
    private CarService carService;
    @MockBean
    private PeopleCarService peopleCarService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldAddCar() {
        // Given:
        Car expectedCar = new Car();
        expectedCar.setColor("Black");
        expectedCar.setBrand("Subaru");
        expectedCar.setModel("Impreza");
        expectedCar.setYear(1990);

        // When:
        when(carService.addCar(expectedCar)).thenReturn(expectedCar);
        ResponseEntity<Car> response = restTemplate.postForEntity("/api/cars", expectedCar, Car.class);
        Car carActual = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(carActual).isEqualTo(expectedCar);
    }

    @Test
    void givenWrongCarShouldThrowMyCarBadRequestException() {
        // Given:
        Car expectedCar = new Car();

        // When:
        when(carService.addCar(expectedCar)).thenThrow(MyCarBadRequestException.class);
        ResponseEntity<PeopleAndCarsError> response = restTemplate.postForEntity("/api/cars", expectedCar, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void givenWrongCarShouldThrowMyCarBadRequestException1() {
        // Given:
        Car car = new Car();
        car.setVin("UYL-137-A");
        car.setColor("Black");
        car.setBrand("Subaru");
        car.setModel("Impreza");
        car.setYear(1990);

        // When:
        when(carService.addCar(car)).thenThrow(new MyCarBadRequestException("Car incomplete attributes!"));
        ResponseEntity<PeopleAndCarsError> response = restTemplate.postForEntity("/api/cars", car, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Car incomplete attributes!");
    }

    @Test
    void givenWrongCarShouldThrowBadRequest() {
        // Given:
        Car expectedCar = new Car();
        expectedCar.setVin("123-456-789");
        expectedCar.setColor("Black");
        expectedCar.setBrand("Subaru");
        expectedCar.setModel("Impreza");
        expectedCar.setYear(1990);

        // When:
        doThrow(new MyCarBadRequestException("Something went wrong!")).when(carService).addCar(expectedCar);
        ResponseEntity<MyCarBadRequestException> response = restTemplate.postForEntity("/api/cars", expectedCar, MyCarBadRequestException.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isInstanceOf(MyCarBadRequestException.class);
    }

    @Test
    void shouldAddCarWithVIN() {
        // Given:
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        Car expectedCar = new Car();
        expectedCar.setColor("Black");
        expectedCar.setBrand("Subaru");
        expectedCar.setModel("Impreza");
        expectedCar.setYear(1990);

        // When:
        when(carService.addCarWithVIN(uuidString, expectedCar)).thenReturn(expectedCar);
        ResponseEntity<Car> response = restTemplate.postForEntity("/api/cars/" + uuidString, expectedCar, Car.class);
        Car carActual = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(carActual).isEqualTo(expectedCar);
    }

    @Test
    void shouldDeleteCar() {
        // Given:
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String, Boolean> expectedResponse = new HashMap<>();
        expectedResponse.put("success", true);

        // When:
        ResponseEntity<Map> response = restTemplate.exchange("/api/cars/" + uuidString, HttpMethod.DELETE, entity, Map.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    void tryingToDeleteCarWithFakeIdReturnsNotFound() {
        // Given:
        String vin = "fakeId";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // When:
        doThrow(new MyCarResourceNotFoundException("Car not found!")).when(carService).deleteCar(vin);
        ResponseEntity<PeopleAndCarsError> response = restTemplate.exchange("/api/cars/" + vin, HttpMethod.DELETE, entity, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void shouldGetAllCarPeople() {
        // Given:
        String vin = "UYL-137-A";
        People people = new People();
        people.setGuid("123-qwerty-123");
        people.setFirstName("Juan");
        people.setLastName("Juarez");
        people.setEmail("juan@mail.com");
        people.setGender("male");

        People people1 = new People();
        people1.setGuid("345-qwerty-345");
        people1.setFirstName("Jane");
        people1.setLastName("Doe");
        people1.setEmail("jane@mail.com");
        people1.setGender("female");

        List<People> peopleList = new ArrayList<>();
        peopleList.add(people);
        peopleList.add(people1);

        String expectedResponse = "[{guid=123-qwerty-123, firstName=Juan, lastName=Juarez, email=juan@mail.com, gender=male}, {guid=345-qwerty-345, firstName=Jane, lastName=Doe, email=jane@mail.com, gender=female}]";

        // When:
        when(peopleCarService.fetchAllCarPeople(vin)).thenReturn(peopleList);
        ResponseEntity<List> response = restTemplate.getForEntity("/api/cars/" + vin + "/people", List.class);
        List<Car> peopleListActual = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert peopleListActual != null;
        assertThat(peopleListActual.toString()).isEqualTo(expectedResponse);
    }

    @Test
    void getAllCarPeopleWithFakeIdReturnNotFound() {
        // Given:
        String vin = "fakeId";

        // When:
        when(peopleCarService.fetchAllCarPeople(vin)).thenThrow(MyCarResourceNotFoundException.class);
        ResponseEntity<PeopleAndCarsError> response = restTemplate.getForEntity("/api/cars/" + vin + "/people", PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void shouldGetAllCars() {
        // Given:
        Car car = new Car();
        car.setVin("UYL-137-A");
        car.setColor("Black");
        car.setBrand("Subaru");
        car.setModel("Impreza");
        car.setYear(1999);

        Car car1 = new Car();
        car1.setVin("UYL-137-B");
        car1.setColor("Red");
        car1.setBrand("Ferrari");
        car1.setModel("La Ferrari");
        car1.setYear(1997);

        Car car2 = new Car();
        car2.setVin("UYL-137-A");
        car2.setColor("White");
        car2.setBrand("Toyota");
        car2.setModel("Prius");
        car2.setYear(2023);

        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car1);
        carList.add(car2);

        String expectedResponse = "[{vin=UYL-137-A, brand=Subaru, model=Impreza, year=1999, color=Black}, {vin=UYL-137-B, brand=Ferrari, model=La Ferrari, year=1997, color=Red}, {vin=UYL-137-A, brand=Toyota, model=Prius, year=2023, color=White}]";

        // When:
        when(carService.fetchAllCars()).thenReturn(carList);
        ResponseEntity<List> response = restTemplate.getForEntity("/api/cars", List.class);
        List<Car> carListActual = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert carListActual != null;
        assertThat(carListActual.toString()).isEqualTo(expectedResponse);
    }

    @Test
    void getAllCarsWithoutRecordsWillReturnNotFound() {
        // Given:
        // When:
        when(carService.fetchAllCars()).thenThrow(MyCarResourceNotFoundException.class);

        ResponseEntity<PeopleAndCarsError> response = restTemplate.getForEntity("/api/cars", PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void shouldGetCarByVIN() {
        // Given:
        UUID uuid = UUID.randomUUID();
        String vin = uuid.toString();

        Car car = new Car();
        car.setVin(vin);
        car.setColor("Black");
        car.setBrand("Subaru");
        car.setModel("Impreza");
        car.setYear(1999);

        // When:
        when(carService.fetchCarById(vin)).thenReturn(car);
        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/" + vin, Car.class);
        Car actualCar = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualCar).isEqualTo(car);
    }

    @Test
    void getCarByVINWithFakeIdReturnNotFound() {
        // Given:
        String vin = "fakeID";

        // When:
        when(carService.fetchCarById(vin)).thenThrow(MyCarResourceNotFoundException.class);

        ResponseEntity<PeopleAndCarsError> response = restTemplate.getForEntity("/api/cars/" + vin, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }
    
    @Test
    void shouldUpdateCar() {
        // Given:
        UUID uuid = UUID.randomUUID();
        String vin = uuid.toString();

        Car car = new Car();
        car.setVin(vin);
        car.setColor("Black");
        car.setBrand("Subaru");
        car.setModel("Impreza");
        car.setYear(1999);

        HttpEntity<Car> request = new HttpEntity<>(car);
        Map<String, Boolean> expectedResponse = new HashMap<>();
        expectedResponse.put("success", true);

        // When:
        when(carService.fetchCarById(vin)).thenReturn(car);
        ResponseEntity<Map> response = restTemplate.exchange("/api/cars/" + vin, HttpMethod.PUT, request, Map.class);
        Map actualResponse = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
    
    @Test
    void tryingToUpdateCarWithFakeIdReturnNotFound() {
        // Given:
        String vin = "fakeID";

        Car car = new Car();
        car.setVin(vin);
        car.setColor("Black");
        car.setBrand("Subaru");
        car.setModel("Impreza");
        car.setYear(1999);

        HttpEntity<Car> request = new HttpEntity<>(car);

        // When:
        doThrow(new MyCarResourceNotFoundException("Car not found!")).when(carService).updateCar(vin, car);
        ResponseEntity<PeopleAndCarsError> response = restTemplate.exchange("/api/cars/" + vin, HttpMethod.PUT, request, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }
}