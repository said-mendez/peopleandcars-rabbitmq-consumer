package iuresti.training.peopleandcars.modelapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PeopleTest {
    @Test
    void canCreatePeople() {
        // Given:
        People people = new People();
        people.guid("123-qwerty-123");
        people.firstName("FirstName");
        people.lastName("LastName");
        people.gender("male");

        // When:
        People p = people.email("email@mail.com");

        // Then:
        assertThat(p).isEqualTo(people);
    }
}
