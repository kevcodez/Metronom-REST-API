package de.kevcodez.metronom.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

/**
 * Converts the JSON object from the Metronom endpoint to the internal {@link Delay} object.
 * 
 * @author Kevin Grüneberg
 *
 */
public class DelayConverter {

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

  /**
   * Converts the given JSON object to a {@link Delay}.
   * 
   * @param delay delay as JSON OBJECT
   * @return converted delay
   */
  public static Delay convert(JSONObject delay) {
    String meldung = delay.getString("Stoermeldung");
    String empfangsdatum = delay.getString("Empfangsdatum");
    String id = delay.getJSONObject("@attributes").getString("ID");
    LocalDateTime dateTime = LocalDateTime.parse(empfangsdatum, formatter);

    return new Delay(id, meldung, dateTime);
  }

}
