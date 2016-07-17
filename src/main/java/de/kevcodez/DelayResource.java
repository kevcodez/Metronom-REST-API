package de.kevcodez;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("delay")
@Produces({ MediaType.APPLICATION_JSON })
public class DelayResource {

  @Inject
  private DelayCache delayCache;

  @GET
  public List<Delay> findAllDelays() {
    return delayCache.getDelays();
  }

  @GET
  @Path("since/{since}")
  public List<Delay> findDelaysSince(@PathParam(value = "since") String dateTime) {
    LocalDateTime localDateTime = LocalDateTime.parse(dateTime);

    if (localDateTime != null) {
      return streamDelays().filter(d -> d.getCreatedAt().isAfter(localDateTime)).collect(toList());
    }

    return Collections.emptyList();
  }

  @GET
  @Path("contains/{text}")
  public List<Delay> findByText(@PathParam(value = "text") String text) {
    return streamDelays().filter(d -> d.getText().contains(text)).collect(toList());
  }

  private Stream<Delay> streamDelays() {
    return delayCache.getDelays().stream();
  }

}
