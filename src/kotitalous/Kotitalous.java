package kotitalous;

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
    private final Kayttajat kayttajat = new Kayttajat();
    private final Tehtavat tehtavat = new Tehtavat();
    private final SovitutTehtavat sovitut = new SovitutTehtavat();
//    private String hakemisto = "kotitalous";

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
     * Palauttaa i:nnen jäsenen
     * @param i monesko käyttäjä palautetaan
     * @return viite i:nteen käyttäjään
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Kayttaja annaKayttaja(int i) throws IndexOutOfBoundsException {
        return this.kayttajat.anna(i);
    }
    
    
    /**
     * Palauttaa i:nnen tehtävän
     * @param i monesko tehtävä palautetaan
     * @return viite i:nteen tehtävään
     * @throws SailoException jos i väärin
     */
    public Tehtava annaTehtava(int i) throws SailoException {
        return this.tehtavat.anna(i);
    }
    
    
    /**
     * Palauttaa listan käyttäjistä annetun tehtävän perusteella
     * @param t tehtävä
     * @return lista käyttäjistä, joilla on sovittuna annettu tehtävä
     */
    public List<SovittuTehtava> annaSovitutKayttajat(Tehtava t) {
        return this.sovitut.annaSovitutKayttajat(t);
    }
    
    
    /**
     * Palauttaa listan tehtävistä annetun käyttäjän perusteella
     * @param k käyttäjä
     * @return lista tehtävistä, joilla on sovittuna annettu käyttäjä
     */
    public List<SovittuTehtava> annaSovitutTehtavat(Kayttaja k) {
        return this.annaSovitutTehtavat(k);
    }

    
//    /**
//     * Tallettaa kotitalouden tiedot tiedostoon
//     * @throws SailoException jos tallettamisessa ongelmia
//     */
//    public void tallenna() throws SailoException {
//        String virhe = "";
//        try {
//            kayttajat.tallenna(hakemisto);
//        } catch ( SailoException ex) {
//            virhe = ex.getMessage();
//        }
//        
//        try {
//            tehtavat.tallenna(hakemisto);
//        } catch ()
//    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kotitalous kt = new Kotitalous();

        // kotitalous.lueTiedostosta("kotitalous");
        Kayttaja aada = new Kayttaja(), ben = new Kayttaja();
        aada.rekisteroi();
        aada.taytaAadaTiedoilla();
        ben.rekisteroi();
        ben.taytaAadaTiedoilla();

        kt.lisaa(aada);
        kt.lisaa(ben);

        System.out.println(
                " ================ Kotitalouden testi ================ ");
        for (int i = 0; i < kt.getKayttajia(); i++) {
            Kayttaja kayttaja = kt.annaKayttaja(i);
            System.out.println("Jäsen paikassa: " + i);
            kayttaja.tulosta(System.out);
        }
        
        // kotitalous.tallenna(); //try catchin sisään...

    }

}
