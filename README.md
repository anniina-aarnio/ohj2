# JYU Ohjelmointi 2 -kurssin harjoitustyö

Ohjelman tarkoitus on luoda ja ylläpitää rekisteri kullekin käyttäjälle sovituista kotitöistä. Alunperin idea oli jakaa kotitöitä perheenjäsenille, mutta ohjelmaa voi käyttää myös projektin eri osien suunnitteluun ja jakamiseen tekijöiden kesken.

## Ohjelman käyttö

Ohjelma on valmis esimerkkitiedostoineen (rekisterit käyttäjistä ja kotitöistä). Ohjelmaa käytetään komentorivillä:
java -jar Kotityot.jar

## Ohjelman taustaa

### Mitä tietoja rekisteriin kerätään

Käyttäjät (eli perheenjäsenet):
- nimi
- ikä

Kotityöt:
- nimi
- kesto minuutteina
- ikäsopivuus

### Mitä ominaisuuksia rekisteriltä halutaan

- käyttäjien lisääminen
- käyttäjien poistaminen
- tietyn käyttäjän tietojen hakeminen
- tietyn käyttäjän tietojen muuttaminen
- tehtävien lisääminen
- tehtävien poistaminen
- tietyn tehtävän tietojen hakeminen
- tietyn tehtävän tietojen muuttaminen
- nimilista nimen / iän mukaisessa järjestyksessä
- sellaisten tehtävien haku, joilla ei ole sovittuna tekijää
- tehtävien haku soveltuvuudeltaan minimi-iän mukaan

### Tallennustiedostojen muoto

Ohjelmat tiedot tallennetaan seuraavanlaisiin tekstitiedostoihin:
`perhe\kayttajat.dat` - käyttäjien päätaulu

```
Kayttajat
id|nimi         |ikä    
1 |Aada         |35
2 |Benjamin     |35
3 |Cecilia      |11
4 |Daniel       |2
```

`perhe\tehtavat.dat` - tehtävien päätaulu
```
id|nimi                 |kesto  |tekijän vähimmäisikä
1 |Imurointi            |40     |7
2 |Roskien vienti       |3      |7
3 |Likakaivon tyhjennys |30     |18
4 |Omat astiat pesuun   |1      |2
5 |WC:n pesu            |20     |10
6 |Koiran ulkoilutus    |10     |10
```

`perhe\sovitut_tehtavat.dat` - käyttäjille sovitut tehtävät relaation avulla
Sovittu tehtävä tallennetaan vasta kun tehtävälle asetetaan vähintään yksi
käyttäjä

```
kayttaja_id |tehtava_id
1           |1
1           |3
2           |2
2           |3
3           |2
2           |5
1           |5
3           |5
```

