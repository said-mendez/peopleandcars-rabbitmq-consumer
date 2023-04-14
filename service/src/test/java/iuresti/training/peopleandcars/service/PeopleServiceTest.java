package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.exception.MyCarBadRequestException;
import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import iuresti.training.peopleandcars.repository.PeopleDao;
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
    void getPeopleByFakeIdWillThrowMyCarNotFoundException() {
        // Given:
        // When:
        // Then:
        assertThatThrownBy(() -> peopleService.fetchPeopleById("fakeID"))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("People not found!");
    }

    @Test
    void canAddPeople() {
        // Given:
        People expectedPerson = new People();
        expectedPerson.setFirstName("Eduardo");
        expectedPerson.setLastName("Méndez");
        expectedPerson.setGender("male");
        expectedPerson.setEmail("lalo@mail.com");

        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setFirstName("Eduardo");
        peopleDB.setLastName("Méndez");
        peopleDB.setGender("male");
        peopleDB.setEmail("lalo@mail.com");

        // When:
        when(peopleDao.save(any(PeopleDB.class))).thenReturn(peopleDB);

        People serviceCurrentPerson = peopleService.addPeople(expectedPerson);
        serviceCurrentPerson.setGuid(expectedPerson.getGuid());

        // Then:
        assertThat(serviceCurrentPerson).isEqualTo(expectedPerson);
    }

    @Test
    void addPersonWithoutAttributesThrowMyCarBadRequestException() {
        // Given:
        People expectedPerson = new People();

        // When:
        // Then:
        assertThatThrownBy(() -> peopleService.addPeople(expectedPerson))
                .isInstanceOf(MyCarBadRequestException.class);
    }

    @Test
    void canAddPeopleWithGUID() {
        // Given:
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString();
        People expectedPerson = new People();
        expectedPerson.setFirstName("Eduardo");
        expectedPerson.setLastName("Méndez");
        expectedPerson.setGender("male");
        expectedPerson.setEmail("lalo@mail.com");

        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid(guid);
        peopleDB.setFirstName("Eduardo");
        peopleDB.setLastName("Méndez");
        peopleDB.setGender("male");
        peopleDB.setEmail("lalo@mail.com");

        // When:
        when(peopleDao.save(any(PeopleDB.class))).thenReturn(peopleDB);

        People serviceCurrentPerson = peopleService.addPeopleWithGUID(guid, expectedPerson);

        // Then:
        assertThat(serviceCurrentPerson).isEqualTo(expectedPerson);
    }

    @Test
    void addPersonWithGUIDWithoutAttributesThrowMyCarBadRequestException() {
        // Given:
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString();
        People expectedPerson = new People();

        // When:
        // Then:
        assertThatThrownBy(() -> peopleService.addPeopleWithGUID(guid, expectedPerson))
                .isInstanceOf(MyCarBadRequestException.class);
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


        // When:
        when(peopleDao.findPeopleByCarsVin("UYL-137-A")).thenReturn(peopleDBAssignedToCar);
        List<People> peopleServiceCurrent = peopleService.fetchAllCarPeople("UYL-137-A");

        // Then:
        assertThat(peopleServiceCurrent.size()).isEqualTo(expectedPeopleAssignedToCar.size());
        assertThat(peopleServiceCurrent.get(0).getGuid()).isEqualTo(expectedPeopleAssignedToCar.get(0).getGuid());
        assertThat(peopleServiceCurrent.get(1).getGuid()).isEqualTo(expectedPeopleAssignedToCar.get(1).getGuid());
    }

    @Test
    void willThrowMyCarNotFoundExceptionWhenCarDoesNotHavePeopleAssigned() {
        // Given:
        String fakeVIN = "fakeVIN";

        // When:
        // Then:
        assertThatThrownBy(() -> peopleService.fetchAllCarPeople(fakeVIN))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("There are not any people assigned to car VIN: " + fakeVIN)
        ;
    }

    @Test
    void canUpdatePerson() {
        // Given:
        String guid = "NewPerson007";
        People expectedPerson = new People();
        expectedPerson.setGuid(guid);
        expectedPerson.setFirstName("Eduardo-Edited");
        expectedPerson.setLastName("Méndez");
        expectedPerson.setGender("male");
        expectedPerson.setEmail("lalo@mail.com");

        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid(guid);
        peopleDB.setFirstName("Eduardo");
        peopleDB.setLastName("Méndez");
        peopleDB.setGender("male");
        peopleDB.setEmail("lalo@mail.com");

        // When:
        when(peopleDao.save(any(PeopleDB.class))).thenReturn(peopleDB);
        when(peopleDao.findById(guid)).thenReturn(Optional.of(peopleDB));

        // Then:
        peopleService.updatePeople(guid, expectedPerson);
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
    void canDeletePeople() {
        // Given:
        String guid = "NewPerson007";

        PeopleDB peopleDB = new PeopleDB();
        peopleDB.setGuid(guid);
        peopleDB.setFirstName("Eduardo");
        peopleDB.setLastName("Méndez");
        peopleDB.setGender("male");
        peopleDB.setEmail("lalo@mail.com");

        // When:
        when(peopleDao.findById(guid)).thenReturn(Optional.of(peopleDB));

        // Then:
        peopleService.deletePeople(guid);
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
