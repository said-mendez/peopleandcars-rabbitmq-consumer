package iuresti.training.peopleandcars.repository;

import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PeopleRepositoryTest {
    @Autowired
    PeopleDao peopleDao;

    @Test
    void tryingToAddPeopleWithoutMandatoryAttributesThrowDataIntegrityViolationException() {
        // Given:
        UUID uuid = UUID.randomUUID();
        PeopleDB personDB = new PeopleDB();
        personDB.setGuid(uuid.toString());
        personDB.setFirstName("Test");
        personDB.setLastName("Male");
        personDB.setGender("male");

        // When:
        // Then:
        assertThatThrownBy(() -> peopleDao.save(personDB))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class)
        ;
    }

    @Test
    void tryingToAddPersonWithRepeatedPKThrowDataIntegrityViolationException() {
        // Given:
        PeopleDB personDB = new PeopleDB();
        personDB.setGuid("45059a71-988f-424c-8aa8-3182e8d76711");
        personDB.setFirstName("Test");
        personDB.setLastName("Male");
        personDB.setGender("male");

        // When:
        // Then:
        assertThatThrownBy(() -> peopleDao.save(personDB))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class)
        ;
    }

    @Test
    void canGetPeopleByCarsVin() {
        // Given:
        // Then:
        List<PeopleDB> peopleDBList = peopleDao.findPeopleByCarsVin("ABC-1234");
        System.out.println(peopleDBList.size());
        assertThat(peopleDBList.size()).isLessThanOrEqualTo(2);
    }
}
