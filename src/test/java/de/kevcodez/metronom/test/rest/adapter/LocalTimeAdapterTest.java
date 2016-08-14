package de.kevcodez.metronom.test.rest.adapter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import de.kevcodez.metronom.rest.adapter.LocalTimeAdapter;

import java.time.LocalTime;

import org.junit.Test;

/*
 * Tests {@link LocalTimeAdapter}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class LocalTimeAdapterTest {

  private LocalTimeAdapter adapter = new LocalTimeAdapter();

  @Test
  public void shouldMarshalLocalTime() throws Exception {
    String marshalledTime = adapter.marshal(LocalTime.of(23, 59));

    assertThat(marshalledTime, is("23:59"));
  }

  @Test
  public void shouldUnmarshalLocalTime() throws Exception {
    LocalTime unmarshalledTime = adapter.unmarshal("23:59");

    assertThat(unmarshalledTime.getHour(), is(23));
    assertThat(unmarshalledTime.getMinute(), is(59));
  }

}
