package de.kevcodez.metronom.model.delay;

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
 * Class to parse delay notifications from the Metronom SOAP endpoint.
 * 
 * @author Kevin Grüneberg
 *
 */
public class DelayParser {

  private static final String METRONOM_DELAY_URL = "http://www.der-metronom.de/extern/sharepoint/sharepoint.soap.php";

  private static ObjectMapper objectMapper = new ObjectMapper();

  public List<Delay> parseDelays() {
    String pageSource = getWebPabeSource(METRONOM_DELAY_URL);
    try {
      JsonNode mainJsonNode = objectMapper.readTree(pageSource);
      JsonNode delayJsonNode = mainJsonNode.get("ListItem");

      List<Delay> delays = new ArrayList<>();
      delayJsonNode.forEach(jsonDelay -> delays.add(DelayConverter.convert(jsonDelay)));

      return delays;
    } catch (IOException exc) {
      throw Exceptions.unchecked(exc);
    }
  }

  private String getWebPabeSource(String sURL) {
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
