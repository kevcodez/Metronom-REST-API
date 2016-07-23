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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a single station. The station also contains a list of alternative names to resolve the station more
 * easily. For example the station Hannover Hbf also has the alternative name Hannover.
 * 
 * @author Kevin Grüneberg
 *
 */
public class Station {

  private String name;
  private List<String> alternativeNames = new ArrayList<>();
  private String code;

  /**
   * Creates a new station with the given name and code.
   * 
   * @param name name
   * @param code code
   */
  public Station(String name, String code) {
    this.name = name;
    this.code = code;
  }

  /**
   * Adds the given array of names to the list of alternative names.
   * 
   * @param names alternative namess
   */
  public void addAlternativeNames(String... names) {
    alternativeNames.addAll(Arrays.asList(names));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @JsonIgnore
  public List<String> getAlternativeNames() {
    return alternativeNames;
  }

  @Override
  public String toString() {
    return "Station [name=" + name + ", code=" + code + "]";
  }

}
