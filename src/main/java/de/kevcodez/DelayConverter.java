package de.kevcodez;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

public class DelayConverter {

  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

  public static Delay convert(JSONObject delay) {

    String meldung = delay.getString("Stoermeldung");
    String empfangsdatum = delay.getString("Empfangsdatum");
    String id = delay.getJSONObject("@attributes").getString("ID");
    LocalDateTime dateTime = LocalDateTime.parse(empfangsdatum, formatter);

    return new Delay(id, meldung, dateTime);
  }

}
