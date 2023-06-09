/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package iuresti.training.peopleandcars.controller;

import iuresti.training.peopleandcars.modelapi.Car;
import iuresti.training.peopleandcars.modelapi.People;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-05T18:10:17.554620-06:00[America/Mexico_City]")
@Validated
public interface CarsApi {

    /**
     * POST /cars : Add a new car
     *
     * @param body Create a new car in the DB (required)
     * @return Successful operation (status code 201)
     *         or Invalid request (status code 404)
     */
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/cars",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Car> addCar(
         @Valid @RequestBody Car body
    );


    /**
     * POST /cars/{vin} : Creates a new car
     *
     * @param vin ID of car that needs to be created (required)
     * @param car Car object to be added to DB (required)
     * @return Successful operation (status code 201)
     *         or Invalid request (status code 400)
     */
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/cars/{vin}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    ResponseEntity<Car> addCarWithVIN(
         @PathVariable("vin") String vin,
         @Valid @RequestBody Car car
    );


    /**
     * DELETE /cars/{vin} : Deletes a car
     *
     * @param vin Car id to delete (required)
     * @return Successful operation (status code 200)
     *         or Invalid VIN supplied (status code 400)
     *         or Car not found (status code 404)
     */
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/cars/{vin}"
    )
    ResponseEntity<Map<String, Boolean>> deleteCar(
         @PathVariable("vin") String vin
    );


    /**
     * GET /cars/{vin}/people : Returns a list of people assigned to the given car VIN
     * Obtain a list of people related to a car
     *
     * @param vin ID of searched car (required)
     * @return Successful operation (status code 200)
     */
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/cars/{vin}/people",
        produces = { "application/json" }
    )
    ResponseEntity<List<People>> fetchAllCarPeople(
         @PathVariable("vin") String vin
    );


    /**
     * GET /cars : Returns a list of cars
     * Obtain a list of cars
     *
     * @return Successful operation (status code 200)
     */
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/cars",
        produces = { "application/json" }
    )
    ResponseEntity<List<Car>> fetchAllCars(
        
    );


    /**
     * GET /cars/{vin} : Find car by VIN
     * Returns a single car
     *
     * @param vin ID of car to return (required)
     * @return Successful operation (status code 200)
     *         or Invalid ID supplied (status code 400)
     *         or Person not found (status code 404)
     */
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/cars/{vin}",
        produces = { "application/json" }
    )
    ResponseEntity<Car> fetchCarById(
         @PathVariable("vin") String vin
    );


    /**
     * PUT /cars/{vin} : Updated car
     * Update an existing car
     *
     * @param vin VIN that need to be updated (required)
     * @param body Car that needs to be added to DB (required)
     * @return Successful operation (status code 200)
     *         or Invalid GUID supplied (status code 400)
     *         or Person not found (status code 404)
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/cars/{vin}",
        consumes = { "application/json" }
    )
    ResponseEntity<Map<String, Boolean>> updateCar(
         @PathVariable("vin") String vin,
         @Valid @RequestBody Car body
    );

}
