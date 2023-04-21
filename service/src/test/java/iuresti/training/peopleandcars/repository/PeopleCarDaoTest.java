package iuresti.training.peopleandcars.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PeopleCarDaoTest {
    @Autowired
    PeopleCarDao peopleCarDao;

    private final int MAX_CAR_COUNT = 2;
    private final int MAX_PEOPLE_CAR_COUNT = 1;

    @Test
    void shouldCountByCarIdAndBeLessOrEqualThanTwo () {
        // Given:
        String carId = "UYL-137-B";

        // When:
        int actualCarCount = peopleCarDao.countByCarId(carId);

        // Then:
        assertThat(actualCarCount).isLessThanOrEqualTo(MAX_CAR_COUNT);
    }

    @Test
    void shouldCountByRandomCarIdAndBeLessOrEqualThanTwo () {
        // Given:
        String carId = UUID.randomUUID().toString();

        // When:
        int actualCarCount = peopleCarDao.countByCarId(carId);

        // Then:
        assertThat(actualCarCount).isLessThanOrEqualTo(MAX_CAR_COUNT);
    }

    @Test
    void shouldCountByCarIdAndPeopleIdAndLessOrEqualThanOne () {
        // Given:
        String peopleId = "73abf8c2-21d0-4f2a-9b9f-447ab9a8dd16";
        String carId = "UYL-137-B";

        // When:
        int actualCarCount = peopleCarDao.countByCarIdAndPeopleId(carId, peopleId);

        // Then:
        assertThat(actualCarCount).isLessThanOrEqualTo(MAX_PEOPLE_CAR_COUNT);
    }

    @Test
    void shouldCountByRandomCarIdAndRandomPeopleIdAndLessOrEqualThanOne () {
        // Given:
        String peopleId = UUID.randomUUID().toString();
        String carId = UUID.randomUUID().toString();

        // When:
        int actualCarCount = peopleCarDao.countByCarIdAndPeopleId(carId, peopleId);

        // Then:
        assertThat(actualCarCount).isLessThanOrEqualTo(MAX_PEOPLE_CAR_COUNT);
    }
}
