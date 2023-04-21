package iuresti.training.peopleandcars.modelapi;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import iuresti.training.peopleandcars.annotation.IgnoreCoverage;

import javax.validation.constraints.*;


import java.util.*;
import javax.annotation.Generated;

/**
 * Car
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-04-05T18:10:17.554620-06:00[America/Mexico_City]")
public class Car {

  @JsonProperty("vin")
  private String vin;

  @JsonProperty("brand")
  private String brand;

  @JsonProperty("model")
  private String model;

  @JsonProperty("year")
  private Integer year;

  @JsonProperty("color")
  private String color;

  public Car vin(String vin) {
    this.vin = vin;
    return this;
  }

  /**
   * Get vin
   * @return vin
  */
  
  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public Car brand(String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Get brand
   * @return brand
  */
  @NotNull 
  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Car model(String model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   * @return model
  */
  @NotNull 
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Car year(Integer year) {
    this.year = year;
    return this;
  }

  /**
   * Get year
   * @return year
  */
  @NotNull 
  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Car color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Get color
   * @return color
  */
  @NotNull 
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @IgnoreCoverage
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Car car = (Car) o;
    return Objects.equals(this.vin, car.vin) &&
        Objects.equals(this.brand, car.brand) &&
        Objects.equals(this.model, car.model) &&
        Objects.equals(this.year, car.year) &&
        Objects.equals(this.color, car.color);
  }

  @IgnoreCoverage
  @Override
  public int hashCode() {
    return Objects.hash(vin, brand, model, year, color);
  }

  @IgnoreCoverage
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Car {\n");
    sb.append("    vin: ").append(toIndentedString(vin)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  @IgnoreCoverage
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

