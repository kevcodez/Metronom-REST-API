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
    addStation("Hamburg Hbf", "AH");
    addStation("Hamburg-Harburg", "AHAR");
    addStation("Meckelfeld", "AMDH");
    addStation("Maschen", "AMA");
    addStation("Stelle", "ASTE");
    addStation("Ashausen", "AASN");
    addStation("Winsen (Luhe)", "AWI");
    addStation("Radbruch", "ARH");
    addStation("Bardowick", "ABAD");
    addStation("Lüneburg", "ALBG");
    addStation("Bienenbüttel", "ABIL");
    addStation("Bad Bevensen", "ABVS");
    addStation("Uelzen", "HU");
    addStation("Hannover Hbf", "HH");
    addStation("Sarstedt", "HSRD");
    addStation("Nordstemmen", "HNOS");
    addStation("Elze (Han)", "HELZ");
    addStation("Banteln", "HBAN");
    addStation("Alfeld (Leine)", "HALF");
    addStation("Freden (Leine)", "HFRE");
    addStation("Kreiensen", "HK");
    addStation("Einbeck Salzderhelden", "HEB");
    addStation("Northeim (Han)", "HN");
    addStation("Nörten-Hardenberg", "HNTH");
    addStation("Göttigen", "HG");
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
    addStation("Buchholz (Nordheide)", "ABLZ");
    addStation("Sprötze", "ASP");
    addStation("Tostedt", "ATST");
    addStation("Lauenbrück", "ALUB");
    addStation("Scheeßel", "ASL");
    addStation("Rotenburg (Wümme)", "AROG");
    addStation("Sottrum", "AS");
    addStation("Ottersberg (Han)", "AOBG");
    addStation("Sagehorn", "ASAG");
    addStation("Bremen-Oberneuland", "HBON");
    addStation("Bremen Hbf", "HB");
    addStation("Suderburg", "HSUD");
    addStation("Unterlüß", "HUNL");
    addStation("Eschede", "HESD");
    addStation("Celle", "HC");
    addStation("Großburgwedel", "HGBW");
    addStation("Isernhagen", "HIHG");
    addStation("Langenhagen Mitte", "HLGM");
  }

  /**
   * Finds the station with the given name.
   * 
   * @param name name to search for
   * @return matching station, or null
   */
  public Station findStationByName(String name) {
    return stations.stream().filter(station -> station.getName().equals(name)).findFirst()
      .orElse(null);
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

  private void addStation(String name, String code) {
    Station station = new Station(name, code);
    stations.add(station);
  }

}
