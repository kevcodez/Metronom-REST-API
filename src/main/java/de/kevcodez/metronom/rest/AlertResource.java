package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.alert.Alert;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST resource that provides alert notifications.
 * 
 * @author Kevin Grüneberg
 *
 */
@Path("alert")
@Produces({ MediaType.APPLICATION_JSON })
public interface AlertResource {

  /**
   * Finds all alerts.
   * 
   * @return list of alerts
   */
  @GET
  List<Alert> findAllAlerts();

  /**
   * Finds all alerts since the given date time. The date time must be in ISO-8601 format, otherwise an empty collection
   * will be returned.
   * 
   * @param dateTime minimum date time
   * @return matching alerts
   */
  @GET
  @Path("since/{since}")
  List<Alert> findAlertsSince(@PathParam(value = "since") String dateTime);

  /**
   * Finds all alerts that contains the given text.
   * 
   * @param text text to search for
   * @return matching alerts
   */
  @GET
  @Path("contains/{text}")
  List<Alert> findByText(@PathParam(value = "text") String text);

}
