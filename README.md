# metronom

API um Verkehrsmeldungen vom Metronom abzufragen.

Das Projekt basiert auf Java EE 7 und setzt einen Application-Server voraus. Die Entwicklung lief auf dem WildFly 10.

[![Build Status](http://kevcodez.de:8080/job/metronom-pipeline/badge/icon)](http://kevcodez.de:8080/job/metronom-pipeline/)

# REST-API:

`Einstiegspunkt: /api/v1`

## Alerts

##### GET /alert

Gibt eine Liste von allen aktuellen Meldungen aus.

##### GET /alert/since/`Datum in ISO-8601`

Gibt eine Liste von allen Meldungen ab dem übergebenen Datum aus.

##### GET /alert/contains/`text`

Gibt eine Liste von allen Meldungen aus, die den übergebenen `text` enhalten.


#### Beispiel-Response

```[{"id":"43169","text":"Wegen einer Signalstörung und warten auf Anschlussgäste verkehrt ME 82829 von Uelzen nach Göttingen ab Celle (18:46 Uhr) mit einer Verspätung von ca. 16 Minuten","createdAt":"2016-07-17T19:04:32"},{"id":"43168","text":"Wegen Verspätung des Eingangszuges durch einen Personenunfall im Hamburger Hauptbahnhof verkehrt ME 82030 von Bremen nach Hamburg ab Bremen(planm. Abfahrt18:33 Uhr) mit einer Verspätung von ca. 15 Minuten","createdAt":"2016-07-17T19:01:32"},{"id":"43167","text":"Wegen eines Personenunfalls entfällt heute leider ME 81934 von Bremen nach Hamburg ab Bremen(planm. Abfahrt 18:59 Uhr) .Die Fahrgäste nutzen bitte den Folgetakt","createdAt":"2016-07-17T18:59:33"},{"id":"43147","text":"Wegen Überholung durch ICE 1156 verkehrt ME 81632 von Lüneburg nach Hamburg ab Bardowick(planm.Abfahrt 18:38 Uhr) mit einer Verspätung von ca. 10 Mintuen","createdAt":"2016-07-16T18:54:30"},{"id":"43144","text":"Wegen einer Störung am Fahrzeug entfällt heute leider ME 81631 von Hamburg nach Lüneburg ab Hamburg (planm. Abfahrt 18:34 Uhr). Die Fahrgäste nutzen bitte den Folgetakt ME 82129 von Hamburg nach Uelzen ab Hamburg(planm.Abfahrt 18:57 Uhr)","createdAt":"2016-07-16T18:07:30"}]```

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

```{"name":"Elbe-Takt","trains":["RB 31","RE 3"],"stations":[null,{"name":"Hamburg-Harburg","code":"AHAR"},{"name":"Meckelfeld","code":"AMDH"},{"name":"Maschen","code":"AMA"},{"name":"Stelle","code":"ASTE"},null,{"name":"Winsen (Luhe)","code":"AWI"},{"name":"Radbruch","code":"ARH"},{"name":"Bardowick","code":"ABAD"},{"name":"Lüneburg","code":"ALBG"},{"name":"Bienenbüttel","code":"ABIL"},{"name":"Bad Bevensen","code":"ABVS"},{"name":"Uelzen","code":"HU"}]}```

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

```{"station":{"name":"Maschen","code":"AMA"},"time":"18:28","departures":[{"time":"18:24","train":"81667","targetStation":{"name":"Lüneburg","code":"ALBG"},"delayInMinutes":11},{"time":"18:55","train":"81631","targetStation":{"name":"Lüneburg","code":"ALBG"},"delayInMinutes":0},{"time":"19:00","train":"81632","targetStation":{"name":"Hamburg Hbf","code":"AH"},"delayInMinutes":0},{"time":"19:24","train":"81669","targetStation":{"name":"Lüneburg","code":"ALBG"},"delayInMinutes":0},{"time":"19:55","train":"81633","targetStation":{"name":"Lüneburg","code":"ALBG"},"delayInMinutes":0},{"time":"20:01","train":"81634","targetStation":{"name":"Hamburg Hbf","code":"AH"},"delayInMinutes":0}]}```
