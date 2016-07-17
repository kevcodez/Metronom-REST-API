package de.kevcodez.metronom.rest;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XMLAdapter for {@link LocalDateTime} so date times will be encoded using ISO-8601 in REST responses.
 * 
 * @author Kevin Grüneberg
 *
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

  @Override
  public LocalDateTime unmarshal(String s) throws Exception {
    return LocalDateTime.parse(s);
  }

  @Override
  public String marshal(LocalDateTime dateTime) throws Exception {
    return dateTime.toString();
  }

}
