package kotitalous;

import java.io.File;
import java.util.List;

/**
 * Kotitalous-luokka, vastuualueet:
 * Huolehtii Käyttäjät-, Tehtävät-, ja SovitutTehtävät-luokkien välisestä yhteistyöstä
 * Lukee ja kirjoittaa Kotitalouden tiedostoon pyytämällä apua valmistajiltaan
 * 
 * Pääosin kaikki metodit ovat vain "välittäjämetodeja" käyttäjistöön.
 * @author Anniina
 * @version 22.2.2022
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
     * @param tehtava lisättävä tehtävä
     */
    public void lisaa(Tehtava tehtava) {
        this.tehtavat.lisaa(tehtava);
    }


    /**
     * @param sovittutehtava lisättävä sovittu tehtävä
     */
    public void lisaa(SovittuTehtava sovittutehtava) {
        this.sovitut.lisaa(sovittutehtava);
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
     */
    public List<Tehtava> annaTehtavat() {
        return this.tehtavat.annaKaikki();
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
     * @param nimi hakemiston nimi
     * @throws SailoException jos lukemisessa ongelmia
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        File dir = new File(nimi);
        dir.mkdir();
        this.kayttajat = new Kayttajat();
        // this.tehtavat = new Tehtavat();
        // this.sovitut = new SovitutTehtavat();
        
        this.hakemisto = nimi;
        this.kayttajat.lueTiedostosta(nimi);
        //this.tehtavat.lueTiedostosta(nimi);
        //this.sovitut.lueTiedostosta(nimi)
    }
    
    
    /**
     * Tallettaa kotitalouden tiedot tiedostoon VAIHE 6
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            kayttajat.tallenna(hakemisto);
        } catch ( SailoException ex) {
            virhe = ex.getMessage();
        }
        
//        try {
//            tehtavat.tallenna(hakemisto);
//        } catch (SailoException ex) {
//            virhe += ex.getMessage();
//        }
//        
//        try {
//            sovitut.tallenna(hakemisto);
//        } catch (SailoException ex) {
//            virhe += ex.getMessage();
//        }
        
        if (!"".equals(virhe)) throw new SailoException(virhe);
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kotitalous kt = new Kotitalous();

        try {
            kt.lueTiedostosta("kotitalous");
        } catch (SailoException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
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
        
        // kotitalous.tallenna(); //try catchin sisään... VAIHE6

    }

}
