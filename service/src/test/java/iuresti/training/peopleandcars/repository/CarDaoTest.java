package iuresti.training.peopleandcars.repository;

import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.modeldb.PeopleDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CarDaoTest {
    @Autowired
    CarDao carDao;

    @Test
    void tryingToAddCarWithoutMandatoryAttributesThrowDataIntegrityViolationException() {
        // Given:
        UUID uuid = UUID.randomUUID();
        CarDB carDB = new CarDB();
        carDB.setVin(uuid.toString());
        carDB.setColor("Black");
        carDB.setModel("Impreza");
        carDB.setYear(1990);

        // When:
        // Then:
        assertThatThrownBy(() -> carDao.save(carDB))
                .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class)
        ;
    }
}
