package iuresti.training.peopleandcars.modelapi;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;


import java.util.*;
import javax.annotation.Generated;

/**
 * PeopleCar
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-05T18:10:17.554620-06:00[America/Mexico_City]")
public class PeopleCar {

  @JsonProperty("uuid")
  private String uuid;

  @JsonProperty("peopleId")
  private String peopleId;

  @JsonProperty("carId")
  private String carId;

  public PeopleCar uuid(String uuid) {
    this.uuid = uuid;
    return this;
  }

  /**
   * Get uuid
   * @return uuid
  */
  
  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public PeopleCar peopleId(String peopleId) {
    this.peopleId = peopleId;
    return this;
  }

  /**
   * Get peopleId
   * @return peopleId
  */
  @NotNull 
  public String getPeopleId() {
    return peopleId;
  }

  public void setPeopleId(String peopleId) {
    this.peopleId = peopleId;
  }

  public PeopleCar carId(String carId) {
    this.carId = carId;
    return this;
  }

  /**
   * Get carId
   * @return carId
  */
  @NotNull 
  public String getCarId() {
    return carId;
  }

  public void setCarId(String carId) {
    this.carId = carId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PeopleCar peopleCar = (PeopleCar) o;
    return Objects.equals(this.uuid, peopleCar.uuid) &&
        Objects.equals(this.peopleId, peopleCar.peopleId) &&
        Objects.equals(this.carId, peopleCar.carId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, peopleId, carId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PeopleCar {\n");
    sb.append("    uuid: ").append(toIndentedString(uuid)).append("\n");
    sb.append("    peopleId: ").append(toIndentedString(peopleId)).append("\n");
    sb.append("    carId: ").append(toIndentedString(carId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

