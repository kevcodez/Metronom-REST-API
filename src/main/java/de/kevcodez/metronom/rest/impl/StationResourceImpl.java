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
package de.kevcodez.metronom.rest.impl;

import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.model.station.StationProvider;
import de.kevcodez.metronom.rest.StationResource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Implements {@link StationResource}.
 * 
 * @author Kevin Grüneberg
 *
 */
@Stateless
public class StationResourceImpl implements StationResource {

  @Inject
  private StationProvider stationProvider;

  @Override
  public List<Station> findAll() {
    return stationProvider.getStations();
  }

  @Override
  public Station findByName(String name) {
    return stationProvider.findStationByName(name);
  }

  @Override
  public Station findByCode(String code) {
    return stationProvider.findStationByCode(code);
  }

}
