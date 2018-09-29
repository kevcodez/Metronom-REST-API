package de.kevcodez.metronom.utility;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class to download the source from an URL.
 *
 * @author Kevin Grüneberg
 */
@Component
public class WebsiteSourceDownloader {

    public String getSource(String websiteUrl) {
        return getSource(websiteUrl, Collections.emptyMap());
    }

    public String getSource(String websiteUrl, Map<String, String> cookies) {
        try {
            URL url = new URL(websiteUrl);
            URLConnection urlConn = url.openConnection();

            if (!cookies.isEmpty()) {
                String cookie = cookies.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
                        .collect(Collectors.joining(";"));
                urlConn.setRequestProperty("Cookie", cookie);
            }

            return getContentFromUrlConn(urlConn);
        } catch (IOException exc) {
            throw Exceptions.unchecked(exc);
        }
    }

    private String getContentFromUrlConn(URLConnection urlConn) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }


    public static Map<String, String> getCookiesFromUrl(String uri) {
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();

            Map<String, List<String>> headerFields = conn.getHeaderFields();

            Set<String> headerFieldsSet = headerFields.keySet();
            Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

            while (hearerFieldsIter.hasNext()) {
                String headerFieldKey = hearerFieldsIter.next();

                if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
                    List<String> headerFieldValue = headerFields.get(headerFieldKey);
                    return headerFieldValue.stream()
                            .map(header -> header.split(";\\s*"))
                            .map(h -> h[0].split("="))
                            .collect(Collectors.toMap(f -> f[0], f -> f[1]));
                }
            }
        } catch (IOException exc) {
            throw Exceptions.unchecked(exc);
        }

        return Collections.emptyMap();
    }


}
