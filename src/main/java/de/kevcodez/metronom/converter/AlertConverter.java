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
package de.kevcodez.metronom.converter;

import com.fasterxml.jackson.databind.JsonNode;
import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.provider.StationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts the JSON object from the Metronom endpoint to the internal {@link Alert} object.
 *
 * @author Kevin Grüneberg
 */
@Component
public class AlertConverter {

    private static final Pattern PATTERN_PLANNED_DEPARTURE = Pattern.compile("((?<time>\\d{1,2}:\\d{2})( ?)(Uhr)?)");

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Autowired
    public AlertConverter(StationProvider stationProvider) {
        this.stationProvider = stationProvider;
    }

    private StationProvider stationProvider;


    public Alert convert(JsonNode alert) {
        String text = alert.get("text").textValue();
        String empfangsdatum = alert.get("datum").textValue();
        String timeFrom = alert.get("time_von").textValue();
        String timeTo = alert.get("time_von").textValue();

        LocalDate createdAt = LocalDate.parse(empfangsdatum.split("\\+")[0]);

        LocalTime plannedDeparture = parsePlannedDeparture(text);

        Alert parsedAlert = new Alert(text, createdAt, plannedDeparture);
        parsedAlert.setTimeFrom(LocalTime.parse(timeFrom.split("\\+")[0]));
        parsedAlert.setTimeTo(LocalTime.parse(timeTo.split("\\+")[0]));
        parsedAlert.setStationStart(stationProvider.findStationByName(alert.get("bhfvon").textValue()));
        parsedAlert.setStationEnd(stationProvider.findStationByName(alert.get("bhfnach").textValue()));

        return parsedAlert;
    }

    public LocalTime parsePlannedDeparture(String alert) {
        Matcher matcher = PATTERN_PLANNED_DEPARTURE.matcher(alert);

        if (matcher.find()) {
            String time = matcher.group("time");

            String[] splitTime = time.split(":");

            // Add leading zero
            if (splitTime[0].length() == 1) {
                time = "0" + time;
            }

            return LocalTime.parse(time);
        }

        return null;
    }

}
