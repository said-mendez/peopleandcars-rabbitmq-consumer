package iuresti.training.peopleandcars.controller;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleAndCarsError;
import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.service.PeopleCarService;
import iuresti.training.peopleandcars.service.PeopleService;
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

// @SpringBootTest Tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance)
// and use that to start a Spring application context.
// Note the use of webEnvironment=RANDOM_PORT to start the server with a random port (useful to avoid conflicts in test environments)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebMvcTest(PeopleApiController.class)
public class PeopleApiControllerTest {
//    @Autowired
//     Fake http requests for us
//    private MockMvc mockMvc;
//    @Autowired
//     WebClient provides a common interface for making web requests in a non-blocking way.
//    private WebTestClient webClient;
//     We need to provide a bean for anything the controller depends on

    @MockBean
    private PeopleService peopleService;
    @MockBean
    private PeopleCarService peopleCarService;
    @Autowired
    // TestRestTemplate is HTTP Client that you use for Spring Boot integration tests.
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnCreatedPeople() {
        // Given:
        People peopleExpected = new People();
        peopleExpected.setFirstName("Juan");
        peopleExpected.setLastName("Juarez");
        peopleExpected.setEmail("juan@mail.com");
        peopleExpected.setGender("male");

        // When:
        when(peopleService.addPeople(peopleExpected)).thenReturn(peopleExpected);
        ResponseEntity<People> response = restTemplate.postForEntity("/api/people", peopleExpected, People.class);
        People peopleActual = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(peopleActual).isEqualTo(peopleExpected);
    }

    @Test
    void givenWrongPeopleShouldThrowMyCarBadRequestException() {
        // Given:
        People expectedPeople = new People();

        // When:
        when(peopleService.addPeople(expectedPeople)).thenThrow(MyCarBadRequestException.class);
        ResponseEntity<PeopleAndCarsError> response = restTemplate.postForEntity("/api/people", expectedPeople, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void shouldReturnCreatedPeopleCar() {
        // Given:
        PeopleCar peopleCarExpected = new PeopleCar();
        peopleCarExpected.setPeopleId("123-abc-123");
        peopleCarExpected.setCarId("UYL-137-A");

        // When:
        when(peopleCarService.addPeopleCar(peopleCarExpected)).thenReturn(peopleCarExpected);
        ResponseEntity<PeopleCar> response = restTemplate.postForEntity("/api/people/cars", peopleCarExpected, PeopleCar.class);
        PeopleCar peopleCarActual = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(peopleCarActual).isEqualTo(peopleCarExpected);
    }

    @Test
    void shouldAddPeopleWithGUID() {
        // Given:
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        People peopleExpected = new People();
        peopleExpected.setFirstName("Juan");
        peopleExpected.setLastName("Juarez");
        peopleExpected.setEmail("juan@mail.com");
        peopleExpected.setGender("male");

        // When:
        when(peopleService.addPeopleWithGUID(uuidString, peopleExpected)).thenReturn(peopleExpected);
        ResponseEntity<People> response = restTemplate.postForEntity("/api/people/" + uuidString, peopleExpected, People.class);
        People peopleActual = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(peopleActual).isEqualTo(peopleExpected);
    }

    @Test
    void shouldDeletePerson() {
        // Given:
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        // A data structure representing HTTP request or response headers, mapping String header names to a list of
        // String values, also offering accessors for common application-level data types.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Represents an HTTP request or response entity, consisting of headers and body.
        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String, Boolean> expectedResponse = new HashMap<>();
        expectedResponse.put("success", true);

        // When:
        ResponseEntity<Map> response = restTemplate.exchange("/api/people/" + uuidString, HttpMethod.DELETE, entity, Map.class);
        //restTemplate.delete("/api/people/" + uuidString);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    void tryingToDeletePersonWithFakeIdReturnsNotFound() {
        // Given:
        String guid = "fakeId";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // When:
        doThrow(new MyCarResourceNotFoundException("People not found!")).when(peopleService).deletePeople(guid);
        ResponseEntity<PeopleAndCarsError> response = restTemplate.exchange("/api/people/" + guid, HttpMethod.DELETE, entity, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void shouldReturnAListOfPeople() {
        // Given:
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

        // When
        when(peopleService.fetchAllPeople()).thenReturn(peopleList);

        ResponseEntity<List> response = restTemplate.getForEntity("/api/people", List.class);
        List<People> listPeopleActual = response.getBody();


        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert listPeopleActual != null;
        assertThat(listPeopleActual.toString()).isEqualTo(expectedResponse);
    }

    @Test
    void getAllPeopleWithoutRecordsWillReturnNotFound() {
        // Given:
        // When:
        when(peopleService.fetchAllPeople()).thenThrow(MyCarResourceNotFoundException.class);

        ResponseEntity<PeopleAndCarsError> response = restTemplate.getForEntity("/api/people", PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void shouldGetAllPeopleCarsAssignments() {
        // Given:
        String uuid = "PeopleCar001";
        String uuid1 = "PeopleCar002";
        String uuid2 = "PeopleCar003";

        PeopleCar peopleCar = new PeopleCar();
        peopleCar.setUuid(uuid);
        peopleCar.setPeopleId("123-abcd-456");
        peopleCar.setCarId("UYL-137-A");

        PeopleCar peopleCar1 = new PeopleCar();
        peopleCar1.setUuid(uuid1);
        peopleCar1.setPeopleId("456-qwer-123");
        peopleCar1.setCarId("UYL-137-B");

        PeopleCar peopleCar2 = new PeopleCar();
        peopleCar2.setUuid(uuid2);
        peopleCar2.setPeopleId("123-abcd-456");
        peopleCar2.setCarId("UYL-137-D");

        List<PeopleCar> peopleCarList = new ArrayList<>();
        peopleCarList.add(peopleCar);
        peopleCarList.add(peopleCar1);
        peopleCarList.add(peopleCar2);

        String expectedResponse = "[{uuid=PeopleCar001, peopleId=123-abcd-456, carId=UYL-137-A}, {uuid=PeopleCar002, peopleId=456-qwer-123, carId=UYL-137-B}, {uuid=PeopleCar003, peopleId=123-abcd-456, carId=UYL-137-D}]";

        // When:
        when(peopleCarService.fetchAllPeopleCars()).thenReturn(peopleCarList);
        ResponseEntity<List> response = restTemplate.getForEntity("/api/people/cars", List.class);
        List<PeopleCar> peopleCarListActual = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert peopleCarListActual != null;
        assertThat(peopleCarListActual.toString()).isEqualTo(expectedResponse);
    }

    @Test
    void shouldGetAllPersonCars() {
        // Given:
        String guid = "123-abc-123";
        Car car = new Car();
        car.setVin("UYL-137-A");
        car.setColor("Black");
        car.setBrand("Subaru");
        car.setModel("Impreza");
        car.setYear(1990);

        Car car1 = new Car();
        car1.setVin("UYL-137-B");
        car1.setColor("Pink");
        car1.setBrand("Toyota");
        car1.setModel("Corolla");
        car1.setYear(2017);

        Car car2 = new Car();
        car2.setVin("UYL-137-C");
        car2.setColor("Red");
        car2.setBrand("Ferrari");
        car2.setModel("La Ferrari");
        car2.setYear(2001);

        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car1);
        carList.add(car2);

        String expectedResponse = "[{vin=UYL-137-A, brand=Subaru, model=Impreza, year=1990, color=Black}, {vin=UYL-137-B, brand=Toyota, model=Corolla, year=2017, color=Pink}, {vin=UYL-137-C, brand=Ferrari, model=La Ferrari, year=2001, color=Red}]";

        // When
        when(peopleCarService.fetchAllPersonCars(guid)).thenReturn(carList);

        ResponseEntity<List> response = restTemplate.getForEntity("/api/people/" + guid + "/cars", List.class);
        List<Car> carListActual = response.getBody();


        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert carListActual != null;
        assertThat(carListActual.toString()).isEqualTo(expectedResponse);
    }

    @Test
    void getAllPersonCarsWithFakeIdReturnNotFound() {
        // Given:
        String guid = "fakeId";

        // When:
        when(peopleCarService.fetchAllPersonCars(guid)).thenThrow(MyCarResourceNotFoundException.class);
        ResponseEntity<PeopleAndCarsError> response = restTemplate.getForEntity("/api/people/" + guid + "/cars", PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void shouldGetPeopleByGuid() {
        // Given:
        String guid = "123-qwerty-123";
        People expectedPeople = new People();
        expectedPeople.setGuid(guid);
        expectedPeople.setFirstName("Juan");
        expectedPeople.setLastName("Juarez");
        expectedPeople.setEmail("juan@mail.com");
        expectedPeople.setGender("male");

        // When:
        when(peopleService.fetchPeopleById(guid)).thenReturn(expectedPeople);

        ResponseEntity<People> response = restTemplate.getForEntity("/api/people/" + guid, People.class);
        People actualPeople = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualPeople).isEqualTo(expectedPeople);
    }

    @Test
    void getPeopleByGUIDWithFakeIdReturnNotFound() {
        // Given:
        String guid = "fakeID";

        // When:
        when(peopleService.fetchPeopleById(guid)).thenThrow(MyCarResourceNotFoundException.class);

        ResponseEntity<PeopleAndCarsError> response = restTemplate.getForEntity("/api/people/" + guid, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }

    @Test
    void shouldUpdatePeople() {
        // Given:
        String guid = "123-qwerty-123";
        People people = new People();
        people.setGuid(guid);
        people.setFirstName("Juan");
        people.setLastName("Juarez");
        people.setEmail("juan@mail.com");
        people.setGender("male");

        // Create the request body by wrapping the object in HttpEntity
        HttpEntity<People> request = new HttpEntity<>(people);

        Map<String, Boolean> expectedResponse = new HashMap<>();
        expectedResponse.put("success", true);

        // When:
        when(peopleService.fetchPeopleById(guid)).thenReturn(people);

        ResponseEntity<Map> response = restTemplate.exchange("/api/people/" + guid, HttpMethod.PUT, request, Map.class);
        Map actualResponse = response.getBody();

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void tryingToUpdatePersonWithFakeIdReturnNotFound() {
        // Given:
        String guid = "fakeID";
        People people = new People();
        people.setGuid(guid);
        people.setFirstName("Juan");
        people.setLastName("Juarez");
        people.setEmail("juan@mail.com");
        people.setGender("male");


        HttpEntity<People> request = new HttpEntity<>(people);

        // When:
        doThrow(new MyCarResourceNotFoundException("People not found!")).when(peopleService).updatePeople(guid, people);
        ResponseEntity<PeopleAndCarsError> response = restTemplate.exchange("/api/people/" + guid, HttpMethod.PUT, request, PeopleAndCarsError.class);

        // Then:
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isInstanceOf(PeopleAndCarsError.class);
    }
}
