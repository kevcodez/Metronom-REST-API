package de.kevcodez.metronom.test.rest.adapter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import de.kevcodez.metronom.rest.adapter.LocalDateTimeAdapter;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * Tests {@link LocalDateTimeAdapter}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class LocalDateTimeAdapterTest {

  private LocalDateTimeAdapter adapter = new LocalDateTimeAdapter();

  @Test
  public void shouldMarshalDateTime() throws Exception {
    String marshalledDateTime = adapter.marshal(LocalDateTime.of(2016, 12, 31, 23, 59));

    assertThat(marshalledDateTime, is("2016-12-31T23:59"));
  }

  @Test
  public void shouldUnmarshalDateTime() throws Exception {
    LocalDateTime unmarshalledDateTime = adapter.unmarshal("2016-12-31T23:59");

    assertThat(unmarshalledDateTime.getYear(), is(2016));
    assertThat(unmarshalledDateTime.getMonthValue(), is(12));
    assertThat(unmarshalledDateTime.getDayOfMonth(), is(31));
    assertThat(unmarshalledDateTime.getHour(), is(23));
    assertThat(unmarshalledDateTime.getMinute(), is(59));
  }

}
