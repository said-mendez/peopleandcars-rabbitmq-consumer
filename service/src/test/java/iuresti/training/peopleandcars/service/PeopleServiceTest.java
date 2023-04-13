package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import iuresti.training.peopleandcars.repository.PeopleDao;
import iuresti.training.peopleandcars.service.PeopleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class PeopleServiceTest {
    @MockBean
    private PeopleDao peopleDao;
    @Autowired
    private PeopleService peopleService;

    @Test
    void canGetAllPeople() {
        // Given:
        List<PeopleDB> peopleDBList = new ArrayList<>();
        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid("123-qwerty-123");
        peopleDB.setFirstName("Juan");
        peopleDB.setLastName("Juarez");
        peopleDB.setEmail("juan@mail.com");
        peopleDB.setGender("male");
        peopleDBList.add(peopleDB);

        List<People> peopleServiceExpected = new ArrayList<>();
        People people = new People();
        people.setGuid("123-qwerty-123");
        people.setFirstName("Juan");
        people.setLastName("Juarez");
        people.setEmail("juan@mail.com");
        people.setGender("male");
        peopleServiceExpected.add(people);


        // When:
        when(peopleDao.findAll()).thenReturn(peopleDBList);
        List<People> peopleServiceCurrent = peopleService.fetchAllPeople();

        // Then:
        assertThat(peopleServiceCurrent.size()).isEqualTo(peopleServiceExpected.size());
        assertThat(peopleServiceCurrent.get(0).getGuid()).isEqualTo(peopleServiceExpected.get(0).getGuid());
    }

    @Test
    void getAllPeopleWithoutRecordsWillThrowMyCarNotFoundException() {
        // Given:
        // When:
        // Then:
        assertThatThrownBy(() -> peopleService.fetchAllPeople())
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("There are not any people records!");
    }

    @Test
    void canGetPersonById() {
        // Given:
        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid("123-qwerty-123");
        peopleDB.setFirstName("Juan");
        peopleDB.setLastName("Juarez");
        peopleDB.setEmail("juan@mail.com");
        peopleDB.setGender("male");

        People expectedPerson = new People();
        expectedPerson.setGuid("123-qwerty-123");
        expectedPerson.setFirstName("Juan");
        expectedPerson.setLastName("Juarez");
        expectedPerson.setEmail("juan@mail.com");
        expectedPerson.setGender("male");


        // When:
        when(peopleDao.findById("123-qwerty-123")).thenReturn(Optional.of(peopleDB));
        People peopleServiceCurrent = peopleService.fetchPeopleById("123-qwerty-123");

        // Then:
        assertThat(peopleServiceCurrent).isEqualTo(expectedPerson);
        assertThat(peopleServiceCurrent.getGuid()).isEqualTo(expectedPerson.getGuid());
    }

    @Test
    void getPeopleByFakIdWillThrowMyCarNotFoundException() {
        // Given:
        // When:
        // Then:
        assertThatThrownBy(() -> peopleService.fetchPeopleById("fakeID"))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("People not found!");
    }

    @Test
    @Disabled
    void canAddPeople() {
        // Given:
        UUID uuid = UUID.randomUUID();
        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid(uuid.toString());
        peopleDB.setFirstName("Eduardo");
        peopleDB.setLastName("Méndez");
        peopleDB.setGender("male");
        peopleDB.setEmail("lalo@mail.com");

        People expectedPerson = new People();
        expectedPerson.setGuid(uuid.toString());
        expectedPerson.setFirstName("Eduardo");
        expectedPerson.setLastName("Méndez");
        expectedPerson.setGender("male");
        expectedPerson.setEmail("lalo@mail.com");

        // When:
        when(peopleDao.save(peopleDB)).thenReturn(peopleDB);
        given(peopleDao.save(peopleDB))
                .willReturn(peopleDB);
        peopleService.addPeople(expectedPerson);

        // Then:
        // Use it to capture argument values for further assertions.
//        ArgumentCaptor<PeopleDB> peopleDBArgumentCaptor = ArgumentCaptor.forClass(PeopleDB.class);
//        PeopleDB capturedPerson = verify(peopleDao).save(peopleDBArgumentCaptor.capture());
//
//        System.out.println(peopleDBArgumentCaptor.capture());
//        System.out.println(capturedPerson);

//        assertThat(capturedPerson).isEqualTo(person);
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

        CarDB carDB = new CarDB();
        carDB.setVin("UYL-137-A");
        carDB.setColor("Black");
        carDB.setBrand("Subaru");
        carDB.setModel("Impreza");
        carDB.setYear(1990);


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

        Car car = new Car();
        car.setVin("UYL-137-A");
        car.setColor("Black");
        car.setBrand("Subaru");
        car.setModel("Impreza");
        car.setYear(1990);

//        PeopleCarDB peopleCarDB = new PeopleCarDB();
//        peopleCarDB.setCarId(carDB.getVin());
//        peopleCarDB.setPeopleId(peopleDB.getGuid());
//
//        PeopleCarDB peopleCarDB1 = new PeopleCarDB();
//        peopleCarDB1.setCarId(carDB.getVin());
//        peopleCarDB1.setPeopleId(peopleDB1.getGuid());
//
//        List<PeopleCarDB> peopleCarDBList = new ArrayList<>();
//        peopleCarDBList.add(peopleCarDB);
//        peopleCarDBList.add(peopleCarDB1);
//
//        PeopleCar peopleCar = new PeopleCar();
//        peopleCar.setCarId(car.getVin());
//        peopleCar.setPeopleId(people.getGuid());
//
//        PeopleCar peopleCar1 = new PeopleCar();
//        peopleCar1.setCarId(car.getVin());
//        peopleCar1.setPeopleId(people1.getGuid());
//        List<PeopleCar> peopleCarList = new ArrayList<>();
//        peopleCarList.add(peopleCar);
//        peopleCarList.add(peopleCar1);

        List<People> expectedPeopleAssignedToCar = new ArrayList<>();
        expectedPeopleAssignedToCar.add(people);
        expectedPeopleAssignedToCar.add(people1);

        List<PeopleDB> peopleDBAssignedToCar = new ArrayList<>();
        peopleDBAssignedToCar.add(peopleDB);
        peopleDBAssignedToCar.add(peopleDB1);


        // When:
        when(peopleDao.findPeopleByCarsVin("UYL-137-A")).thenReturn(peopleDBAssignedToCar);
        List<People> peopleServiceCurrent = peopleService.fetchAllCarPeople("UYL-137-A");

        // Then:
        assertThat(peopleServiceCurrent.size()).isEqualTo(expectedPeopleAssignedToCar.size());
        assertThat(peopleServiceCurrent.get(0).getGuid()).isEqualTo(expectedPeopleAssignedToCar.get(0).getGuid());
        assertThat(peopleServiceCurrent.get(1).getGuid()).isEqualTo(expectedPeopleAssignedToCar.get(1).getGuid());
    }

    @Test
    void tryingToUpdatePersonWithFakeIdThrowMyCarNotFoundException() {
        // Given:
        String fakeId = "fakeId";

        People peopleToUpdate = new People();
        peopleToUpdate.setFirstName("Name");
        peopleToUpdate.setLastName("LastName");
        peopleToUpdate.setEmail("name@mail.com");
        peopleToUpdate.setGender("male");

        // When:
        // Then:
        assertThatThrownBy(() -> peopleService.updatePeople(fakeId, peopleToUpdate))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("People not found!");
    }

    @Test
    void tryingToDeletePersonWithFakeIdThrowMyCarNotFoundException() {
        // Given:
        String fakeId = "fakeId";

        // When:
        // Then:
        assertThatThrownBy(() -> peopleService.deletePeople(fakeId))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("People not found!");
    }
}
