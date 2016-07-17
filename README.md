# metronom

API um Verkehrsmeldungen vom Metronom abzufragen.

Das Projekt basiert auf Java EE 7 und setzt einen Application-Server voraus. Die Entwicklung lief auf dem WildFly 10.

# REST-API:

**Einstiegspunkt: /api/v1**



###### GET /delay

Gibt eine Liste von allen aktuellen Meldungen aus.

###### GET /delay/since/<Datum in ISO-8601>

Gibt eine Liste von allen Meldungen ab dem übergebenen Datum aus.

###### GET /delay/contains/<text>

Gibt eine Liste von allen Meldungen aus, die den übergebenen Text enhalten.


###### Beispiel-Response

```[{"id":"43169","text":"Wegen einer Signalstörung und warten auf Anschlussgäste verkehrt ME 82829 von Uelzen nach Göttingen ab Celle (18:46 Uhr) mit einer Verspätung von ca. 16 Minuten","createdAt":"2016-07-17T19:04:32"},{"id":"43168","text":"Wegen Verspätung des Eingangszuges durch einen Personenunfall im Hamburger Hauptbahnhof verkehrt ME 82030 von Bremen nach Hamburg ab Bremen(planm. Abfahrt18:33 Uhr) mit einer Verspätung von ca. 15 Minuten","createdAt":"2016-07-17T19:01:32"},{"id":"43167","text":"Wegen eines Personenunfalls entfällt heute leider ME 81934 von Bremen nach Hamburg ab Bremen(planm. Abfahrt 18:59 Uhr) .Die Fahrgäste nutzen bitte den Folgetakt","createdAt":"2016-07-17T18:59:33"},{"id":"43147","text":"Wegen Überholung durch ICE 1156 verkehrt ME 81632 von Lüneburg nach Hamburg ab Bardowick(planm.Abfahrt 18:38 Uhr) mit einer Verspätung von ca. 10 Mintuen","createdAt":"2016-07-16T18:54:30"},{"id":"43144","text":"Wegen einer Störung am Fahrzeug entfällt heute leider ME 81631 von Hamburg nach Lüneburg ab Hamburg (planm. Abfahrt 18:34 Uhr). Die Fahrgäste nutzen bitte den Folgetakt ME 82129 von Hamburg nach Uelzen ab Hamburg(planm.Abfahrt 18:57 Uhr)","createdAt":"2016-07-16T18:07:30"}]```