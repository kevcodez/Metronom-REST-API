package de.kevcodez.metronom.model.alert;

import de.kevcodez.metronom.rest.adapter.LocalDateTimeAdapter;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Class that contains all relevant information for a single alert notification from the Metronom website.
 * 
 * @author Kevin Grüneberg
 *
 */
public class Alert {

  private String id;

  private String message;

  private LocalDateTime creationDate;

  /**
   * Creates a new alert notification with the given ID, text and creation date.
   * 
   * @param id unique ID
   * @param message text message
   * @param creationDate creation date
   */
  public Alert(String id, String message, LocalDateTime creationDate) {
    this.id = id;
    this.message = message;
    this.creationDate = creationDate;
  }

  /**
   * Gets the unique ID.
   * 
   * @return unique ID
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the unique ID.
   * 
   * @param id unique ID
   */
  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
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
    Alert other = (Alert) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Stoermeldung [id=" + id + ", text=" + message + ", createdAt=" + creationDate + "]";
  }

}
