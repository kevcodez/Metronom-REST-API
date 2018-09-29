# metronom

API um Verkehrsmeldungen vom Metronom abzufragen.

Das Projekt basiert auf Spring Boot 2.

# Lokal starten

Voraussetzungen:

* Java 8+ ist installiert

```
git clone https://github.com/kevcodez/Metronom-REST-API.git
cd Metronom-REST-API
# Für Windows gradlew.bat nutzen
./gradlew build
java -jar ./build/libs/metronom-0.0.1-SNAPSHOT.jar
``` 

# REST-API

Das Projekt nutzt Swagger 2 samt Swagger-UI. Erreichbar unter `/swagger-ui.html`

Beispiel: http://localhost:8080/swagger-ui.html

## Alerts

##### GET /alert

Gibt eine Liste von allen aktuellen Meldungen aus.

##### GET /alert/since/`Datum in ISO-8601`

Gibt eine Liste von allen Meldungen ab dem übergebenen Datum aus.

##### GET /alert/contains/`text`

Gibt eine Liste von allen Meldungen aus, die den übergebenen `text` enhalten.


#### Beispiel-Response

```[{"id":"43702","message":"Wegen einer Störung verkehrt Zug 82815 von Uelzen nach Hannover mit einer Verspätung von ca. +44 Minuten ab Uelzen (planmäßige Abfahrt 11:09 Uhr)","creationDate":"2016-08-07T11:59:22","stationStart":{"name":"Uelzen","code":"HU"},"stationEnd":{"name":"Hannover","code":"HH"},"plannedDeparture":"11:09"},{"id":"43701","message":"Wegen Krankmeldung des Lokführers muss Zug 82816 von Göttingen nach Uelzen heute leider entfallen (planmäßige Abfahrt 12:09 Uhr), Fahrgäste nutzen bitte den Folgetakt","creationDate":"2016-08-07T11:33:22","stationStart":{"name":"Göttingen","code":"HG"},"stationEnd":{"name":"Uelzen","code":"HU"},"plannedDeparture":"12:09"},{"id":"43700","message":"Wegen einer Türstörung wird sich die Abfahrt von 82815 Uelzen nach Hannover voraussichtlich ca. 10 - 15 Minuten verzögern (planmäßige Abfahrt 11:09 Uhr)","creationDate":"2016-08-07T11:06:22","stationStart":{"name":"Uelzen","code":"HU"},"stationEnd":{"name":"Hannover","code":"HH"},"plannedDeparture":"11:09"},{"id":"43699","message":"Wegen Verspätung des Eingangszuges verkehrt Zug 81614 von Lüneburg nach Hamburg mit einer Verspätung von ca. +11 Minuten ab Lüneburg (planmäßige Abfahrt 9:32 Uhr)","creationDate":"2016-08-07T09:51:22","stationStart":{"name":"Lüneburg","code":"ALBG"},"stationEnd":{"name":"Hamburg","code":"AH"},"plannedDeparture":"09:32"}]```

## Routes

##### GET /route

Gibt eine Liste von allen Strecken aus.

#### GET /route/stop/`stop`

Gibt alle Strecken, welche die Haltestelle `stop` enthalten, z.B. Hamburg-Harburg.

#### GET /route/name/`name`

Gibt die Strecke mit dem Namen `name`, z.B. Elbe-Takt.

#### GET /route/<start>/to/`stop`

Gibt die Strecken, in dem die Haltestelle `start` UND die Haltestelle `stop` vorkommt.

#### Beispiel

```[{"name":"Elbe-Takt","trains":["RB 31","RE 3"],"stations":[{"name":"Hamburg","code":"AH"},{"name":"Hamburg-Harburg","code":"AHAR"},{"name":"Meckelfeld","code":"AMDH"},{"name":"Maschen","code":"AMA"},{"name":"Stelle","code":"ASTE"},{"name":"Ashausen","code":"AASN"},{"name":"Winsen","code":"AWI"},{"name":"Radbruch","code":"ARH"},{"name":"Bardowick","code":"ABAD"},{"name":"Lüneburg","code":"ALBG"},{"name":"Bienenbüttel","code":"ABIL"},{"name":"Bevensen","code":"ABVS"},{"name":"Uelzen","code":"HU"}]}]```

## Stations

#### GET /station

Gibt eine Liste von allen Haltestellen.

#### GET /station/name/`name`

Gibt die Haltestelle mit dem Namen `name`.

#### GET /station/code/`code`

Gibt die Haltestelle mit dem Kürzel `code`.

#### Beispiel

```{"name":"Hamburg-Harburg","code":"AHAR"}```

## StationDelay

#### GET /stationDelay/`name`

Gibt die Verspätungsinformationen zu der Haltestelle `name`.

#### GET /stationDelay/code/`code`

Gibt die Verspätungsinformationen zu der Haltestelle mit dem Kürzel `code`.

#### Beispiel

```{"station":{"name":"Maschen","code":"AMA"},"time":"13:30","departures":[{"time":"14:00","train":"81622","targetStation":{"name":"Hamburg","code":"AH"},"delayInMinutes":0,"track":null},{"time":"14:54","train":"81623","targetStation":{"name":"Lüneburg","code":"ALBG"},"delayInMinutes":0,"track":null}]}```

## DeparturesWithAlert

#### GET /departure/station/`station`

Gibt die Verspätungsinformationen und relevanten Störmeldung zu der Haltestelle `station`.

#### Beispiel

```{"departures":[{"departure":{"time":"14:01","train":"82120","targetStation":{"name":"Hamburg","code":"AH"},"delayInMinutes":0,"track":null},"alert":null},{"departure":{"time":"14:09","train":"82821","targetStation":{"name":"Göttingen","code":"HG"},"delayInMinutes":0,"track":null},"alert":null},{"departure":{"time":"15:01","train":"82122","targetStation":{"name":"Hamburg","code":"AH"},"delayInMinutes":0,"track":null},"alert":null},{"departure":{"time":"15:09","train":"82823","targetStation":{"name":"Göttingen","code":"HG"},"delayInMinutes":0,"track":null},"alert":null}],"remainingAlerts":[]}```