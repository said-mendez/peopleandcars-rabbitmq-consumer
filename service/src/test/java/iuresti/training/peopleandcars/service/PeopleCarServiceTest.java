package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.modeldb.PeopleCarDB;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import iuresti.training.peopleandcars.repository.CarDao;
import iuresti.training.peopleandcars.repository.PeopleCarDao;
import iuresti.training.peopleandcars.repository.PeopleDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class PeopleCarServiceTest {
    @MockBean
    private PeopleCarDao peopleCarDao;
    @MockBean
    private CarDao carDao;
    @MockBean
    private PeopleDao peopleDao;
    @MockBean
    private CarService carService;
    @MockBean
    private PeopleService peopleService;
    @Autowired
    private PeopleCarService peopleCarService;

    @Test
    void canGetAllPeopleCar() {
        // Given:
        UUID uuid = UUID.randomUUID();
        PeopleCarDB peopleCarDB = new PeopleCarDB();
        peopleCarDB.setUuid(uuid.toString());
        peopleCarDB.setPeopleId("123-abcd-456");
        peopleCarDB.setCarId("UYL-137-A");

        UUID uuid1 = UUID.randomUUID();
        PeopleCarDB peopleCarDB1 = new PeopleCarDB();
        peopleCarDB1.setUuid(uuid1.toString());
        peopleCarDB1.setPeopleId("456-qwer-123");
        peopleCarDB1.setCarId("UYL-137-B");

        PeopleCar peopleCar = new PeopleCar();
        peopleCar.setUuid(uuid.toString());
        peopleCar.setPeopleId("123-abcd-456");
        peopleCar.setCarId("UYL-137-A");

        PeopleCar peopleCar1 = new PeopleCar();
        peopleCar1.setUuid(uuid1.toString());
        peopleCar1.setPeopleId("456-qwer-123");
        peopleCar1.setCarId("UYL-137-B");


        List<PeopleCarDB> peopleCarDBList = new ArrayList<>();
        peopleCarDBList.add(peopleCarDB);
        peopleCarDBList.add(peopleCarDB1);

        List<PeopleCar> peopleCarListExpected = new ArrayList<>();
        peopleCarListExpected.add(peopleCar);
        peopleCarListExpected.add(peopleCar1);

        // When:
        when(peopleCarDao.findAll()).thenReturn(peopleCarDBList);
        List<PeopleCar> peopleCarServiceCurrent = peopleCarService.fetchAllPeopleCars();

        // Then:
        assertThat(peopleCarServiceCurrent.size()).isEqualTo(peopleCarListExpected.size());
        assertThat(peopleCarServiceCurrent.get(0).getPeopleId()).isEqualTo(peopleCarListExpected.get(0).getPeopleId());
        assertThat(peopleCarServiceCurrent.get(0).getUuid()).isEqualTo(peopleCarListExpected.get(0).getUuid());
        assertThat(peopleCarServiceCurrent.get(1).getPeopleId()).isEqualTo(peopleCarListExpected.get(1).getPeopleId());
    }

    @Test
    void getAllPeopleCarWithoutRecordsWillThrowMyCarNotFoundException() {
        assertThatThrownBy(() -> peopleCarService.fetchAllPeopleCars())
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("There are not any PeopleCar records!");
    }

    @Test
    void shouldGetAllPersonCars() {
        // Given:
        CarDB carDB = new CarDB();
        carDB.setVin("UYL-137-A");
        carDB.setColor("Black");
        carDB.setBrand("Subaru");
        carDB.setModel("Impreza");
        carDB.setYear(1990);

        CarDB carDB1 = new CarDB();
        carDB1.setVin("UYL-137-B");
        carDB1.setColor("Red");
        carDB1.setBrand("Ferrari");
        carDB1.setModel("La Ferrari");
        carDB1.setYear(1997);

        Car car = new Car();
        car.setVin("UYL-137-A");
        car.setColor("Black");
        car.setBrand("Subaru");
        car.setModel("Impreza");
        car.setYear(1990);

        Car car1 = new Car();
        car1.setVin("UYL-137-B");
        car1.setColor("Red");
        car1.setBrand("Ferrari");
        car1.setModel("La Ferrari");
        car1.setYear(1997);

        List<Car> expectedCarsAssignedToPerson = new ArrayList<>();
        expectedCarsAssignedToPerson.add(car);
        expectedCarsAssignedToPerson.add(car1);

        List<CarDB> carDBAssignedToPerson = new ArrayList<>();
        carDBAssignedToPerson.add(carDB);
        carDBAssignedToPerson.add(carDB1);

        when(carDao.findCarsByPeopleGuid("1234-qwerty-456")).thenReturn(carDBAssignedToPerson);
        when(carService.findCarsByPeopleGuid("1234-qwerty-456")).thenReturn(expectedCarsAssignedToPerson);
        List<Car> peopleCarServiceCurrent = peopleCarService.fetchAllPersonCars("1234-qwerty-456");
        assertThat(peopleCarServiceCurrent.size()).isEqualTo(expectedCarsAssignedToPerson.size());
    }

    @Test
    void willThrowMyCarNotFoundExceptionWhenPersonDoesNotHaveCarsAssigned() {
        // Given:
        String fakeGUID = "fakeGUID";

        // Then:
        when(carService.findCarsByPeopleGuid(fakeGUID)).thenThrow(MyCarResourceNotFoundException.class);

        // When:
        assertThatThrownBy(() -> peopleCarService.fetchAllPersonCars(fakeGUID))
                .isInstanceOf(MyCarResourceNotFoundException.class);
    }

    @Test
    void canAddPeopleCar() {
        // Given:
        String carId = "UYL-137-A";
        String peopleId = "123-abc-123";
        PeopleCar expectedPeopleCar = new PeopleCar();
        expectedPeopleCar.setPeopleId(peopleId);
        expectedPeopleCar.setCarId(carId);

        PeopleCarDB peopleCarDB = new PeopleCarDB();
        peopleCarDB.setPeopleId(peopleId);
        peopleCarDB.setCarId(carId);

        // When:
        when(peopleCarDao.save(any(PeopleCarDB.class))).thenReturn(peopleCarDB);
        when(peopleCarDao.countByCarIdAndPeopleId(carId, peopleId)).thenReturn(0);
        when(peopleCarDao.countByCarId(carId)).thenReturn(0);

        PeopleCar serviceCurrentPeopleCar = peopleCarService.addPeopleCar(expectedPeopleCar);
        serviceCurrentPeopleCar.setUuid(expectedPeopleCar.getUuid());

        // Then:
        assertThat(serviceCurrentPeopleCar).isEqualTo(expectedPeopleCar);
    }

    @Test
    void canNotAddPeopleCarIfAlreadyExist() {
        // Given:
        String carId = "UYL-137-A";
        String peopleId = "123-abc-123";
        PeopleCar expectedPeopleCar = new PeopleCar();
        expectedPeopleCar.setPeopleId(peopleId);
        expectedPeopleCar.setCarId(carId);

        PeopleCarDB peopleCarDB = new PeopleCarDB();
        peopleCarDB.setPeopleId(peopleId);
        peopleCarDB.setCarId(carId);

        // When:
        when(peopleCarDao.save(any(PeopleCarDB.class))).thenReturn(peopleCarDB);
        when(peopleCarDao.countByCarIdAndPeopleId(carId, peopleId)).thenReturn(1);

        // Then:
        assertThatThrownBy(() -> peopleCarService.addPeopleCar(expectedPeopleCar))
                .isInstanceOf(MyCarBadRequestException.class)
                .hasMessageContaining("People car relation already exist!")
        ;
    }

    @Test
    void canNotAddPeopleCarIfCarIsAlreadyAssignedToTwoPersons() {
        // Given:
        String carId = "UYL-137-A";
        String peopleId = "123-abc-123";
        PeopleCar expectedPeopleCar = new PeopleCar();
        expectedPeopleCar.setPeopleId(peopleId);
        expectedPeopleCar.setCarId(carId);

        PeopleCarDB peopleCarDB = new PeopleCarDB();
        peopleCarDB.setPeopleId(peopleId);
        peopleCarDB.setCarId(carId);

        // When:
        when(peopleCarDao.save(any(PeopleCarDB.class))).thenReturn(peopleCarDB);
        when(peopleCarDao.countByCarIdAndPeopleId(carId, peopleId)).thenReturn(0);
        when(peopleCarDao.countByCarId(carId)).thenReturn(2);

        // Then:
        assertThatThrownBy(() -> peopleCarService.addPeopleCar(expectedPeopleCar))
                .isInstanceOf(MyCarBadRequestException.class)
                .hasMessageContaining("Car is already assigned to two persons!")
        ;
    }

    @Test
    void tryingToAddPeopleCarWithoutAttributesThrowMyCarBadRequestException() {
        // Given:
        PeopleCar expectedPeopleCar = new PeopleCar();

        // When:
        // Then:
        assertThatThrownBy(() -> peopleCarService.addPeopleCar(expectedPeopleCar))
                .isInstanceOf(MyCarBadRequestException.class)
                .hasMessageStartingWith("Something went wrong! ")
        ;
    }

    @Test
    void shouldGetAllCarPeople() {
        // Given:
        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid("123-qwerty-123");
        peopleDB.setFirstName("Juan");
        peopleDB.setLastName("Juarez");
        peopleDB.setEmail("juan@mail.com");
        peopleDB.setGender("male");

        PeopleDB peopleDB1 = new PeopleDB();
        peopleDB1.setGuid("345-qwerty-345");
        peopleDB1.setFirstName("Jane");
        peopleDB1.setLastName("Doe");
        peopleDB1.setEmail("jane@mail.com");
        peopleDB1.setGender("female");

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

        List<People> expectedPeopleAssignedToCar = new ArrayList<>();
        expectedPeopleAssignedToCar.add(people);
        expectedPeopleAssignedToCar.add(people1);

        List<PeopleDB> peopleDBAssignedToCar = new ArrayList<>();
        peopleDBAssignedToCar.add(peopleDB);
        peopleDBAssignedToCar.add(peopleDB1);

        when(peopleDao.findPeopleByCarsVin("UYL-137-A")).thenReturn(peopleDBAssignedToCar);
        when(peopleService.fetchAllCarPeople("UYL-137-A")).thenReturn(expectedPeopleAssignedToCar);
        List<People> peopleCarServiceCurrent = peopleCarService.fetchAllCarPeople("UYL-137-A");
        assertThat(peopleCarServiceCurrent.size()).isEqualTo(expectedPeopleAssignedToCar.size());
    }

    @Test
    void willThrowMyCarNotFoundExceptionWhenCarDoesNotHavePeopleAssigned() {
        // Given:
        String fakeVIN = "fakeVIN";

        // Then:
        given(peopleService.fetchAllCarPeople(fakeVIN)).willThrow(new MyCarResourceNotFoundException("There are not any people assigned to car VIN: " + fakeVIN));

        // When:
        assertThatThrownBy(() -> peopleCarService.fetchAllCarPeople(fakeVIN))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("There are not any people assigned to car VIN: " + fakeVIN)
        ;
    }
}
