package kotitalous;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Kotitalous-luokka, vastuualueet:
 * Huolehtii Käyttäjät-, Tehtävät-, ja SovitutTehtävät-luokkien välisestä yhteistyöstä
 * Lukee ja kirjoittaa Kotitalouden tiedostoon pyytämällä apua valmistajiltaan
 * 
 * Pääosin kaikki metodit ovat vain "välittäjämetodeja" käyttäjistöön.
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
 *
 */
public class Kotitalous {
    private Kayttajat kayttajat = new Kayttajat();
    private Tehtavat tehtavat = new Tehtavat();
    private SovitutTehtavat sovitut = new SovitutTehtavat();
    private String hakemisto = "kotitalous";

    
    /**
     * Palauttaa käyttäjien määrän
     * @return käyttäjien määrä
     */
    public int getKayttajia() {
        return this.kayttajat.getLkm();
    }
    

    /**
     * Lisää kerhoon uuden jäsenen
     * @param kayttaja lisättävä käyttäjä
     * @example
     * <pre name="test">
     *  Kotitalous kt = new Kotitalous();
     *  Kayttaja aada = new Kayttaja(), ben = new Kayttaja();
     *  aada.rekisteroi(); ben.rekisteroi();
     *  kt.getKayttajia()  === 0;
     *  kt.lisaa(aada); kt.getKayttajia() === 1;
     *  kt.lisaa(ben); kt.getKayttajia() === 2;
     *  kt.lisaa(aada); kt.getKayttajia() === 3;
     *  kt.annaKayttaja(0) === aada;
     *  kt.annaKayttaja(1) === ben;
     *  kt.annaKayttaja(2) === aada;
     *  kt.annaKayttaja(3) === aada; #THROWS IndexOutOfBoundsException
     *  kt.lisaa(aada); kt.getKayttajia() === 4;
     *  kt.lisaa(aada); kt.getKayttajia() === 5;
     * </pre>
     */
    public void lisaa(Kayttaja kayttaja) {
        this.kayttajat.lisaa(kayttaja);
    }


    /**
     * Korvaa käyttäjän tietorakenteessa.
     * Ottaa käyttäjän omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva käyttäjä. Jos ei löydy,
     * niin lisätään uutena käyttäjänä.
     * @param kayttaja lisätään käyttäjän viite. Huom. tietorakenne muuttuu omistajaksi!
     */
    public void korvaaTaiLisaa(Kayttaja kayttaja) { //TODO testit?
        kayttajat.korvaaTaiLisaa(kayttaja);
    }

    /**
     * @param tehtava lisättävä tehtävä
     */
    public void lisaa(Tehtava tehtava) {
        this.tehtavat.lisaa(tehtava);
    }


    /**
     * Korvaa tehtävän tietorakenteessa.
     * Ottaa tehtävän omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva tehtävä. Jos ei löydy,
     * lisätään uutena tehtävänä.
     * @param tehtava lisätään tehtävän viite, huom! Tietorakenne muuttuu omistajaksi!
     */
    public void korvaaTaiLisaa(Tehtava tehtava) {   //TODO testit?
        this.tehtavat.korvaaTaiLisaa(tehtava);
    }

    
    /**
     * Poistaa tehtävän ja siihen sidotut sovituttehtävät
     * @param tehtava joka poistetaan
     */
    public void poista(Tehtava tehtava) {
        this.tehtavat.poista(tehtava);
        poistaSovitut(tehtava);
    }
    
    /**
     * @param sovittutehtava lisättävä sovittu tehtävä
     */
    public void lisaa(SovittuTehtava sovittutehtava) {
        this.sovitut.lisaa(sovittutehtava);
    }
    
    
    /**
     * Poistaa sovituntehtävän, jossa on molemmat:
     * annettu käyttäjä ja annettu tehtävä
     * @param kayttaja annettu käyttäjä
     * @param tehtava annettu tehtävä
     * @example
     * <pre name="test">
     * Kotitalous kt = new Kotitalous();
     * Kayttaja k1 = new Kayttaja(); Kayttaja k2 = new Kayttaja();
     * Tehtava t1 = new Tehtava(); Tehtava t2 = new Tehtava();
     * SovittuTehtava s1 = new SovittuTehtava(); SovittuTehtava s2 = new SovittuTehtava();
     * SovittuTehtava s3 = new SovittuTehtava();
     * k1.rekisteroi(); k2.rekisteroi();
     * t1.rekisteroi(); t2.rekisteroi();
     * s1.setKayttaja(k1); s2.setKayttaja(k2); s3.setKayttaja(k1);
     * s1.setTehtava(t1); s2.setTehtava(t1); s3.setTehtava(t2);
     * kt.lisaa(k1); kt.lisaa(k2); kt.lisaa(t1); kt.lisaa(t2);
     * kt.lisaa(s1); kt.lisaa(s2); kt.lisaa(s3);
     * 
     * List<SovittuTehtava> sovitutT1 = kt.annaSovitutKayttajat(t1);
     * sovitutT1.get(0).toString() === s1.toString();
     * sovitutT1.get(1).toString() === s2.toString();
     * kt.poistaSovittu(k2, t1);
     * sovitutT1 = kt.annaSovitutKayttajat(t1);
     * sovitutT1.size() === 1;
     * sovitutT1.get(0).toString() === s1.toString();
     * kt.lisaa(s2);
     * sovitutT1 = kt.annaSovitutKayttajat(t1);
     * sovitutT1.size() === 2;
     * kt.poistaSovittu(s2);
     * sovitutT1 = kt.annaSovitutKayttajat(t1);
     * sovitutT1.size() === 1;
     * 
     * List<SovittuTehtava> sovitutK1 = kt.annaSovitutTehtavat(k1);
     * sovitutK1.size() === 2;
     * kt.poistaSovitut(k1);
     * sovitutK1 = kt.annaSovitutTehtavat(k1);
     * sovitutK1.size() === 0;
     * </pre>
     */
    public void poistaSovittu(Kayttaja kayttaja, Tehtava tehtava) {
        this.sovitut.poista(kayttaja, tehtava);
    }
    
    
    /**
     * Poistaa sovituntehtävän
     * Testejä "poistaSovittu(Kayttaja kayttaja, Tehtava tehtava)" kohdalla
     * @param st poistettava sovittutehtävä
     */
    public void poistaSovittu(SovittuTehtava st) {
        this.sovitut.poista(st);
    }
    
    
    /**
     * Poistaa sovitutTehtävät, joilla on annettu käyttäjä
     * Testejä "poistaSovittu(Kayttaja kayttaja, Tehtava tehtava)" kohdalla
     * @param kayttaja joka ollaan poistamassa kotitaloudesta
     */
    public void poistaSovitut(Kayttaja kayttaja) {
        List<SovittuTehtava> kayttajalleSovitut = annaSovitutTehtavat(kayttaja);
        for (SovittuTehtava st : kayttajalleSovitut) {
            this.sovitut.poista(st);
        }
    }
    
    
    /**
     * Poistaa sovitutTehtävät, joilla annettu tehtävä
     * @param tehtava jonka perusteella poistetaan
     */
    public void poistaSovitut(Tehtava tehtava) {
        List<SovittuTehtava> tehtavalleSovitut = annaSovitutKayttajat(tehtava);
        for (SovittuTehtava st : tehtavalleSovitut) {
            this.sovitut.poista(st);
        }
    }
    
    
    /**
     * Lisää sovittuun tehtävään käyttäjän (id:n).
     * @param st sovittu tehtävä, jota muokataan
     * @param kayttaja käyttäjä, jonka id halutaan lisätä
     */
    public void setKayttaja(SovittuTehtava st, Kayttaja kayttaja) {
        this.sovitut.setKayttaja(st, kayttaja);
    }


    /**
     * Palauttaa i:nnen jäsenen
     * @param i monesko käyttäjä palautetaan
     * @return viite i:nteen käyttäjään
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Kayttaja annaKayttaja(int i) throws IndexOutOfBoundsException {
        return this.kayttajat.anna(i);
    }
    
    
    /**
     * @param kayttajaId käyttäjäid, jonka perusteella etsitään
     * @return käyttäjä, jolla annettu käyttäjä-id
     * @throws SailoException jos annettua käyttäjä-id:tä ei löydy
     */
    public Kayttaja etsiKayttaja(int kayttajaId) throws SailoException {
        return this.kayttajat.etsi(kayttajaId);
    }
    
    
    /**
     * Poistaa annetun käyttäjän ja tallentaa tiedoston
     * @param kayttaja annettu käyttäjä
     * @throws SailoException jos tallennus ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Kotitalous kt = new Kotitalous();
     * Kayttaja k1 = new Kayttaja(); k1.rekisteroi();
     * kt.lisaa(k1);
     * kt.annaKayttaja(0).toString() === k1.toString();
     * kt.poista(k1);
     * kt.annaKayttaja(0); #THROWS IndexOutOfBoundsException
     * </pre>
     */
    public void poista(Kayttaja kayttaja) throws SailoException {
        this.kayttajat.poista(kayttaja);
        poistaSovitut(kayttaja);
        tallenna();
    }
    
    /**
     * Palauttaa tehtävän tehtavaId:n perusteella
     * @param tehtavaId id, jonka perusteella tehtävä etsitään
     * @return viite tehtävään, jolla annettu id
     * @throws SailoException jos tehtavaId:tä ei löydy
     */
    public Tehtava etsiTehtava(int tehtavaId) throws SailoException {
        return this.tehtavat.etsi(tehtavaId);
    }
    
    
    /**
     * Pyytää tehtäviä palauttamaan listan,
     * jossa on kaikki tämän hetken tehtävät
     * @return kaikki tehtävät listana
     * @example
     * <pre name="test">
     * Kotitalous kt = new Kotitalous();
     * Tehtava t1 = new Tehtava(); Tehtava t2 = new Tehtava();
     * kt.lisaa(t1); kt.lisaa(t2);
     * List<Tehtava> kaikki = kt.annaTehtavat();
     * kaikki.size() === 2;
     * </pre>
     */
    public List<Tehtava> annaTehtavat() {
        return this.tehtavat.annaKaikki();
    }
    
    
    /**
     * Etsii kaikista tehtävistä ne, joille ei ole vielä tekijöitä ja 
     * palauttaa ne listana // TODO testit
     * @return lista sellaisista tehtävistä, joilla ei ole vielä tekijää
     */
    public List<Tehtava> annaVapaatTehtavat() {
        List<Tehtava> kaikki = annaTehtavat();
        List<Tehtava> vapaat = new ArrayList<Tehtava>();
        for (Tehtava t : kaikki) {
            if (!tehtavalleTekija(t)) vapaat.add(t);
        }
        return vapaat;  // voi palauttaa myös tyhjän listan 
    }
    
    
    /**
     * Etsii kaikista tehtävistä ne, joitka 
     * @param hakuehto merkkijono, joka täytyy löytyä tehtävän nimestä
     * @return lista tehtävistä, joissa on hakuehto nimessä
     */
    public List<Tehtava> annaTehtavatHakuehdolla(String hakuehto) {
        return this.tehtavat.annaHakuehdolla(hakuehto);
    }
    
    
    /**
     * Palauttaa tiedon, onko tehtävälle tekijä
     * @param t tehtävä, jolle etsitään tekijää
     * @return true jos on edes yksi sovittu käyttäjä, false jos ei yhtään
     */
    public boolean tehtavalleTekija(Tehtava t) {
        return this.sovitut.tehtavalleTekija(t);
    }
    
    /**
     * Palauttaa listan käyttäjistä annetun tehtävän perusteella
     * @param t tehtävä
     * @return lista käyttäjistä, joilla on sovittuna annettu tehtävä
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Kotitalous kt = new Kotitalous();
     * Kayttaja aada = new Kayttaja(), ben = new Kayttaja();
     * aada.rekisteroi(); ben.rekisteroi();
     * Tehtava imu1 = new Tehtava(), imu2 = new Tehtava(), imu3 = new Tehtava();
     * imu1.rekisteroi(); imu2.rekisteroi(); imu3.rekisteroi();
     * SovittuTehtava st1 = new SovittuTehtava(), st2 = new SovittuTehtava();
     * SovittuTehtava st3 = new SovittuTehtava(), st4 = new SovittuTehtava();
     * st1.setKayttaja(aada); st1.setTehtava(imu1);
     * st2.setKayttaja(aada); st2.setTehtava(imu2);
     * st3.setKayttaja(aada); st3.setTehtava(imu3);
     * st4.setKayttaja(ben); st4.setTehtava(imu3);
     * 
     * kt.lisaa(aada); kt.lisaa(ben);
     * kt.lisaa(imu1); kt.lisaa(imu2); kt.lisaa(imu3);
     * kt.lisaa(st1); kt.lisaa(st2); kt.lisaa(st3); kt.lisaa(st4);
     * 
     * List<SovittuTehtava> loydetyt = kt.annaSovitutKayttajat(imu1);
     * loydetyt.size() === 1;
     * loydetyt.get(0) === st1;
     * loydetyt = kt.annaSovitutKayttajat(imu2);
     * loydetyt.size() === 1;
     * loydetyt.get(0) === st2;
     * loydetyt = kt.annaSovitutKayttajat(imu3);
     * loydetyt.size() === 2;
     * loydetyt.get(0) === st3;
     * loydetyt.get(1) === st4;
     * loydetyt = kt.annaSovitutTehtavat(aada);
     * loydetyt.size() === 3;
     * loydetyt = kt.annaSovitutTehtavat(ben);
     * loydetyt.size() === 1;
     * loydetyt.get(0) === st4;
     * 
     * </pre>
     */
    public List<SovittuTehtava> annaSovitutKayttajat(Tehtava t) {
        return this.sovitut.annaSovitutKayttajat(t);
    }
    
    
    /**
     * Palauttaa listan tehtävistä annetun käyttäjän perusteella
     * @param k käyttäjä
     * @return lista tehtävistä, joilla on sovittuna annettu käyttäjä
     * (Testit tästä "annaSovitutKayttajat" alla)
     */
    public List<SovittuTehtava> annaSovitutTehtavat(Kayttaja k) {
        return this.sovitut.annaSovitutTehtavat(k);
    }

    
    
    
    /**
     * Lukee kerhon tiedot tiedostosta
     * @param nimi hakemiston nimi
     * @throws SailoException jos lukemisessa ongelmia
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     *  Kotitalous kt = new Kotitalous();
     *  Kayttaja aada1 = new Kayttaja(); aada1.rekisteroi(); aada1.taytaAadaTiedoilla();
     *  Kayttaja aada2 = new Kayttaja(); aada2.rekisteroi(); aada2.taytaAadaTiedoilla();
     *  Kayttaja aada3 = new Kayttaja(); aada3.rekisteroi(); aada3.taytaAadaTiedoilla();
     *  Tehtava imu12 = new Tehtava(); imu12.rekisteroi(); imu12.taytaImurointiTiedoilla();
     *  Tehtava imu23 = new Tehtava(); imu23.rekisteroi(); imu23.taytaImurointiTiedoilla();
     *  Tehtava imu34 = new Tehtava(); imu34.rekisteroi(); imu34.taytaImurointiTiedoilla();
     *  SovittuTehtava st = new SovittuTehtava(); st.setKayttaja(aada1); st.setTehtava(imu12);
     *  
     *  String tiedNimi = "testiKotitalous";
     *  File ftied = new File(tiedNimi + "/kayttajat.dat");
     *  File ftied2 = new File(tiedNimi + "/tehtavat.dat");
     *  File ftied3 = new File(tiedNimi + "/sovitut.dat");
     *  ftied.delete(); ftied2.delete(); ftied3.delete();
     *  kt.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kt.lisaa(aada1); kt.lisaa(aada2); kt.lisaa(aada3);
     *  kt.lisaa(imu12); kt.lisaa(imu23); kt.lisaa(imu34);
     *  kt.lisaa(st);
     *  kt.tallenna(tiedNimi);
     *  
     *  kt = new Kotitalous();
     *  kt.lueTiedostosta(tiedNimi);
     *  kt.getKayttajia() === 3;
     *  kt.annaKayttaja(0).toString() === aada1.toString();
     *  kt.poista(aada1);
     *  kt.annaKayttaja(0).toString() == aada1.toString() === false;
     *  kt.annaKayttaja(0).toString() === aada2.toString();
     *  kt.getKayttajia() === 2;
     *  kt.etsiTehtava(imu12.getTid()).toString() === imu12.toString();
     *  kt.tallenna(tiedNimi);
     *  
     *  ftied.delete() === true;
     *  ftied2.delete() === true;
     *  ftied3.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        File dir = new File(nimi);
        dir.mkdir();
        this.kayttajat = new Kayttajat();
        this.tehtavat = new Tehtavat();
        this.sovitut = new SovitutTehtavat();
        
        this.hakemisto = nimi;
        this.kayttajat.lueTiedostosta(nimi);
        this.tehtavat.lueTiedostosta(nimi);
        this.sovitut.lueTiedostosta(nimi);
    }
    
    
    /**
     * Tallettaa kotitalouden tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            kayttajat.tallenna(hakemisto);
        } catch ( SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            tehtavat.tallenna(hakemisto);
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        
        try {
            sovitut.tallenna(hakemisto);
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        
        if (!"".equals(virhe)) throw new SailoException(virhe);
    }
    
    
    /**
     * Testejä varten tallenna()-metodia vastaava, jossa hakemiston nimen voi vaihtaa
     * @param hakemistonNimi testihakemiston nimi
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna(String hakemistonNimi) throws SailoException {
        String virhe = "";
        try {
            kayttajat.tallenna(hakemistonNimi);
        } catch ( SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            tehtavat.tallenna(hakemistonNimi);
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        
        try {
            sovitut.tallenna(hakemistonNimi);
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        
        if (!"".equals(virhe)) throw new SailoException(virhe);
    }

    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kotitalous kt = new Kotitalous();

        try {
            kt.lueTiedostosta("testiKotitalous");
        } catch (SailoException e1) {
            System.err.println(e1.getMessage());
        }
        Kayttaja aada = new Kayttaja(), ben = new Kayttaja();
        aada.rekisteroi();
        aada.taytaAadaTiedoilla();
        ben.rekisteroi();
        ben.taytaAadaTiedoilla();

        kt.lisaa(aada);
        kt.lisaa(ben);

        Tehtava imu = new Tehtava(), imut = new Tehtava();
        imu.rekisteroi();
        imu.taytaImurointiTiedoilla();
        imut.rekisteroi();
        imut.taytaImurointiTiedoilla();
        
        kt.lisaa(imu);
        kt.lisaa(imut);
        
        SovittuTehtava st1 = new SovittuTehtava(), st2 = new SovittuTehtava(),
                st3 = new SovittuTehtava();
        st1.setKayttaja(aada);
        st1.setTehtava(imu);
        st2.setKayttaja(ben);
        st2.setTehtava(imut);
        st3.setKayttaja(aada);
        st3.setTehtava(imut);
        
        kt.lisaa(st1);
        kt.lisaa(st2);
        kt.lisaa(st3);
        
        System.out.println(
                " ================ Kotitalouden testi ================ ");
        for (int i = 0; i < kt.getKayttajia(); i++) {
            Kayttaja kayttaja = kt.annaKayttaja(i);
            System.out.println("Jäsen paikassa: " + i);
            kayttaja.tulosta(System.out);
        }
        
        
        System.out.println("\nTehtävälle " + imu + " on sovittuna käyttäjät: ");
        List<SovittuTehtava> sovitut = kt.annaSovitutKayttajat(imu);
        for (SovittuTehtava st : sovitut) {
            try {
                System.out.println(kt.etsiKayttaja(st.getKid()));
            } catch (SailoException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\nKäyttäjälle " + aada + " on sovittuna tehtävät: ");
        sovitut = kt.annaSovitutTehtavat(aada);
        for (SovittuTehtava st : sovitut) {
            try {
                System.out.println(kt.etsiTehtava(st.getTid()));
            } catch (SailoException e) {
                e.printStackTrace();
            };
        }
        
        try {
            kt.tallenna("testiKotitalous");
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }

    }
}
