package de.kevcodez.metronom.model.alert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.kevcodez.metronom.utility.Exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse alert notifications from the Metronom SOAP endpoint.
 * 
 * @author Kevin Grüneberg
 *
 */
public class AlertParser {

  private static final String METRONOM_ALERT_URL = "http://www.der-metronom.de/extern/sharepoint/sharepoint.soap.php";

  private static ObjectMapper objectMapper = new ObjectMapper();

  public List<Alert> parseAlerts() {
    String pageSource = getWebPabeSource(METRONOM_ALERT_URL);
    try {
      JsonNode mainJsonNode = objectMapper.readTree(pageSource);
      JsonNode alertJsonNode = mainJsonNode.get("ListItem");

      List<Alert> alerts = new ArrayList<>();
      alertJsonNode.forEach(jsonAlert -> alerts.add(AlertConverter.convert(jsonAlert)));

      return alerts;
    } catch (IOException exc) {
      throw Exceptions.unchecked(exc);
    }
  }

  public static String getWebPabeSource(String sURL) {
    try {
      URL url = new URL(sURL);
      URLConnection urlConn = url.openConnection();
      BufferedReader inputStream = new BufferedReader(
        new InputStreamReader(urlConn.getInputStream(), StandardCharsets.UTF_8));

      String inputLine;
      StringBuilder stringBuilder = new StringBuilder();

      while ((inputLine = inputStream.readLine()) != null) {
        stringBuilder.append(inputLine);
      }

      inputStream.close();

      return stringBuilder.toString();
    } catch (IOException exc) {
      throw Exceptions.unchecked(exc);
    }
  }

}
