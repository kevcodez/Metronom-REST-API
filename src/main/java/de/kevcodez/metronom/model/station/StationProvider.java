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
    addStation("Hamburg Hbf", "AH", "Hamburg");
    addStation("Hamburg-Harburg", "AHAR", "Harburg");
    addStation("Meckelfeld", "AMDH");
    addStation("Maschen", "AMA");
    addStation("Stelle", "ASTE");
    addStation("Ashausen", "AASN");
    addStation("Winsen (Luhe)", "AWI", "Winsen");
    addStation("Radbruch", "ARH");
    addStation("Bardowick", "ABAD");
    addStation("Lüneburg", "ALBG", "Lueneburg");
    addStation("Bienenbüttel", "ABIL", "Bienenbuettel");
    addStation("Bad Bevensen", "ABVS");
    addStation("Uelzen", "HU");
    addStation("Hannover Hbf", "HH", "Hannover");
    addStation("Sarstedt", "HSRD");
    addStation("Nordstemmen", "HNOS");
    addStation("Elze (Han)", "HELZ", "Elze");
    addStation("Banteln", "HBAN");
    addStation("Alfeld (Leine)", "HALF", "Alfeld");
    addStation("Freden (Leine)", "HFRE", "Freden");
    addStation("Kreiensen", "HK");
    addStation("Einbeck Salzderhelden", "HEB");
    addStation("Northeim (Han)", "HN", "Northeim");
    addStation("Nörten-Hardenberg", "HNTH", "Hardenberg", "Nörten", "Noerten-Hardenberg");
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
    addStation("Buchholz (Nordheide)", "ABLZ", "Buchholz");
    addStation("Sprötze", "ASP", "Sproetze");
    addStation("Tostedt", "ATST");
    addStation("Lauenbrück", "ALUB", "Lauenbrueck");
    addStation("Scheeßel", "ASL");
    addStation("Rotenburg (Wümme)", "AROG", "Rotenburg");
    addStation("Sottrum", "AS");
    addStation("Ottersberg (Han)", "AOBG", "Ottersberg");
    addStation("Sagehorn", "ASAG");
    addStation("Bremen-Oberneuland", "HBON", "Oberneuland");
    addStation("Bremen Hbf", "HB", "Bremen");
    addStation("Suderburg", "HSUD");
    addStation("Unterlüß", "HUNL", "Unterlüss", "Unterlueß");
    addStation("Eschede", "HESD");
    addStation("Celle", "HC");
    addStation("Großburgwedel", "HGBW", "Grossburgwedel");
    addStation("Isernhagen", "HIHG");
    addStation("Langenhagen Mitte", "HLGM", "Langenhagen");
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
      .filter(station -> station.getName().equalsIgnoreCase(name) || station.getAlternativeNames().contains(name))
      .findFirst().orElse(null);
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
