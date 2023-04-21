package iuresti.training.peopleandcars.rabbitmqlistener;

import iuresti.training.peopleandcars.config.QueuesConfig;
import iuresti.training.peopleandcars.modelapi.People;
import iuresti.training.peopleandcars.service.PeopleService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PeopleListener {
    private PeopleService peopleService;

    public PeopleListener(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @RabbitListener(queues = QueuesConfig.PEOPLE_QUEUE)
    public void listener(People people) {
        peopleService.addPeopleWithGUID(people.getGuid(), people);
        System.out.println("people = " + people);
    }
}
