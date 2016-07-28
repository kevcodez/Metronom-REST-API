/**
 * MIT License
 * 
 * Copyright (c) 2016 Kevin Grüneberg
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
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
   * Finds the alerts relevant for the station with the given name that were created during the last hour.
   * 
   * @param station station name
   * @return relevant alerts
   */
  @GET
  @Path("station/{station}")
  List<Alert> findRelevantAlertsForStation(@PathParam(value = "station") String station);

  /**
   * Finds the alerts where the system could not assign a start or stop station.
   * 
   * @return list of alerts
   */
  @GET
  @Path("unknownStations")
  List<Alert> findAlertsWithUnknownStation();

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
