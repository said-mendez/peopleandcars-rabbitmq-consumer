package iuresti.training.peopleandcars.rabbitmqlistener;

import iuresti.training.peopleandcars.config.QueuesConfig;
import iuresti.training.peopleandcars.modelapi.PeopleCar;
import iuresti.training.peopleandcars.service.PeopleCarService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PeopleCarListener {
    private PeopleCarService peopleCarService;

    public PeopleCarListener(PeopleCarService peopleCarService) {
        this.peopleCarService = peopleCarService;
    }

    @RabbitListener(queues = QueuesConfig.PEOPLE_CAR_QUEUE)
    public void listener(PeopleCar peopleCar) {
        peopleCarService.addPeopleCar(peopleCar);
        System.out.println("peopleCar = " + peopleCar);
    }
}
