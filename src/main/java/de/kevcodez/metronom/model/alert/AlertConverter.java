package de.kevcodez.metronom.model.alert;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Converts the JSON object from the Metronom endpoint to the internal {@link Alert} object.
 * 
 * @author Kevin Grüneberg
 *
 */
public class AlertConverter {

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

  /**
   * Converts the given JSON object to a {@link Alert}.
   * 
   * @param alert alert as JSON OBJECT
   * @return converted alert
   */
  public static Alert convert(JsonNode alert) {
    String meldung = alert.get("Stoermeldung").textValue();
    String empfangsdatum = alert.get("Empfangsdatum").textValue();
    String id = alert.get("@attributes").get("ID").textValue();
    LocalDateTime dateTime = LocalDateTime.parse(empfangsdatum, formatter);

    return new Alert(id, meldung, dateTime);
  }

}
