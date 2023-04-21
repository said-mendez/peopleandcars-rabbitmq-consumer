package iuresti.training.peopleandcars.rabbitmqlistener;

import iuresti.training.peopleandcars.config.QueuesConfig;
import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.service.CarService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CarListener {
    private CarService carService;

    public CarListener(CarService carService) {
        this.carService = carService;
    }

    @RabbitListener(queues = QueuesConfig.CAR_QUEUE)
    public void listener(Car car) {
        carService.addCarWithVIN(car.getVin(), car);
        System.out.println("car = " + car);
    }
}
