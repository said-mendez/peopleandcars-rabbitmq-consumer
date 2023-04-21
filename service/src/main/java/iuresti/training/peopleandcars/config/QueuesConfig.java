package iuresti.training.peopleandcars.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueuesConfig {
    public static final String CAR_QUEUE = "car_queue";
    public static final String CAR_ROUTING_KEY = "car";
    public static final String PEOPLE_QUEUE = "people_queue";
    public static final String PEOPLE_ROUTING_KEY = "people";
    public static final String PEOPLE_CAR_QUEUE = "people_car_queue";
    public static final String PEOPLE_CAR_ROUTING_KEY = "people_car";
    public static final String EXCHANGE = "my_car_exchange";

    @Bean
    public Queue carQueue() {
        return new Queue(CAR_QUEUE);
    }

    @Bean
    public Queue peopleQueue() {
        return new Queue(PEOPLE_QUEUE);
    }

    @Bean
    public Queue peopleCarQueue() {
        return new Queue(PEOPLE_CAR_QUEUE);
    }

    @Bean
    public DirectExchange direct() {return new DirectExchange(EXCHANGE);}

    @Bean
    public Binding carBinding(Queue carQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(carQueue)
                .to(directExchange)
                .with(CAR_ROUTING_KEY);
    }

    @Bean
    public Binding peopleBinding(Queue peopleQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(peopleQueue)
                .to(directExchange)
                .with(PEOPLE_ROUTING_KEY);
    }

    @Bean
    public Binding peopleCarBinding(Queue peopleCarQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(peopleCarQueue)
                .to(directExchange)
                .with(PEOPLE_CAR_ROUTING_KEY);
    }

    @Bean
    // Convert to JSON format
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    // RabbitTemplate is the implementation of AmqpTemplate
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());

        return template;
    }
}
