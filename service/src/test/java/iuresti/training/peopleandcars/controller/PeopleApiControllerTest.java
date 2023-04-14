package iuresti.training.peopleandcars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import iuresti.training.peopleandcars.modelapi.People;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

// @SpringBootTest Tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance)
// and use that to start a Spring application context.
// Note the use of webEnvironment=RANDOM_PORT to start the server with a random port (useful to avoid conflicts in test environments)
@SpringBootTest
//@WebMvcTest(PeopleApiController.class)
public class PeopleApiControllerTest {
//    @Autowired
//     Fake http requests for us
//    private MockMvc mockMvc;
//    @Autowired
//     WebClient provides a common interface for making web requests in a non-blocking way.
//    private WebTestClient webClient;
//     We need to provide a bean for anything the controller depends on
//    @MockBean
//    private PeopleService peopleService;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAListOfPeople() {
        ResponseEntity<People[]> response = restTemplate.getForEntity("/api/people", People[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        People[] arr = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
//        People[] people = mapper.readValue(response.getBody(), new TypeReference<People[]>(){}.getType());
//        assertThat(this.restTemplate.getForEntity("http://localhost:8080/api/people", People.class, <List<People>>));
    }

//    @Test
//    public void shouldReturnAListOfPeople() throws Exception {
//        webClient.get().uri("/api/people")
//                .exchange()
//                .expectStatus().isOk();
//    }

//    @Test
//    public void addPeople() {
//        // Given:
//        People people = new People();
//        people.setFirstName("Gustavo");
//        people.setLastName("Paez");
//        people.setEmail("tavopaez@mail.com");
//        people.setGender("male");
//
//        // When:
//        given(peopleService.addPeople(any(People.class)))
//                .willAnswer((invocation) -> invocation.getArgument(0));
//
//        // Then:
//    }
}
