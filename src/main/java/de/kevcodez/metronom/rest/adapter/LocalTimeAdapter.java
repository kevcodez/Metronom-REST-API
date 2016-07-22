package de.kevcodez.metronom.rest.adapter;

import java.time.LocalTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * XMLAdapter for {@link LocalTime} so times will be encoded in HH:mm format in REST responses.
 * 
 * @author Kevin Grüneberg
 *
 */
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

  @Override
  public LocalTime unmarshal(String s) throws Exception {
    return LocalTime.parse(s);
  }

  @Override
  public String marshal(LocalTime time) throws Exception {
    return time.toString();
  }

}
