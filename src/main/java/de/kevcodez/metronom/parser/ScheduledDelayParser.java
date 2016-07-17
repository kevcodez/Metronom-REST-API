package de.kevcodez.metronom.parser;

import de.kevcodez.metronom.DelayCache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Class to parse delay notifications.
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class ScheduledDelayParser {

  private static final String METRONOM_DELAY_URL = "http://www.der-metronom.de/extern/sharepoint/sharepoint.soap.php";

  @Inject
  private DelayCache delayCache;

  /**
   * Parses the delay notifications frmo the Metronom SOAP endpoint (see {@link #METRONOM_DELAY_URL}) and puts them in
   * the {@link DelayCache}. This function is executed every 30 seconds.
   */
  @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
  public void parseDelays() throws IOException {
    Document document = Jsoup.connect(METRONOM_DELAY_URL).get();
    JSONObject jsonObj = new JSONObject(document.body().text());
    JSONArray jsonDelays = jsonObj.getJSONArray("ListItem");

    List<Delay> delays = new ArrayList<>();
    for (Object jsonDelay : jsonDelays) {
      delays.add(DelayConverter.convert((JSONObject) jsonDelay));
    }

    delayCache.setDelays(delays);
  }

}
