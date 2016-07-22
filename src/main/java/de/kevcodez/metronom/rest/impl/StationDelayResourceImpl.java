package de.kevcodez.metronom.rest.impl;

import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.model.delay.StationDelayParser;
import de.kevcodez.metronom.model.stop.Station;
import de.kevcodez.metronom.model.stop.StationProvider;
import de.kevcodez.metronom.rest.StationDelayResource;

import javax.inject.Inject;

/**
 * Implements {@link StationDelayResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
public class StationDelayResourceImpl implements StationDelayResource {

  @Inject
  private StationDelayParser stationDelayParser;

  @Inject
  private StationProvider stationProvider;

  @Override
  public StationDelay findStationDelayByName(String name) {
    return stationDelayParser.findDelays(name);
  }

  @Override
  public StationDelay findStationDelayByCode(String code) {
    Station station = stationProvider.findStationByCode(code);
    return stationDelayParser.findDelays(station);
  }
}
