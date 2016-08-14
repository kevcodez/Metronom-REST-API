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
package de.kevcodez.metronom.model.station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Provides all stations statically.
 * 
 * @author Kevin Grüneberg
 *
 */
@Startup
@Singleton
public class StationProvider {

  private List<Station> stations = new ArrayList<>();

  /**
   * Creates the stations.
   */
  @PostConstruct
  public void constructStations() {
    addStation("Hamburg", "AH", "Hamburg Hbf", "Hamburger Hauptbahnhof", "Hauptbahnhof Hamburg");
    addStation("Harburg", "AHAR", "Hamburg-Harburg", "Harburg", "Hamburg- Harburg", "Hamburg Harburg");
    addStation("Meckelfeld", "AMDH");
    addStation("Maschen", "AMA");
    addStation("Stelle", "ASTE");
    addStation("Ashausen", "AASN");
    addStation("Winsen", "AWI", "Winsen  (Luhe)");
    addStation("Radbruch", "ARH");
    addStation("Bardowick", "ABAD");
    addStation("Lüneburg", "ALBG", "Lueneburg");
    addStation("Bienenbüttel", "ABIL", "Bienenbuettel");
    addStation("Bevensen", "ABVS", "Bad Bevensen");
    addStation("Uelzen", "HU");
    addStation("Hannover", "HH", "Hannover Hbf");
    addStation("Sarstedt", "HSRD");
    addStation("Nordstemmen", "HNOS");
    addStation("Elze", "HELZ", "Elze  (Han)");
    addStation("Banteln", "HBAN");
    addStation("Alfeld", "HALF", "Alfeld  (Leine)");
    addStation("Freden", "HFRE", "Freden  (Leine)");
    addStation("Kreiensen", "HK");
    addStation("Salzderhelden", "HEB", "Einbeck", "Einbeck Salzderhelden");
    addStation("Northeim", "HN", "Northeim  (Han)");
    addStation("Nörten-Hardenberg", "HNTH", "Hardenberg", "Nörten", "Noerten-Hardenberg", "Noerten");
    addStation("Göttingen", "HG", "Goettingen", "Götingen");
    addStation("Buxtehude", "ABX");
    addStation("Horneburg", "AHOG");
    addStation("Stade", "AST");
    addStation("Hammah", "AHAM");
    addStation("Himmelpforten", "AHPF");
    addStation("Hechthausen", "AHEN");
    addStation("Hemmoor", "AHEM");
    addStation("Wingst", "AWG");
    addStation("Cadenberge", "ACD");
    addStation("Cuxhaven", "ACV");
    addStation("Hittfeld", "AHIF");
    addStation("Klecken", "AKC");
    addStation("Buchholz", "ABLZ", "Buchholz (Nordheide)");
    addStation("Sprötze", "ASP", "Sproetze");
    addStation("Tostedt", "ATST");
    addStation("Lauenbrück", "ALUB", "Lauenbrueck");
    addStation("Scheeßel", "ASL");
    addStation("Rotenburg", "AROG", "Rotenburg (Wümme)");
    addStation("Sottrum", "AS");
    addStation("Ottersberg", "AOBG", "Ottersberg (Han)");
    addStation("Sagehorn", "ASAG");
    addStation("Bremen-Oberneuland", "HBON", "Oberneuland", "Bremen Oberland");
    addStation("Bremen", "HB", "Bremen Hbf", "Bremen Hauptbahnhof");
    addStation("Suderburg", "HSUD");
    addStation("Unterlüß", "HUNL", "Unterlüss", "Unterlueß");
    addStation("Eschede", "HESD");
    addStation("Celle", "HC");
    addStation("Großburgwedel", "HGBW", "Grossburgwedel");
    addStation("Isernhagen", "HIHG");
    addStation("Langenhagen", "HLGM", "Langenhagen Mitte");
  }

  /**
   * Finds the station with the given name.
   * 
   * @param name name to search for
   * @return matching station, or null
   */
  public Station findStationByName(String name) {
    if (name == null) {
      return null;
    }

    return stations.stream()
      .filter(station -> stationNameMatches(name, station) || alternativeNameMatches(name, station))
      .findFirst().orElse(null);
  }

  /**
   * Checks if the given station name matches the given station. The string is compared using
   * {@link String#equalsIgnoreCase(String)}.
   * 
   * @param stationName station name to match
   * @param station station to match
   * @return true, if matches
   */
  public static boolean stationNameMatches(String stationName, Station station) {
    return station.getName().equalsIgnoreCase(stationName);
  }

  /**
   * Checks if the given station name matches any of the alternative names of the given station. The string is compared
   * using {@link String#equalsIgnoreCase(String)}.
   * 
   * @param stationName station name to match
   * @param station station to match
   * @return true, if matches
   */
  public static boolean alternativeNameMatches(String stationName, Station station) {
    return station.getAlternativeNames().stream().anyMatch(name -> name.equalsIgnoreCase(stationName));
  }

  /**
   * Finds the station with the given code.
   * 
   * @param code code to search for
   * @return matching station, or null
   */
  public Station findStationByCode(String code) {
    return stations.stream().filter(station -> station.getCode().equals(code)).findFirst()
      .orElse(null);
  }

  public List<Station> getStations() {
    return Collections.unmodifiableList(stations);
  }

  private void addStation(String name, String code, String... names) {
    Station station = new Station(name, code);
    station.addAlternativeNames(names);
    stations.add(station);
  }

}
