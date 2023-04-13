package iuresti.training.peopleandcars.service;

import iuresti.training.peopleandcars.exception.MyCarResourceNotFoundException;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modeldb.CarDB;
import iuresti.training.peopleandcars.repository.CarDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CarServiceTest {
    @MockBean
    private CarDao carDao;
    @Autowired
    private CarService carService;

    @Test
    void canGetAllCars() {
        // Given:
        List<CarDB> carDBList = new ArrayList<>();
        CarDB carDB = new CarDB();
        carDB.setVin("xyz-123-abc");
        carDB.setBrand("Toyota");
        carDB.setColor("Pink");
        carDB.setModel("Corolla");
        carDB.setYear(2002);
        carDBList.add(carDB);

        List<Car> carServiceExpected = new ArrayList<>();
        Car car = new Car();
        car.setVin("xyz-123-abc");
        car.setBrand("Toyota");
        car.setColor("Pink");
        car.setModel("Corolla");
        car.setYear(2002);
        carServiceExpected.add(car);


        // When:
        when(carDao.findAll()).thenReturn(carDBList);
        List<Car> carServiceCurrent = carService.fetchAllCars();

        // Then:
        assertThat(carServiceCurrent.size()).isEqualTo(carServiceExpected.size());
        assertThat(carServiceCurrent.get(0).getVin()).isEqualTo(carServiceExpected.get(0).getVin());
    }

    @Test
    void getAllCarsWithoutRecordsWillThrowMyCarNotFoundException() {
        // Given:
        // When:
        // Then:
        assertThatThrownBy(() -> carService.fetchAllCars())
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("There are not any cars records!");
    }

    @Test
    void canGetCarById() {
        // Given:
        CarDB carDB = new CarDB();
        carDB.setVin("xyz-123-abc");
        carDB.setBrand("Toyota");
        carDB.setColor("Pink");
        carDB.setModel("Corolla");
        carDB.setYear(2002);

        Car expectedCar = new Car();
        expectedCar.setVin("xyz-123-abc");
        expectedCar.setBrand("Toyota");
        expectedCar.setColor("Pink");
        expectedCar.setModel("Corolla");
        expectedCar.setYear(2002);

        // When:
        when(carDao.findById("xyz-123-abc")).thenReturn(Optional.of(carDB));
        Car carServiceCurrent = carService.fetchCarById("xyz-123-abc");

        // Then:
        assertThat(carServiceCurrent).isEqualTo(expectedCar);
        assertThat(carServiceCurrent.getVin()).isEqualTo(expectedCar.getVin());
    }

    @Test
    void getCarByFakeIdWillThrowMyCarNotFoundException() {
        // Given:
        // When:
        // Then:
        assertThatThrownBy(() -> carService.fetchCarById("fakeID-123123"))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("Car not found!");
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

        // When:
        when(carDao.findCarsByPeopleGuid("1234-qwerty-456")).thenReturn(carDBAssignedToPerson);
        List<Car> carServiceCurrent = carService.findCarsByPeopleGuid("1234-qwerty-456");

        // Then:
        assertThat(carServiceCurrent.size()).isEqualTo(expectedCarsAssignedToPerson.size());
        assertThat(carServiceCurrent.get(0).getVin()).isEqualTo(expectedCarsAssignedToPerson.get(0).getVin());
        assertThat(carServiceCurrent.get(1).getVin()).isEqualTo(expectedCarsAssignedToPerson.get(1).getVin());
    }

    @Test
    void willThrowMyCarNotFoundExceptionWhenPersonDoesNotHaveCarsAssigned() {
        // Given:
        String fakeGUID = "fakeGUID";

        // Then:
        // When:
        assertThatThrownBy(() -> carService.findCarsByPeopleGuid(fakeGUID))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("There are not any cars assigned to people GUID: " + fakeGUID);
    }

    @Test
    void tryingToUpdateCarWithFakeIdThrowMyCarNotFoundException() {
        // Given:
        String fakeId = "fakeId";

        Car carToUpdate = new Car();
        carToUpdate.setBrand("Toyota");
        carToUpdate.setColor("Cyan");
        carToUpdate.setModel("Supra");
        carToUpdate.setYear(2022);

        // When:
        // Then:
        assertThatThrownBy(() -> carService.updateCar(fakeId, carToUpdate))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("Car not found!");
    }

    @Test
    void tryingToDeleteCarWithFakeIdThrowMyCarNotFoundException() {
        // Given:
        String fakeId = "fakeId";

        // When:
        // Then:
        assertThatThrownBy(() -> carService.deleteCar(fakeId))
                .isInstanceOf(MyCarResourceNotFoundException.class)
                .hasMessageContaining("Car not found!");
    }
}
