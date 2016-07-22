package de.kevcodez.metronom.model.stop;

/**
 * Represents a single station.
 * 
 * @author Kevin Grüneberg
 *
 */
public class Station {

  private String name;
  private String code;

  public Station(String name, String code) {
    this.name = name;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

}
