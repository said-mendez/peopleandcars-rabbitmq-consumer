package iuresti.training.peopleandcars.modelapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
public class PeopleCarTest {
    @Test
    void canCreatePeopleCar() {
        // Given:
        PeopleCar expectedPeopleCar = new PeopleCar();
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString();
        UUID uuid1 = UUID.randomUUID();
        String vin = uuid.toString();

        expectedPeopleCar.uuid(UUID.randomUUID().toString());
        expectedPeopleCar.carId(vin);

        // When:
        expectedPeopleCar.peopleId(guid);

        // Then:
    }
}
