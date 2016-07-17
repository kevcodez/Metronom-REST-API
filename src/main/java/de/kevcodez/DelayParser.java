package de.kevcodez;

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

@Singleton
public class DelayParser {

  private static final String METRONOM_DELAY_URL = "http://www.der-metronom.de/extern/sharepoint/sharepoint.soap.php";

  @Inject
  private DelayCache delayCache;

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
