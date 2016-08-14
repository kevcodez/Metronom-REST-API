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
package de.kevcodez.metronom.provider;

import de.kevcodez.metronom.model.route.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * The route provider provides all routes statically.
 * 
 * @author Kevin Grüneberg
 *
 */
@Singleton
public class RouteProvider {

  @Inject
  private StationProvider stationProvider;

  private List<Route> routes = new ArrayList<>();

  /**
   * Creates the routes.
   */
  @PostConstruct
  public void constructRoutes() {
    addRouteElbeTakt();
    addRouteNordseeTakt();
    addRouteWeserTakt();
    addRouteAllerTakt();
    addRouteLeinetalTakt();
  }

  public List<Route> getRoutes() {
    return Collections.unmodifiableList(routes);
  }

  private void addRouteElbeTakt() {
    Route route = new Route("Elbe-Takt");
    route.addTrains("RB 31", "RE 3");

    String[] stations = { "Hamburg", "Hamburg-Harburg", "Meckelfeld", "Maschen", "Stelle",
      "Ashausen", "Winsen", "Radbruch", "Bardowick", "Lüneburg", "Bienenbüttel",
      "Bad Bevensen", "Uelzen" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addRouteNordseeTakt() {
    Route route = new Route("Nordsee-Takt");
    route.addTrains("RE 5");

    String[] stations = { "Hamburg", "Hamburg-Harburg", "Buxtehude", "Horneburg", "Stade",
      "Hammah", "Himmelpforten", "Hechthausen", "Hemmoor", "Wingst", "Cadenberge", "Cuxhaven" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addRouteWeserTakt() {
    Route route = new Route("Weser-Takt");
    route.addTrains("RB41", "RE 4");

    String[] stations = { "Hamburg", "Hamburg-Harburg", "Hittfeld", "Klecken",
      "Buchholz", "Sprötze", "Tostedt", "Lauenbrück", "Scheeßel", "Rotenburg",
      "Sottrum", "Ottersberg", "Sagehorn", "Bremen-Oberneuland", "Bremen" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addRouteAllerTakt() {
    Route route = new Route("Aller-Takt");
    route.addTrains("RE 2");

    String[] stations = { "Uelzen", "Suderburg", "Unterlüß", "Eschede", "Celle", "Großburgwedel",
      "Isernhagen", "Langenhagen Mitte", "Hannover" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addRouteLeinetalTakt() {
    Route route = new Route("Leinetal-Takt");
    route.addTrains("RE 2");

    String[] stations = { "Hannover Hbf", "Sarstedt", "Nordstemmen", "Elze", "Banteln",
      "Alfeld", "Freden", "Kreiensen", "Einbeck Salzderhelden", "Northeim",
      "Nörten-Hardenberg", "Göttingen" };

    addStations(stations, route);
    routes.add(route);
  }

  private void addStations(String[] stops, Route route) {
    Arrays.stream(stops).map(stationProvider::findStationByName).forEach(route::addStation);
  }

}
