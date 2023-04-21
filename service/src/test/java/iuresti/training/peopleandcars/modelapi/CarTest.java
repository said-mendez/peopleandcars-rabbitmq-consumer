package iuresti.training.peopleandcars.modelapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CarTest {
    @Test
    void canCreateCar() {
        // Given:
        Car car = new Car();
        car.vin("UYL-137-A");
        car.color("Black");
        car.brand("Subaru");
        car.model("Impreza");

        // When:
        Car carActual = car.year(1990);

        // Then:
        assertThat(carActual).isEqualTo(car);
    }
}
