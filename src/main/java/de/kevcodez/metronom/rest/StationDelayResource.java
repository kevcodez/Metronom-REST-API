/**
 * MIT License
 * <p>
 * Copyright (c) 2016 Kevin Grüneberg
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package de.kevcodez.metronom.rest;

import de.kevcodez.metronom.model.delay.StationDelay;
import de.kevcodez.metronom.model.station.Station;
import de.kevcodez.metronom.parser.StationDelayParser;
import de.kevcodez.metronom.provider.StationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST resource that provides possible delays for a given station.
 *
 * @author Kevin Grüneberg
 */
@RestController
@RequestMapping(value = "stationDelay", produces = "application/json")
public class StationDelayResource {

    @Autowired
    private StationDelayParser stationDelayParser;

    @Autowired
    private StationProvider stationProvider;

    /**
     * Gets the delay information for the station with the given name.
     *
     * @param name station name
     * @return station delay information
     */
    @GetMapping("{name}")
    public StationDelay findStationDelayByName(@PathVariable("name") String name) {
        // TODO Cache for at least 30 seconds
        return stationDelayParser.findDelays(name);
    }

    /**
     * Gets the delay information for the station with the given code.
     *
     * @param code station code
     * @return station delay information
     */
    @GetMapping("code/{code}")
    public StationDelay findStationDelayByCode(@PathVariable("code") String code) {
        Station station = stationProvider.findStationByCode(code);
        return findStationDelayByName(station.getName());
    }
}
