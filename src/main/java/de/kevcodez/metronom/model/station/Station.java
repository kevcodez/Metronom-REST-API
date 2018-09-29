package de.kevcodez.metronom.model.station;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public Station(String name, String code) {
        this.name = name;
        this.code = code;
    }

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
        return Collections.unmodifiableList(alternativeNames);
    }

    @Override
    public String toString() {
        return "Station [name=" + name + ", code=" + code + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int codeHash = 0;
        if (code != null) {
            codeHash = code.hashCode();
        }

        return prime + codeHash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Station other = (Station) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }

}
