/**
 * MIT License
 * 
 * Copyright (c) 2016 Kevin Grüneberg
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
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
