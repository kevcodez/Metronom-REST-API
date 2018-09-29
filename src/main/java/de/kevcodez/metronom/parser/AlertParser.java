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
package de.kevcodez.metronom.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kevcodez.metronom.converter.AlertConverter;
import de.kevcodez.metronom.model.alert.Alert;
import de.kevcodez.metronom.utility.Exceptions;
import de.kevcodez.metronom.utility.WebsiteSourceDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.kevcodez.metronom.utility.WebsiteSourceDownloader.getCookiesFromUrl;

/**
 * Class to parse alert notifications from the Metronom SOAP endpoint.
 *
 * @author Kevin Grüneberg
 */
@Component
public class AlertParser {

    public static final String METRONOM_ALERT_URL = "https://www.der-metronom.de/livedata/etc?type=troublelist&_=1538252265246";

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AlertConverter alertConverter;

    @Autowired
    private WebsiteSourceDownloader websiteSourceDownloader;

    /**
     * Parses the alerts from the Metronom SOAP endpoint.
     *
     * @return list of alerts
     */
    public List<Alert> parseAlerts() {
        Map<String, String> cookies = getCookiesFromUrl("https://www.der-metronom.de/ueber-metronom/wer-wir-sind");
        String pageSource = websiteSourceDownloader.getSource(METRONOM_ALERT_URL, cookies);
        return parseAlertsFromSource(pageSource);
    }

    private List<Alert> parseAlertsFromSource(String pageSource) {
        try {
            JsonNode mainJsonNode = objectMapper.readTree(pageSource);
            JsonNode alertJsonNode = mainJsonNode.get("stoerung");

            List<Alert> alerts = new ArrayList<>();
            alertJsonNode.forEach(jsonAlert -> {
                Alert alert = alertConverter.convert(jsonAlert);

                alerts.add(alert);
            });

            return alerts;
        } catch (IOException exc) {
            throw Exceptions.unchecked(exc);
        }
    }

}
