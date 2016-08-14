package de.kevcodez.metronom.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to download the source from an URL.
 * 
 * @author Kevin Grüneberg
 *
 */
public class WebsiteSourceDownloader {

  /**
   * Gets the source of the web page at the given URL.
   * 
   * @param websiteUrl website URL
   * @return page source
   */
  public String getSource(String websiteUrl) {
    try {
      URL url = new URL(websiteUrl);
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
