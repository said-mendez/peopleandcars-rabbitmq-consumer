package iuresti.training.peopleandcars.repository;

import iuresti.training.peopleandcars.modeldb.PeopleDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PeopleDaoTest {
    @Autowired
    PeopleDao peopleDao;

    @MockBean
    PeopleCarDao peopleCarDao;

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
    void tryingToAddPersonWithRepeatedEmailThrowDataIntegrityViolationException() {
        // Given:
        UUID uuid = UUID.randomUUID();
        PeopleDB personDB = new PeopleDB();
        personDB.setGuid(uuid.toString());
        personDB.setFirstName("Test");
        personDB.setLastName("Male");
        personDB.setGender("male");
        personDB.setEmail("test@mail.com");

        // When:
        // Then:
        assertThatThrownBy(() -> peopleDao.save(personDB))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class)
        ;
    }
}
